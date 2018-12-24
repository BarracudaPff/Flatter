package com.barracudapff.hoobes.flatter.activities.party;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.barracudapff.hoobes.flatter.R;
import com.barracudapff.hoobes.flatter.database.models.User;
import com.google.android.gms.maps.model.LatLng;

public class CreatePartyActivity extends AppCompatActivity {

    LatLng position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_party);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        position = new LatLng(getIntent().getDoubleExtra("POSITION_LAT", 0),
                getIntent().getDoubleExtra("POSITION_LON", 0));

        System.out.println(User.getCurrent(getBaseContext()));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
