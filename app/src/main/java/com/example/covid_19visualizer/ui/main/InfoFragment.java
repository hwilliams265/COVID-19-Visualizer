package com.example.covid_19visualizer.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.covid_19visualizer.R;

import org.jetbrains.annotations.NotNull;

/**
 * File created by: Harry
 *
 * This class contains everything needed for the info_fragment.xml page.
 */
public class InfoFragment extends Fragment {

//    The newInstance() method below provides an alternate way to instantiate objects of this class.
//    I've commented it out because it isn't currently used in this app but it might be useful in
//    the future.
//    @NotNull
//    public static InfoFragment newInstance() {
//        Bundle bundle = new Bundle();
//        InfoFragment fragment = new InfoFragment();
//        fragment.setArguments(bundle);
//        return fragment;
//    }

    // onCreateView() is automatically run after onCreate() when the fragment is called to view.
    // It returns a View object that displays the info interface.
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.info_fragment, container, false);
    }
}
