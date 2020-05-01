package com.example.covid_19visualizer.maps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.covid_19visualizer.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by: Harry
 * <p>
 * This class contains everything needed for the maps_fragment.xml page.
 * A lot of the code here was taken from various stackoverflow threads, so I can't claim to know
 * exactly how it all works.
 */
public class MapsFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private CovidData covidData;
    private FloatingActionButton fabExpand;
    private FloatingActionButton fabDeaths;
    private FloatingActionButton fabRecovered;
    private FloatingActionButton fabActive;
    private FloatingActionButton fabConfirmed;
    private TextView deaths;
    private TextView recovered;
    private TextView active;
    private TextView confirmed;
    private GoogleMap googleMap;
    private GoogleApiClient googleApiClient;

    // See SectionsPagerAdapter for info about the Context class
    private Context context;

    private Map<String, List<Circle>> plottedCircles = null;

    // onAttach() is automatically run before onCreate()
    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    // onCreateView() is automatically run after onCreate() when the fragment is called to view.
    // It returns a View object that displays the maps interface.
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.maps_fragment, container, false);
    }

    // onViewCreated() is automatically run immediately after onCreateView(), prior to any changes
    // to the view being reflected on the user's end. We use it to get a map fragment, which we
    // will later instantiate in onMapReady().
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Instantiates the mapFragment object and links it to the map xml object
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        // Set a callback object to trigger when the map is ready to use
        mapFragment.getMapAsync(this);
        if (googleApiClient != null) {
            googleApiClient.connect();
        }

        fabExpand = view.findViewById(R.id.fabExpand);
        fabDeaths = view.findViewById(R.id.fabDeaths);
        fabRecovered = view.findViewById(R.id.fabRecovered);
        fabActive = view.findViewById(R.id.fabActive);
        fabConfirmed = view.findViewById(R.id.fabConfirmed);

        deaths = view.findViewById(R.id.textDeaths);
        recovered = view.findViewById(R.id.textRecovered);
        active = view.findViewById(R.id.textActive);
        confirmed = view.findViewById(R.id.textConfirmed);

        // When the green button in the bottom left is clicked, toggle the visibility of the 4
        // modal buttons above it and their text.
        fabExpand.setOnClickListener(new View.OnClickListener() {
            private boolean isExpanded = true;

            @Override
            public void onClick(View v) {
                int viewMode;
                if (isExpanded) {
                    viewMode = View.GONE;
                } else {
                    viewMode = View.VISIBLE;
                }
                isExpanded = !isExpanded;

                fabDeaths.setVisibility(viewMode);
                fabRecovered.setVisibility(viewMode);
                fabActive.setVisibility(viewMode);
                fabConfirmed.setVisibility(viewMode);

                deaths.setVisibility(viewMode);
                recovered.setVisibility(viewMode);
                active.setVisibility(viewMode);
                confirmed.setVisibility(viewMode);
            }
        });

        fabDeaths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCircles("Deaths");
                toggleFabBackground(fabDeaths, 0xff760000, R.drawable.deaths_custom_foreground,
                        R.drawable.deaths_custom_light_foreground);
            }
        });
        fabRecovered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCircles("Recovered");
                toggleFabBackground(fabRecovered, 0xff3a9dbd,
                        R.drawable.recovered_custom_foreground,
                        R.drawable.recovered_custom_light_foreground);
            }
        });
        fabActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCircles("Active");
                toggleFabBackground(fabActive, 0xffe0e000, R.drawable.active_custom_foreground,
                        R.drawable.active_custom_light_foreground);
            }
        });
        fabConfirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCircles("Confirmed");
                toggleFabBackground(fabConfirmed, 0xff00bb25,
                        R.drawable.confirmed_custom_foreground,
                        R.drawable.confirmed_custom_light_foreground);
            }
        });

        // Sometimes, the view will get re-created without the app restarting. When this happens,
        // we want to reset the displayed circles (the button states get reset automatically).
        if (plottedCircles != null) {
            for (String category : new String[]{"Deaths", "Recovered", "Active"}) {
                // If only 1 element is visible, we know that all the elements will be visible
                // (and vice-versa).
                if (plottedCircles.get(category).get(0).isVisible()) {
                    toggleCircles(category);
                }
            }
            if (!plottedCircles.get("Confirmed").get(0).isVisible()) {
                toggleCircles("Confirmed");
            }
        }
    }

    // Triggered when one of the four mini buttons is pressed. Toggles the color from light to
    // dark, or dark to light.
    private void toggleFabBackground(FloatingActionButton fab, int darkColor, int darkIcon,
                                     int lightIcon) {
        if (fab.getBackgroundTintList().equals(ColorStateList.valueOf(0xffffffff))) {
            fab.setBackgroundTintList(ColorStateList.valueOf(darkColor));
            fab.setImageResource(lightIcon);
        } else {
            fab.setBackgroundTintList(ColorStateList.valueOf(0xffffffff));
            fab.setImageResource(darkIcon);
        }
    }

    // Instantiate the GoogleMap and the GoogleApiClient
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            googleApiClient.connect();
        }
    }

    @Override
    public void onStart() {
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
        super.onStart();
    }

    // onConnected() is executed when googleApiClient.connect() is returns. We use it to load the
    // .csv containing the COVID-19 data into a hash map, and to draw all of the circles on the
    // map (but make them all invisible). This slows the start-up time slightly, but it makes the
    // app run much smoother once its started up.
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // If the permissions needed to run the app are granted...
        if (ActivityCompat.checkSelfPermission(context,
                android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {

            initializeCovidData();
            initializePlottedCircles();
            toggleCircles("Confirmed");

            LatLng nyc = new LatLng(41, -74);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(nyc));
        }
    }

    // Initialize a CovidData object and download the data if necessary
    private void initializeCovidData() {
        covidData = new CovidData(context);
//        covidData.deleteData();
        int downloadResult = 0;
        if (!covidData.isDataDownloaded()) {
            downloadResult = covidData.downloadData();
            // This next line is horrible programming practice, but I'm not really sure how else
            // to fix the issue within the scope of this project. Android requires data downloads
            // to happen within an AysncTask, but this means that the data is initialized before
            // the data is downloaded, resulting in an error. This while() statement will force
            // the program to wait until the data is downloaded before continuing.
            while(!covidData.isDataDownloaded());
        }
        Snackbar snackbar = Snackbar.make(((Activity) context).findViewById(android.R.id.content),
                "", Snackbar.LENGTH_LONG);
        switch (downloadResult) {
            case 2: // Download failed, but we have old data in the system memory
                snackbar.setText("Warning: Could not fetch new data. using data from" +
                        covidData.localData.getName() + ".").show();
            case 0: // Download succeeded!
                covidData.initializeData();
                break;
            case 1: // Download failed with no backup
                snackbar.setText("Error: Could not fetch new data.").show();
        }
    }

    // Create objects for all of the circles, but don't draw them yet. Must be run before
    // toggleCircles().
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initializePlottedCircles() throws IllegalStateException {
        Snackbar snackbar = Snackbar.make(((Activity) context).findViewById(android.R.id.content),
                "Initializing the map...", Snackbar.LENGTH_INDEFINITE);
        snackbar.show();

        final Map<String, List<Object>> data = covidData.getData();
        int scalingFactor = 1000;
        plottedCircles = new ArrayMap<>();
        for (String category : new String[]{"Confirmed", "Active", "Recovered", "Deaths"}) {
            plottedCircles.put(category, new ArrayList<Circle>());
            String fillColor;
            String edgeColor;
            switch (category) {
                case "Confirmed":
                    fillColor = "#4080ff99";
                    edgeColor = "#ff00320a";
                    break;
                case "Active":
                    fillColor = "#40ffffb9";
                    edgeColor = "#ff929200";
                    break;
                case "Recovered":
                    fillColor = "#40add8e6";
                    edgeColor = "#ff3a9dbd";
                    break;
                case "Deaths":
                    fillColor = "#40ff9d9d";
                    edgeColor = "#ff760000";
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + category);
            }
            for (int i = 0; i < data.get(category).size(); i++) {
                CircleOptions circleOptions = new CircleOptions()
                        .center(new LatLng((double) data.get("Lat").get(i),
                                (double) data.get("Long").get(i)))
                        .radius(Math.sqrt((double) ((Integer) data.get(category).get(i)).intValue())
                                * scalingFactor)
                        .fillColor(Color.parseColor(fillColor))
                        .strokeColor(Color.parseColor(edgeColor))
                        .strokeWidth(2)
                        .visible(false);
                plottedCircles.get(category).add(googleMap.addCircle(circleOptions));
                plottedCircles.get(category).get(i).setTag(i);
                // The z-index determines the order of the circles on the map. If a circle
                // overlaps another circle, the circle with the higher z-index will be clickable.
                // We always want smaller circles to have a higher z-index than larger circles so
                // that even completely overlapped circles will be clickable. 
                plottedCircles.get(category).get(i)
                        .setZIndex(-((Integer) data.get("Confirmed").get(i)).floatValue());
            }
        }

        // When a circle is clicked, send the info about the circle to the RegionInfoPopup
        // activity and start that activity.
        googleMap.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {
            @Override
            public void onCircleClick(Circle circle) {
                if (circle.isVisible()) {
                    Intent intent = new Intent(MapsFragment.this.getActivity(),
                            RegionInfoPopup.class);
                    int index = (int) circle.getTag();
                    intent.putExtra("REGION", (String) data.get("Country_Region").get(index));
                    intent.putExtra("CONFIRMED", (int) data.get("Confirmed").get(index));
                    intent.putExtra("ACTIVE", (int) data.get("Active").get(index));
                    intent.putExtra("RECOVERED", (int) data.get("Recovered").get(index));
                    intent.putExtra("DEATHS", (int) data.get("Deaths").get(index));

                    startActivity(intent);
                }
            }
        });

        snackbar.dismiss();
    }

    // Draw the (already-instantiated) circles of the inputted category.
    private void toggleCircles(String category) {
        for (Circle circle : plottedCircles.get(category)) {
            circle.setVisible(!circle.isVisible());
            circle.setClickable(!circle.isClickable());
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

}
