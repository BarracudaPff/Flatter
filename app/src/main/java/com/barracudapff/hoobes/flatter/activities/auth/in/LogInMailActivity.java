package com.barracudapff.hoobes.flatter.activities.auth.in;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.barracudapff.hoobes.flatter.R;

public class LogInMailActivity extends AppCompatActivity {
    public static int CODE_SIGN_UP = 102;

    FloatingActionButton fab;
    Animation animation, animFadeIn, animFadeInWithListener, animationCard;
    ImageView imageView;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_mail);

        fab = findViewById(R.id.fab_log_in);
        cardView = findViewById(R.id.card_log_container);
        imageView = findViewById(R.id.iv_log_in_logo);

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

                fab.startAnimation(LogInMailActivity.this.animation);
                cardView.startAnimation(LogInMailActivity.this.animationCard);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        fab.setOnClickListener(view -> {
            fab.startAnimation(animation);
            cardView.startAnimation(animationCard);
        });

        imageView.setAnimation(animFadeIn);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            imageView.startAnimation(animFadeInWithListener);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        fab.setOnClickListener(null);
        animFadeInWithListener.setAnimationListener(null);
        fab = null;
    }
}