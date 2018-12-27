package com.barracudapff.hoobes.flatter.activities.settings;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.barracudapff.hoobes.flatter.R;
import com.barracudapff.hoobes.flatter.adapters.SettingsPagerAdapter;
import com.barracudapff.hoobes.flatter.fragments.settings.SettingsBaseFragment;
import com.barracudapff.hoobes.flatter.support.NonSwipeableViewPager;

public class ProfileSettingsActivity extends AppCompatActivity {
    SettingsPagerAdapter adapter;
    NonSwipeableViewPager pager;

    int current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter = new SettingsPagerAdapter(getSupportFragmentManager());

        pager = findViewById(R.id.auth_main_content);
        pager.setAdapter(adapter);

        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                System.out.println("onPageSelected");
                try {
                    current = position;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        pager.setCurrentItem(0, false);
    }

    public void changePage(int pos) {
        pager.setCurrentItem(pos, false);
            ((SettingsBaseFragment) adapter.getItem(pos)).update();
    }

    public Toolbar getBar() {
        return findViewById(R.id.toolbar);
    }

    @Override
    public void onBackPressed() {
        System.out.println("Back");
        if (current == 0)
            super.onBackPressed();
        else
            changePage(0);
    }
}
