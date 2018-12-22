package com.barracudapff.hoobes.flatter.support;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.barracudapff.hoobes.flatter.R;

import java.util.ArrayList;

public class StepAnimation {
    private Animation fadeInAnimation, animationStart;

    private ArrayList<View> views;
    private Context context;

    public StepAnimation(Context context) {
        this.context = context;
        views = new ArrayList<>();
        fadeInAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        animationStart = AnimationUtils.loadAnimation(context, R.anim.fade_in);
    }

    public void addAnimationStep(View view) {
        views.add(view);
        view.startAnimation(fadeInAnimation);
    }

    public void addAnimationStart(View view) {
        views.add(view);
        view.startAnimation(animationStart);
    }

    public void start() {
        System.out.println(views.size());
        for (View view : views) {
            view.animate();
        }
    }
}
