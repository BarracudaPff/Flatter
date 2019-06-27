package com.barracudapff.hoobes.flatter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.barracudapff.hoobes.flatter.fragments.ChatListFragment;
import com.barracudapff.hoobes.flatter.fragments.MapFragment;
import com.barracudapff.hoobes.flatter.fragments.PartyListFragment;
import com.barracudapff.hoobes.flatter.fragments.PersonFragment;
import com.barracudapff.hoobes.flatter.fragments.SignInFragment;
import com.barracudapff.hoobes.flatter.fragments.SocialFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private ChatListFragment chatListFragment;
    private MapFragment mapFragment;
    private PartyListFragment partyListFragment;
    private PersonFragment personFragment;
    private SocialFragment socialFragment;
    private Fragment active;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragments();
        setUpNavigationBar();
        switchToFragment(socialFragment);

    }

    /**
     * Simple NavBar setup
     */
    private void setUpNavigationBar() {
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
    }

    /**
     * Simple fragment initialization.
     */
    private void initFragments() {
        chatListFragment = ChatListFragment.newInstance();
        mapFragment = MapFragment.newInstance();
        partyListFragment = PartyListFragment.newInstance();
        socialFragment = SocialFragment.newInstance();
        personFragment = PersonFragment.newInstance();
    }

    /**
     * Switching main screens (Fragments)
     *
     * @param fragment Switch to existing or create new fragment
     */
    public void switchToFragment(Fragment fragment) {
        if (getSupportFragmentManager().findFragmentByTag(fragment.getClass().getSimpleName()) != null) {
            getSupportFragmentManager().beginTransaction()
                    .hide(active)
                    .show(fragment)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.screen_frame, fragment, fragment.getClass().getSimpleName())
                    .commit();
        }

        active = fragment;
    }

    /**
     * Listen NavBar clicks
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_social:
                switchToFragment(socialFragment);
                return true;
            case R.id.navigation_chat_list:
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    switchToFragment(chatListFragment);
                } else {
                    switchToFragment(SignInFragment.newInstance());
                }
                return true;
            case R.id.navigation_map:
                switchToFragment(mapFragment);
                Objects.requireNonNull(getSupportActionBar()).hide();
                return true;
            case R.id.navigation_party_list:
                switchToFragment(partyListFragment);
                return true;
            case R.id.navigation_person:
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    switchToFragment(personFragment);
                } else {
                    switchToFragment(SignInFragment.newInstance());
                }
                return true;
        }
        return false;
    }

}