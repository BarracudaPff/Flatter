package com.barracudapff.hoobes.flatter.adapters;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.barracudapff.hoobes.flatter.fragments.auth.AuthSignInFragment;
import com.barracudapff.hoobes.flatter.fragments.auth.AuthSignUpFragment;

public class AuthPagerAdapter extends FragmentPagerAdapter {
    private AuthSignInFragment signInFragment;
    private AuthSignUpFragment signUpFragment;

    public AuthPagerAdapter(FragmentManager fm) {
        super(fm);
        signInFragment = AuthSignInFragment.newInstance();
        signUpFragment = AuthSignUpFragment.newInstance();
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return signInFragment;
            case 1:
                return signUpFragment;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}


