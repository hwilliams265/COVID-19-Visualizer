package com.example.covid_19visualizer;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import android.view.View;

import com.example.covid_19visualizer.ui.main.SectionsPagerAdapter;

public class MainActivity extends FragmentActivity {

    static SectionsPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.view_pager);
//        SectionsPagerAdapter adapter = new SectionsPagerAdapter(this,
//                                                                getSupportFragmentManager());
        adapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        viewPager.setAdapter(adapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"This could be a link to a coronavirus symptom checker or" +
                        " something, idk", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}