package com.barracudapff.hoobes.flatter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.barracudapff.hoobes.flatter.database.models.User;
import com.barracudapff.hoobes.flatter.fragments.screens.ChatListFragment;
import com.barracudapff.hoobes.flatter.fragments.screens.MapFragment;
import com.barracudapff.hoobes.flatter.fragments.screens.PartyListFragment;
import com.barracudapff.hoobes.flatter.fragments.screens.PersonFragment;
import com.barracudapff.hoobes.flatter.fragments.screens.SignInFragment;
import com.barracudapff.hoobes.flatter.fragments.screens.SocialFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener,
        PersonFragment.OnSignOutInterface {

    BottomNavigationView navigationView;

    private ChatListFragment chatListFragment;
    private MapFragment mapFragment;
    private PartyListFragment partyListFragment;
    private PersonFragment personFragment;
    private SocialFragment socialFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragments();
        setUpNavigationBar();
        switchToFragment(socialFragment);

        Toolbar mActionBarToolbar = findViewById(R.id.toolbar);
        mActionBarToolbar.setTitle(R.string.app_name);
        setSupportActionBar(mActionBarToolbar);
    }

    /**
     * Simple NavBar setup
     */
    private void setUpNavigationBar() {
        navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    /**
     * Simple fragment initialization.
     */
    private void initFragments() {
        chatListFragment = ChatListFragment.newInstance();
        mapFragment = MapFragment.newInstance();
        partyListFragment = PartyListFragment.newInstance();
        socialFragment = SocialFragment.newInstance();
        personFragment = new PersonFragment();
    }

    /**
     * Switching main screens (Fragments)
     * Fragments have saved instances.
     *
     * @param fragment Switch to existing or create new fragment.
     */
    public void switchToFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.screen_frame, fragment)
                .commit();
    }

    /**
     * Starts new activity with code {@param code}
     * @param context Parent Activity
     * @param activity New Activity
     * @param code request code
     */
    public static void basicStart(Activity context, Class activity, int code) {
        Intent starter = new Intent(context, activity);
        context.startActivityForResult(starter,code);
    }

    /**
     * Listen NavBar clicks
     *
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
                    System.out.println("chatListFragment");
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
                    System.out.println("personFragment");
                    switchToFragment(personFragment);
                } else {
                    switchToFragment(SignInFragment.newInstance());
                }
                return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 102) {
            if (resultCode == RESULT_OK) {
                System.out.println(navigationView.getSelectedItemId());
                switch (navigationView.getSelectedItemId()) {
                    case R.id.navigation_chat_list:
                        switchToFragment(chatListFragment);
                        return;
                    case R.id.navigation_person:
                        switchToFragment(personFragment);
                }
            }
        }
        if (requestCode == 103) {
            if (resultCode == RESULT_OK) {
                System.out.println("#########\"#########\"#########");
                FirebaseDatabase.getInstance().getReference()
                        .child("users")
                        .child(FirebaseAuth.getInstance().getUid())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                personFragment.updateUI(dataSnapshot.getValue(User.class));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
            }
        }
    }

    @Override
    public void onSignOutInteraction() {
        switchToFragment(SignInFragment.newInstance());
    }
}