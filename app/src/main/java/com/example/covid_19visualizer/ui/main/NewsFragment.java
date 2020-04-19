package com.example.covid_19visualizer.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.covid_19visualizer.R;

import org.jetbrains.annotations.NotNull;

public class NewsFragment extends Fragment {

    @NotNull
    public static NewsFragment newInstance() {
        Bundle bundle = new Bundle();
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.news_fragment, container, false);
        return rootView;
    }
}
