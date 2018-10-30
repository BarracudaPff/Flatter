package com.barracudapff.hoobes.flatter.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.barracudapff.hoobes.flatter.MainActivity;
import com.barracudapff.hoobes.flatter.R;

public class LogInActivity extends AppCompatActivity {
    FloatingActionButton fab;
    Animation animation, animFadeIn, animFadeInWithListener, animationCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        fab = findViewById(R.id.fab_log_in);
        TextView textSingUp = findViewById(R.id.txt_sing_up);
        TextView textSingUpLabel = findViewById(R.id.log_in_label);
        CardView cardView = findViewById(R.id.card_log_container);
        ImageView imageView = findViewById(R.id.iv_log_in_logo);

        textSingUp.setOnClickListener(v -> MainActivity.basicStart(this, SingUpActivity.class));
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        animFadeInWithListener = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        animationCard = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);

        animFadeInWithListener.setAnimationListener(new Animation.AnimationListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);

                fab.startAnimation(LogInActivity.this.animation);
                cardView.startAnimation(LogInActivity.this.animationCard);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        //textSingUpLabel.startAnimation(animFadeInWithListener);

        fab.setOnClickListener(view -> {
            fab.startAnimation(animation);
            cardView.startAnimation(animationCard);
        });

        //new Handler().post(() -> fab.startAnimation(animation));
        //fab.startAnimation(animation);
        final ConstraintLayout layout = findViewById(R.id.id);
        imageView.setAnimation(animFadeIn);
        layout.getViewTreeObserver().addOnGlobalLayoutListener(() -> textSingUp.startAnimation(animFadeInWithListener));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}