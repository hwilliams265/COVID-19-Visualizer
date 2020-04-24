package com.example.covid_19visualizer.ui.main;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.covid_19visualizer.CovidData;
import com.example.covid_19visualizer.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;

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

    private GoogleMap googleMap;

    private GoogleApiClient googleApiClient;

    // See SectionsPagerAdapter for info about the Context class
    private Context context;

    // See InfoFragment for more info about the newInstance() method
//    @NotNull
//    public static MapsFragment newInstance() {
//        Bundle bundle = new Bundle();
//        MapsFragment fragment = new MapsFragment();
//        fragment.setArguments(bundle);
//        return fragment;
//    }

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
    // to the view being reflected on the user's end.
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {

        Log.d("CREATION", "OnViewCreated() is being executed");

        super.onViewCreated(view, savedInstanceState);
        // Instantiates the mapFragment object and links it to the map xml object
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        // raise an exception if mapFragment is null (makes bug checking easier)
        assert mapFragment != null;
        // Set a callback object to trigger when the map is ready to use
        mapFragment.getMapAsync(this);

        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.d("CREATION", "OnMapReady() is being executed");

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

    // onConnected() is executed when googleApiClient.connect() is run.
    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Log.d("CREATED", "onConnected() being executed");

        // If the permissions needed to run the app are granted, ...
        if (ActivityCompat.checkSelfPermission(context,
                android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {

            Log.d("CREATION", "maps permissions passed!");

            initializeCovidData();
            try {
                plotCases("Active");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }

            LatLng paris = new LatLng(48, 2);
            googleMap.addMarker(new MarkerOptions().position(paris).title("Marker in Paris"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(paris));
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    // Initialize a CovidData object and download the data if necessary
    private void initializeCovidData() {
        covidData = new CovidData(context);
//        covidData.deleteData();
        int downloadResult;
        if (!covidData.isDataDownloaded()) {
            downloadResult = covidData.downloadData();
            Log.d("MY_RESULT", String.valueOf(downloadResult));
        }
        Log.d("MY_METHOD", "initializing the data...");
        covidData.initializeData();
        Log.d("MY_METHOD", "Finished initializing the data.");
    }


    // create circles on the map correlated to the number of cases of the category stat.
    // Stat can be "Confirmed" "Deaths" "Recovered" or "Active"
    private void plotCases(@NotNull String stat) throws NoSuchFieldException {
        int scalingFactor;
        String fillColor;
        String edgeColor;
        switch (stat) {
            case "Confirmed":
                // The header for the dataframe is:
                // (0) Lat; (1) Long_; (2) Confirmed; (3) Deaths; (4) Recovered; (5) Active;
//                fillColor = Color.parseColor(String.valueOf(R.color.confirmed_fill));
//                edgeColor = Color.parseColor(String.valueOf(R.color.confirmed_edge));
//                 fillColor = "#80ff99";
//                 edgeColor = "#006c15";
                fillColor = "#4080ff99";
                edgeColor = "#ff00320a";
                scalingFactor = 1000;
                break;
            case "Deaths":
                fillColor = "#40ff9d9d";
                edgeColor = "#ff760000";
                scalingFactor = 1000;
                break;
            case "Recovered":
                fillColor = "#40add8e6";
                edgeColor = "#ff3a9dbd";
                scalingFactor = 1000;
                break;
            case "Active":
                fillColor = "#40ffffb9";
                edgeColor = "#ff929200";
                scalingFactor = 1000;
                break;
            default:
                throw new NoSuchFieldException("\"" + stat + "\" is not a valid stat. " +
                        "Acceptable stats are: \"Confirmed\" \"Deaths\" \"Recovered\" or " +
                        "\"Active\".");
        }

//        LatLngBounds bounds;
//        GroundOverlay circleOverlay;
//        BitmapDescriptor imageDescriptor =
//                BitmapDescriptorFactory.fromResource(R.drawable.circle_overlay);



        Map<String, List<Object>> data = covidData.getData();
        for (int i = 0; i < data.get(stat).size(); i++) {
//            bounds = new LatLngBounds(LatLng())
//            circleOverlay = googleMap.addGroundOverlay()


            CircleOptions circleOptions = new CircleOptions()
                    .center(new LatLng((double) data.get("Lat").get(i),
                            (double) data.get("Long").get(i)))
                    .radius(Math.sqrt((double) ((Integer) data.get(stat).get(i)).intValue()) * scalingFactor)
                    .fillColor(Color.parseColor(fillColor))
                    .strokeColor(Color.parseColor(edgeColor))
                    .strokeWidth(2);
            Circle circle = googleMap.addCircle(circleOptions);
        }
    }
}

