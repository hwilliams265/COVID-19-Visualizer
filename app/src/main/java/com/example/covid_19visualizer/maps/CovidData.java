package com.example.covid_19visualizer.maps;

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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public File localData; // Where the data is stored after it's downloaded
    private URL onlineDataPath; // the path to the csv on the github repository
    private Date date; // The date of the most recent csv data
    private File dataDirectory; // the folder containing the data

    // The actual data that we're plotting, organized into a map of Lists. The keys are the
    // latitude, longitude, and a few basic stats about the COVID infection rate.
    private Map<String, List<Object>> data;

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
        data = new HashMap<>(); // this gets populated by initializeData().
    }

    /******************************************************/
    // This function is only used for debugging purposes so that we can simulate the downloading
    // process multiple times
    public void deleteData() {
        File[] localDataFiles = dataDirectory.listFiles();
        assert localDataFiles != null;
        for (File localDataFile : localDataFiles) {
            if (!localDataFile.isDirectory()) {
                localDataFile.delete();
            }
        }
        isDataDownloaded = false;
    }

    /******************************************************/

    // Download the data from github. In addition to downloading the data to a csv file, this
    // program also causes initializeData() to be executed, which initializes the data member.
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

    // Create usable data from the downloaded csv
    public void initializeData() {
        String[] CATEGORIES = new String[]{"County", "Province_State", "Country_Region",
                "Last_Update", "Lat", "Long", "Confirmed", "Deaths", "Recovered", "Active"};
        for (String category : CATEGORIES) {
            data.put(category, new ArrayList<Object>());
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(localData));
            String[] rowData;
            String row;
            reader.readLine(); // we discard the first row (since its just headers)

            while ((row = reader.readLine()) != null) {
                // That weird string is regex for: "match a comma only if it isn't followed by a
                // space. This way, commas that are naturally part of the text are not considered
                // value separators. God help us if a comma that is naturally part of the data
                // isn't followed by a space...
                rowData = row.split("(?!, ),");

                // columns 1-10 have the data we need
                int i = 1;
                for (String category : CATEGORIES) {
                    data.get(category).add(rowData[i]);
                    i++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Convert Lat and Long to float, and everything else to int.
        double doubleValue;
        int intValue;
        List<Integer> rowsToDelete = new ArrayList<>();
        for (String category : CATEGORIES) {
            for (int i = 0; i < data.get("Lat").size(); i++) {
                if (category.equals("Lat") || category.equals("Long")) {
                    // There are a couple points that don't have a location value. We want to
                    // keep track of them and then delete them once we're finished looping though.
                    // We also want to avoid converting them, since it will crash the program if
                    // we try to convert an empty string to a number.
                    if (data.get(category).get(i).equals("")) {
                        rowsToDelete.add(i);
                    } else {
                        // Can I just say how bad/annoying/unnecessarily verbose java is when
                        // converting types?
                        doubleValue = Double.parseDouble((String) data.get(category).get(i));
                        data.get(category).set(i, doubleValue);
                    }
//                } else if (!category.equals("Country_Region") &&
//                        !category.equals("Last_Update") &&
//                        !category.equals("Province_State") &&
//                        !category.equals("County")) {
                } else if (category.equals("Confirmed") || category.equals("Deaths") ||
                        category.equals("Recovered") || category.equals("Active")) {
                    intValue = Integer.parseInt((String) data.get(category).get(i));
                    data.get(category).set(i, intValue);
                }
            }
        }
        if (rowsToDelete.size() != 0) {
            for (int i = rowsToDelete.size() - 1; i > 0; i--) {
                for (String category : CATEGORIES) {
                    data.get(category).remove(rowsToDelete.get(i).intValue());
                }
            }
        }

        // The original data set does not include data for each state, rather data for each US
        // county. This next bit of code is a way to combine that county-level data to
        // state-level data.
        String[] STATE_NAMES = {"Alabama", "Alaska", "Arizona", "Arkansas", "California",
                "Colorado", "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho",
                "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine",
                "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri",
                "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico",
                "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon",
                "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee",
                "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin"
                , "Wyoming"};
        double[] STATE_LATS = {32.6010112, 61.3025006, 34.1682185, 34.7519275, 37.2718745,
                38.9979339, 41.5187835, 39.145251, 27.9757279, 32.6781248, 20.46, 45.4945756,
                39.739318, 39.7662195, 41.9383166, 38.4987789, 37.8222935, 30.9733766, 45.2185133
                , 38.8063524, 42.0629398, 44.9435598, 46.4418595, 32.5851062, 38.3046615,
                46.6797995, 41.5008195, 38.502032, 44.0012306, 40.1430058, 34.1662325, 40.7056258
                , 35.2145629, 47.4678819, 40.1903624, 35.3097654, 44.1419049, 40.9945928,
                41.5827282, 33.62505, 44.2126995, 35.830521, 31.1693363, 39.4997605, 43.8717545,
                38.0033855, 38.8993487, 38.9201705, 44.7862968, 43.000325};
        double[] STATE_LONGS = {-86.6807365, -158.7750198, -111.930907, -92.1313784, -119.2704153
                , -105.550567, -72.757507, -75.4189206, -83.8330166, -83.2229757, -157.505,
                -114.1424303, -89.504139, -86.441277, -93.389798, -98.3200779, -85.7682399,
                -91.4299097, -69.0148656, -77.2684162, -71.718067, -86.4158049, -93.3655146,
                -89.8772196, -92.437099, -110.044783, -99.680902, -117.0230604, -71.5799231,
                -74.7311156, -106.0260685, -73.97968, -79.8912675, -100.3022655, -82.6692525,
                -98.7165585, -120.5380993, -77.6046984, -71.5064508, -80.9470381, -100.2471641,
                -85.9785989, -100.0768425, -111.547028, -72.4477828, -79.4587861, -77.0145665,
                -80.1816905, -89.8267049, -107.5545669};

        Map<String, List<Object>> stateData = new HashMap<>();
        for (String category : CATEGORIES) {
            stateData.put(category, new ArrayList<Object>());
        }
        for (String stateName : STATE_NAMES) {
            stateData.get("Country_Region").add(stateName);
        }
        for (double lat : STATE_LATS) {
            stateData.get("Lat").add(lat);
        }
        for (double long_ : STATE_LONGS) {
            stateData.get("Long").add(long_);
        }

        // For each state, we want a sum of their COVID stats.
        Map<String, Integer> stateTally = new HashMap<>();
        for (String stateName : STATE_NAMES) {
            stateTally.put("Confirmed", 0);
            stateTally.put("Deaths", 0);
            stateTally.put("Recovered", 0);
            stateTally.put("Active", 0);
            for (int i = 0; i < data.get("Lat").size(); i++) {
                if (data.get("Province_State").get(i).equals(stateName)) {
                    for (String category : new String[]{"Confirmed", "Deaths", "Recovered",
                            "Active"}) {
                        stateTally.put(category,
                                stateTally.get(category) + (Integer) data.get(category).get(i));
                    }
                }
            }
            // Once all the states are tallied up, transfer the stateTally to the correct entry
            // in the stateData hash map.
            for (String categoryKey : new String[]{"Confirmed", "Deaths", "Recovered", "Active"}) {
                stateData.get(categoryKey).add(stateTally.get(categoryKey));
            }
        }

        // Now that we've extracted all of the state data, we can go ahead and delete the county
        // data. Non-county data will have an empty string instead of an entry in "County", so we
        // can use that to identify the counties.
        List<Integer> moreRowsToDelete = new ArrayList<>();
        for (int i = 0; i < data.get("County").size(); i++) {
            if (!data.get("County").get(i).equals("")) {
                moreRowsToDelete.add(i);
            }
        }
        for (int i = moreRowsToDelete.size() - 1; i > 0; i--) {
            for (String category : CATEGORIES) {
                data.get(category).remove(moreRowsToDelete.get(i).intValue());
            }
        }

        // Finally, we need to combine data and stateData.
        for (String category : new String[]{"County", "Province_State", "Last_Update"}) {
            // We can go ahead and drop the unneeded categories
            data.remove(category);
            stateData.remove(category);
        }
        for (String category : data.keySet()) {
            for (int i = 0; i < stateData.get("Country_Region").size(); i++) {
                data.get(category).add(stateData.get(category).get(i));
            }
        }
    }

    public Map<String, List<Object>> getData() {
        return data;
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
            File[] localDataFiles = dataDirectory.listFiles();
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
            snackbar.dismiss();
        }

        // If we can't connect to github, this function determines what to do.
        private int findLocalData() {
            File[] oldFiles = dataDirectory.listFiles();
            assert oldFiles != null;
            if (oldFiles.length == 0) {
                return 1; // There is no file from a previous day
            } else {
                localData = oldFiles[0];
                isDataDownloaded = true;
                String name = localData.getName();
                name = name.substring(0, name.length() - 4);
                date = new Date(name);
                return 2; // There is a file from a previous day
            }
        }
    }
}
