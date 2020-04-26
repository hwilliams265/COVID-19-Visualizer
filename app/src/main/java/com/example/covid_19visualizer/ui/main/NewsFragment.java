package com.example.covid_19visualizer.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.covid_19visualizer.R;

import org.jetbrains.annotations.NotNull;

/**
 * File created by: Harry
 *
 * This class contains everything needed for the news_fragment.xml page.
 */
public class NewsFragment extends Fragment {

    // See InfoFragment for info about the newInstance() method
//    @NotNull
//    public static NewsFragment newInstance() {
//        Bundle bundle = new Bundle();
//        NewsFragment fragment = new NewsFragment();
//        fragment.setArguments(bundle);
//        return fragment;
//    }

    // onCreateView() is automatically run after onCreate() when the fragment is called to view.
    // It returns a View object that displays the news interface.
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.news_fragment, container, false);
    }
}
