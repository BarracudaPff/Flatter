package com.barracudapff.hoobes.flatter.fragments.auth;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.barracudapff.hoobes.flatter.R;

import java.util.ArrayList;

public class AuthSignBaseFragment extends Fragment {
    private Animation animationStart;

    protected View startView;
    protected ArrayList<View> views;
    protected ArrayList<Animation> animations;
    private Context context;

    protected int delay = 00;

    public void setUp(Context context) {
        this.context = context;
        views = new ArrayList<>();
        animations = new ArrayList<>();

        animationStart = AnimationUtils.loadAnimation(context, R.anim.slide_up);
        animationStart.setInterpolator(new FastOutLinearInInterpolator());
        animationStart.setStartOffset(getOffset());
        animationStart.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                for (Animation animation1 : animations) {
                    animation1.start();
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    public void addAnimationStep(View view) {
        Animation anim = fadeAnim(getOffset());
        view.setAnimation(anim);
        views.add(view);
        animations.add(anim);
    }

    public void addAnimationStart(View view) {
        view.setAnimation(animationStart);
        startView = view;
    }

    public void start() {
        for (int i = 0; i < views.size(); i++) {
            views.get(i).setAnimation(animations.get(i));
        }

        startView.startAnimation(animationStart);
    }

    private Animation fadeAnim(int offset) {
        Animation animationCloned = AnimationUtils.loadAnimation(context, R.anim.slide_up);
        animationCloned.setInterpolator(new FastOutLinearInInterpolator());
        animationCloned.setStartOffset(offset);

        return animationCloned;
    }

    private int getOffset() {
        delay += 100;
        return delay;
    }
}
