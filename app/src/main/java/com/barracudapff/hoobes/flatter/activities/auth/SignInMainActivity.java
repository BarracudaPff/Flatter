package com.barracudapff.hoobes.flatter.activities.auth;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

import com.barracudapff.hoobes.flatter.MainActivity;
import com.barracudapff.hoobes.flatter.R;

public class SignInMainActivity extends AppCompatActivity {
    ObjectAnimator animatorUp, animatorDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_main);

        setUpCard(findViewById(R.id.cardViewGoogle), v ->
                Toast.makeText(this, "TODO: not avaibale now", Toast.LENGTH_SHORT).show());
        setUpCard(findViewById(R.id.cardViewInstagram), v ->
                Toast.makeText(this, "TODO: not avaibale now", Toast.LENGTH_SHORT).show());
        setUpCard(findViewById(R.id.cardViewVk), v ->
                Toast.makeText(this, "TODO: not avaibale now", Toast.LENGTH_SHORT).show());
        setUpCard(findViewById(R.id.cardViewMail), v ->
                MainActivity.basicStart(this, LogInMailActivity.class, 102));
        setUpCard(findViewById(R.id.sign_up_button), v ->
                MainActivity.basicStart(this, LogInMailActivity.class, 103));
    }

    public void animUp(CardView view) {
        animatorUp = ObjectAnimator.ofFloat(view, "cardElevation", 5, 20);
        animatorUp.setInterpolator(new FastOutLinearInInterpolator());
        animatorUp.start();
    }

    public void animDown(CardView view) {
        animatorDown = ObjectAnimator.ofFloat(view, "cardElevation", 20, 5);
        animatorDown.setInterpolator(new FastOutLinearInInterpolator());
        animatorDown.start();
    }

    public void setUpCard(View view, View.OnClickListener listener) {
        view.setOnClickListener(listener);
        /*view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean isUp;

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        animDown((CardView) v);
                        isUp = true;
                    }
                    case MotionEvent.ACTION_UP: {
                        //animUp((CardView) v);
                        return !isViewContains(v, event);
                    }
                    case MotionEvent.ACTION_MOVE: {
                        if (isViewContains(v, event)) {

                        } else {

                        }
                    }
                }
                if (isViewContains(v, event)) {
                    System.out.println("Yes");
                    return false;
                }
                return true;
            }

            private boolean isViewContains(View view, MotionEvent touch) {
                int[] location = new int[2];
                view.getLocationOnScreen(location);

                return !(touch.getRawX() < location[0])
                        && !(touch.getRawX() > location[0] + view.getWidth())
                        && !(touch.getRawY() < location[1])
                        && !(touch.getRawY() > location[1] + view.getHeight());
            }
        });*/


    }

}
