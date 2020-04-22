package com.example.covid_19visualizer;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import joinery.DataFrame;

/**
 * Created by: Harry
 * <p>
 * This class provides tools for downloading and accessing the raw COVID-19 data.
 * <p>
 * The data is accessed from https://github.com/CSSEGISandData/COVID-19/, a wonderful open-source
 * data source put together by Johns Hopkins. The specific data we're pulling from is located at
 * https://github.com/CSSEGISandData/COVID-19/tree/master/csse_covid_19_data
 * /csse_covid_19_daily_reports.
 */
public class CovidData {

    private URL onlineDataPath; // the path to the csv on the github repository

    private Date date; // The date of the most recent csv data

    private File localData; // Where the data is stored after it's downloaded

    private File dataDirectory; // the folder containing the data

    // true if the data is downloaded (including if it isn't the most recent data), false if
    // otherwise
    private boolean isDataDownloaded;

    private Context context;

    // The default constructor. It grabs the current date and the github link
    public CovidData(@NotNull Context context) {
        this.context = context;

        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        date = new Date();
        date.setDate(date.getDate() - 1);

        try {
            onlineDataPath = new URL("https://raw.githubusercontent" +
                    ".com/CSSEGISandData/COVID-19/master/csse_covid_19_data" +
                    "/csse_covid_19_daily_reports/" + dateFormat.format(date) + ".csv");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // Create a folder called "data" (or assign it to dataDirectory if it already exists)
        // and put the new file inside of it.
        dataDirectory = context.getDir("covid_data", Context.MODE_PRIVATE);
        localData = new File(dataDirectory, dateFormat.format(date) + ".csv");
        isDataDownloaded = localData.exists();
    }

    /******************************************************/
    // This function is only used for debugging purposes so that we can simulate the downloading
    // process multiple times
    public void deleteData() {
        File[] localDataFiles = dataDirectory.listFiles();
        assert localDataFiles != null;
        for (File localDataFile : localDataFiles) {
            if(!localDataFile.isDirectory()) {
                localDataFile.delete();
            }
        }
        isDataDownloaded = false;
    }
    /******************************************************/

    // Download the data from github.
    // exit status...
    //      0 -> data downloaded successfully
    //      1 -> data could not be downloaded, no old data to fall back on
    //      2 -> data could not be downloaded but we do have old data to fall back on
    public int downloadData() {
        DownloadDataTask downloadDataTask =
                new DownloadDataTask(((Activity) context).findViewById(android.R.id.content));

        downloadDataTask.execute();
        return downloadDataTask.doInBackgroundReturn;
    }


    public boolean isDataDownloaded() {
        return isDataDownloaded;
    }

    // Before we do anything with the downloaded data, we need to format it. This method is run
    // right after the data is finished downloading by DownloadDataTask.onPostExecute().
    private void formatData() {
        DataFrame<Object> data = new DataFrame<>();
        try {
            data = DataFrame.readCsv(localData.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        data.drop("FIPS", "Last_Update", "Combined_Key");
    }

    // Downloading data must be done in the background, hence the need for this sub class.
    private class DownloadDataTask extends AsyncTask<URL, Integer, Integer> {


        private View rootView; // This allows us to have a snackbar display on the main screen

        // The message we display to the user to show that the data is being downloaded.
        private Snackbar snackbar;

        // The value returned by doInBackground, which I use in downloadData() for error checking
        private int doInBackgroundReturn;

        // constructor that assigns the rootView member, allowing us to create a snackbar in
        // onPostExecute
        public DownloadDataTask(View rootView) {
            this.rootView = rootView;
        }

        // Automatically called immediately before doInBackground()
        @Override
        protected void onPreExecute() {
            snackbar = Snackbar.make(rootView, "Fetching the latest data...",
                    Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
        }

        // called by the execute() method
        @Override
        protected Integer doInBackground(URL... urls) {
            // Check if the user is connected to internet
            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            assert cm != null;
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

            BufferedReader reader;
            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
                try {
                    reader = new BufferedReader(new InputStreamReader(onlineDataPath.openStream()));
                } catch (Exception e) {
                    return findLocalData();
                }
            } else {
                return findLocalData();
            }

            // Download the csv 1 line at a time
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(localData));
                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write(line);
                    writer.newLine();
                }
                reader.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            isDataDownloaded = true;

            // Now that we've downloaded our data, we need to delete any other data files that are
            // currently stored to avoid wasting memory.
            File[] localDataFiles = context.getFilesDir().listFiles();
            assert localDataFiles != null;
            for (File localDataFile : localDataFiles) {
                if (!localDataFile.getName().equals(localData.getName())) {
                    localDataFile.delete();
                }
            }
            return 0;
        }

        // called immediately after doInBackground(). result is the output of that method.
        @Override
        protected void onPostExecute(Integer result) {
            doInBackgroundReturn = result;

            if(doInBackgroundReturn == 0) {
                // There's no need to format the data if it didn't download (codes 1 or 2)
                formatData();
            }

            snackbar.dismiss();
        }

        // If we can't connect to github, this function determines what to do.
        private int findLocalData() {
            // check if there is a file from a previous day
            File[] oldFiles = dataDirectory.listFiles();
            assert oldFiles != null;

            if (oldFiles.length == 0) {
                // Well, it looks like we don't have any data to fall back on. exit with
                // status 1.
                return 1;

            } else {
                // If there is an old file, set it to the current file and exit with status 2.
                localData = oldFiles[0];
                isDataDownloaded = true;
                String name = localData.getName();
                // remove the ".csv" extension
                name = name.substring(0, name.length() - 4);
                Log.d("VALUE_TEST", name);
                date = new Date(name);
                return 2;
            }
        }
    }
}
