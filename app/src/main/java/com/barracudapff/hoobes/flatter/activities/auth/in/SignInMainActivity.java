package com.barracudapff.hoobes.flatter.activities.auth.in;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.barracudapff.hoobes.flatter.MainActivity;
import com.barracudapff.hoobes.flatter.R;
import com.barracudapff.hoobes.flatter.activities.auth.up.SignUpMainActivity;

/**
 * Start of auth request.
 */
public class SignInMainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_main);

        setUpCard(findViewById(R.id.cardViewGoogle), v ->
                Toast.makeText(this, "TODO: not available now", Toast.LENGTH_SHORT).show());
        setUpCard(findViewById(R.id.cardViewInstagram), v ->
                Toast.makeText(this, "TODO: not available now", Toast.LENGTH_SHORT).show());
        setUpCard(findViewById(R.id.cardViewVk), v ->
                Toast.makeText(this, "TODO: not available now", Toast.LENGTH_SHORT).show());
        setUpCard(findViewById(R.id.cardViewMail), v ->
                MainActivity.basicStart(this, LogInMailActivity.class, 102));
        setUpCard(findViewById(R.id.sign_up_button), v ->
                MainActivity.basicStart(this, SignUpMainActivity.class, 103));
    }

    public void setUpCard(View view, View.OnClickListener listener) {
        view.setOnClickListener(listener);
    }

}
