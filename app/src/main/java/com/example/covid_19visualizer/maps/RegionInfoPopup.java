package com.example.covid_19visualizer.maps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.covid_19visualizer.R;

/*
 * This class creates popup windows when a circle is pressed on map_fragment
 */
public class RegionInfoPopup extends Activity {
    String region;
    int confirmed;
    int active;
    int recovered;
    int deaths;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        this.region = intent.getStringExtra("REGION");
        this.confirmed = intent.getIntExtra("CONFIRMED", -1);
        this.active = intent.getIntExtra("ACTIVE", -1);
        this.recovered = intent.getIntExtra("RECOVERED", -1);
        this.deaths = intent.getIntExtra("DEATHS", -1);

        setContentView(R.layout.region_info_popup);
        TextView text = findViewById(R.id.regionInfoText);

        text.setText(getString(R.string.popup_display_text, region, confirmed, active, recovered,
         deaths));

        ConstraintLayout layout = findViewById(R.id.regionInfoLayout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegionInfoPopup.this.finish();
            }
        });
    }
}
