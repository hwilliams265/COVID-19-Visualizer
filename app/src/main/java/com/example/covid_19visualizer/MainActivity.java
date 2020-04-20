package com.example.covid_19visualizer;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import android.view.View;

import com.example.covid_19visualizer.ui.main.SectionsPagerAdapter;


/**
 * File created by: System
 *
 * This class contains everything needed for the activity_main.xml page. Because it is the
 * designated as the main class, it (specifically onCreate()) will automatically run when the app
 * starts.
 */
public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Run the parent class (FragmentActivity) onCreate method
        super.onCreate(savedInstanceState);

        // Set the view as activity_main.xml
        setContentView(R.layout.activity_main);

        // Instantiate the viewPager object. This is the object that switches between the map,
        // info, and news fragments in the main screen.
        ViewPager viewPager = findViewById(R.id.view_pager);

        // Instantiate the adapter object. This is a user-created class (in ui.main) that tells
        // the viewPager which fragment corresponds to which tab (eg. the INFO tab corresponds to
        // the InfoFragment class)
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(this,
                                                                getSupportFragmentManager());

        // Assign the adapter to the viewPager
        viewPager.setAdapter(adapter);

        // Link the tabs xml object with the tabs java object
        TabLayout tabs = findViewById(R.id.tabs);

        // Assign the viewPager object to the tabs object, so the view can be switched using the
        // tabs.
        tabs.setupWithViewPager(viewPager);

        // Link the fab xml object (the button in the bottom right corner) with the fab java object
        FloatingActionButton fab = findViewById(R.id.fab);

        // the setOnClickListener() method of fab takes a function as an input, so we're writing
        // the function inline here. As of now all this does is display a line of text when you
        // press the button, but we'll probably want it to redirect the user to a new page.
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Snackbar is Google's class that creates reminder popups when an action is done.
                Snackbar.make(view,"This could be a link to a coronavirus symptom checker or" +
                        " something, idk", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}