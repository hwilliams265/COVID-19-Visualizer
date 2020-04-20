package com.example.covid_19visualizer;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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

    // true if the data is downloaded (including if it isn't the most recent data), false if
    // otherwise
    private boolean isDataDownloaded;

    private Context context;

    // The default constructor. It grabs the current date and the github link
    public CovidData() throws MalformedURLException {
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        date = new Date();
        date.setDate(date.getDate() - 1);

        onlineDataPath = new URL("https://raw.githubusercontent" +
                ".com/CSSEGISandData/COVID-19/master/csse_covid_19_data" +
                "/csse_covid_19_daily_reports/" + dateFormat.format(date) + ".csv");

        localData = new File(context.getFilesDir(), dateFormat.format(date) + ".csv");
        isDataDownloaded = localData.exists();
    }

    // Download the data from github.
    // exit status...
    //      0 -> data downloaded successfully
    //      1 -> data could not be downloaded, no old data to fall back on
    //      2 -> data could not be downloaded but we do have old data to fall back on
    public int downloadData() throws IOException {
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
        BufferedWriter writer = new BufferedWriter(new FileWriter(localData));
        String line;
        while ((line = reader.readLine()) != null) {
            writer.write(line);
        }
        reader.close();
        writer.close();

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

    // If we can't connect to github, this function determines what to do.
    private int findLocalData() {
        // check if there is a file from a previous day
        File[] oldFiles = context.getFilesDir().listFiles();
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
            date = new Date(name.substring(0, name.length() - 4));
            return 2;
        }
    }
}
