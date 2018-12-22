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

    protected ArrayList<Animation> animations;
    private Context context;

    protected int delay = 100;

    public void setUp(Context context) {
        this.context = context;
        animations = new ArrayList<>();

        animationStart = AnimationUtils.loadAnimation(context, R.anim.slide_up);
        animationStart.setInterpolator(new FastOutLinearInInterpolator());
        animationStart.setStartOffset(getOffset());
        animationStart.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                System.out.println("Started");
                for (Animation animation1 : animations) {
                    System.out.println("!");
                    //animation1.start();
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
        view.setAnimation(fadeAnim(getOffset()));
    }

    public void addAnimationStart(View view) {
        view.setAnimation(animationStart);
    }

    public void start() {
        System.out.println("start");
        //animationStart.startNow();
    }

    private Animation fadeAnim(int offset) {
        Animation animationCloned = AnimationUtils.loadAnimation(context, R.anim.slide_up);
        animationCloned.setInterpolator(new FastOutLinearInInterpolator());
        animationCloned.setStartOffset(offset);

        animations.add(animationCloned);
        return animationCloned;
    }

    private int getOffset() {
        delay += 200;
        return delay;
    }
}
