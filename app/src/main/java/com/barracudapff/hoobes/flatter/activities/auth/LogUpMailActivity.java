package com.barracudapff.hoobes.flatter.activities.auth;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.barracudapff.hoobes.flatter.R;

public class LogUpMailActivity extends AppCompatActivity {
    public static int CODE_SIGN_UP = 102;

    FloatingActionButton fab;
    Animation animation, animFadeIn, animFadeInWithListener, animationCard;
    ConstraintLayout layout;

    ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener;

    boolean isCreated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_up_mail);

        fab = findViewById(R.id.fab_log_in);
        CardView cardView = findViewById(R.id.card_log_container);
        ImageView imageView = findViewById(R.id.iv_log_in_logo);

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

                fab.startAnimation(LogUpMailActivity.this.animation);
                cardView.startAnimation(LogUpMailActivity.this.animationCard);
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

        //new Handler().post(() -> fab.startAnimation(animation));
        //fab.startAnimation(animation);
        layout = findViewById(R.id.id);
        imageView.setAnimation(animFadeIn);
        onGlobalLayoutListener = () -> {
            if (!isCreated) {
                imageView.startAnimation(animFadeInWithListener);
                isCreated = true;
            }
        };
        layout.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        layout.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
        fab.setOnClickListener(null);
        animFadeInWithListener.setAnimationListener(null);
        layout = null;
        fab = null;
        onGlobalLayoutListener = null;
    }

}
