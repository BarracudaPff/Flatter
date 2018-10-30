package com.barracudapff.hoobes.flatter.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.barracudapff.hoobes.flatter.MainActivity;
import com.barracudapff.hoobes.flatter.R;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        TextView textSingUp = findViewById(R.id.txt_sing_up);
        textSingUp.setOnClickListener(v -> MainActivity.basicStart(this, SingUpActivity.class));

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up);

        FloatingActionButton fab = findViewById(R.id.fab_log_in);
        fab.setOnClickListener(view -> {
            fab.startAnimation(animation);
        });
    }

}
