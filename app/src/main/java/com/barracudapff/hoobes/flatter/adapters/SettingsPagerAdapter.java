package com.barracudapff.hoobes.flatter.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.barracudapff.hoobes.flatter.fragments.settings.SettingsAboutFragment;
import com.barracudapff.hoobes.flatter.fragments.settings.SettingsAgeFragment;
import com.barracudapff.hoobes.flatter.fragments.settings.SettingsListFragment;
import com.barracudapff.hoobes.flatter.fragments.settings.SettingsNamesFragment;
import com.barracudapff.hoobes.flatter.fragments.settings.SettingsPasswordFragment;

public class SettingsPagerAdapter extends FragmentStatePagerAdapter {
    private SettingsListFragment settingsListFragment;
    private SettingsAgeFragment settingsAgeFragment;
    private SettingsNamesFragment settingsNamesFragment;
    private SettingsPasswordFragment settingsPasswordFragment;
    private SettingsAboutFragment settingsAboutFragment;

    public SettingsPagerAdapter(FragmentManager fm) {
        super(fm);
        settingsListFragment = SettingsListFragment.newInstance();
        settingsNamesFragment = SettingsNamesFragment.newInstance();
        settingsPasswordFragment = SettingsPasswordFragment.newInstance();
        settingsAgeFragment = SettingsAgeFragment.newInstance();
        settingsAboutFragment = SettingsAboutFragment.newInstance();
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return settingsListFragment;
            case 1:
                return settingsNamesFragment;
            case 2:
                return settingsPasswordFragment;
            case 3:
                return settingsAgeFragment;
            case 4:
                return settingsAboutFragment;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
