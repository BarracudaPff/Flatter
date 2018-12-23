package com.barracudapff.hoobes.flatter.activities.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.barracudapff.hoobes.flatter.R;
import com.barracudapff.hoobes.flatter.adapters.AuthPagerAdapter;
import com.barracudapff.hoobes.flatter.fragments.auth.AuthSignBaseFragment;
import com.barracudapff.hoobes.flatter.fragments.auth.AuthSignInFragment;
import com.barracudapff.hoobes.flatter.fragments.auth.AuthSignUpFragment;
import com.barracudapff.hoobes.flatter.support.NonSwipeableViewPager;

public class AuthActivity extends AppCompatActivity
        implements AuthSignInFragment.OnAuthSignInFragmentInteractionListener
        , AuthSignUpFragment.OnAuthSignUpFragmentInteractionListener {

    AuthPagerAdapter adapter;
    NonSwipeableViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        adapter = new AuthPagerAdapter(getSupportFragmentManager());

        pager = findViewById(R.id.auth_main_content);
        pager.setAdapter(adapter);
        pager.setCurrentItem(0, false);
        //((AuthSignBaseFragment) adapter.getItem(0)).start();

        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                System.out.println("onPageSelected");
                ((AuthSignBaseFragment) adapter.getItem(position)).start();
            }
        });
    }

    @Override
    public void OnAuthSignInInteraction() {
        pager.setCurrentItem(1, false);
    }

    @Override
    public void OnAuthSignUpFragmentInteraction() {
        pager.setCurrentItem(0, false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println(requestCode +" "+resultCode);
        if (requestCode == 102) {
            if (resultCode == RESULT_OK) {
                System.out.println();
                finish();
            }
        }
    }
}
