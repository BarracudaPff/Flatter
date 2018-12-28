package com.barracudapff.hoobes.flatter.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.barracudapff.hoobes.flatter.R;
import com.barracudapff.hoobes.flatter.adapters.ProfilePhotosAdapter;
import com.barracudapff.hoobes.flatter.database.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PersonActivity extends AppCompatActivity {
    private ViewPager pagerProfilePhotos;
    private ProfilePhotosAdapter adapter;

    private TextView personName;
    private TextView personAge;
    private TextView personDot;
    private TextView personPartiesCount;
    private TextView personParties;
    private TextView personLikesCount;
    private TextView personLikes;
    private TextView personAbout;

    private View divider;
    private View cover;
    private ProgressBar progressBar;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = User.getFromIntent(getIntent());
        setContentView(R.layout.activity_person);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        user = dataSnapshot.getValue(User.class);
                        System.out.println(user);
                        System.out.println("!!!!");
                        User.saveCurrent(getBaseContext(), user);
                        adapter.setUser(user);
                        updateUI(user);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        init();
        adapter = new ProfilePhotosAdapter(this);

        pagerProfilePhotos.setAdapter(adapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void init() {
        personName = findViewById(R.id.person_name);
        personAge = findViewById(R.id.person_age);
        personDot = findViewById(R.id.person_dot);
        personPartiesCount = findViewById(R.id.person_parties_count);
        personParties = findViewById(R.id.person_parties);
        personLikesCount = findViewById(R.id.person_likes_count);
        personLikes = findViewById(R.id.person_likes);
        personAbout = findViewById(R.id.person_about);

        divider = findViewById(R.id.person_divider);
        cover = findViewById(R.id.person_cover);
        progressBar = findViewById(R.id.person_progressBar);

        findViewById(R.id.refresh).setVisibility(View.GONE);
        pagerProfilePhotos = findViewById(R.id.person_profile_photos);
    }

    @SuppressLint("SetTextI18n")
    public void updateUI(User user) {
        getSupportActionBar().setTitle(user.first_name+" "+user.second_name);
        adapter.notifyDataSetChanged();
        cover.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        personName.setText(user.first_name + " " + user.second_name);

        //ages
        if (user.ages == -1) {
            personDot.setVisibility(View.GONE);
            personAge.setVisibility(View.GONE);
        } else {
            personAge.setText(String.valueOf(user.ages));
            personDot.setVisibility(View.VISIBLE);
            personAge.setVisibility(View.VISIBLE);
        }

        //parties count
        if (user.parties == 0) {
            personParties.setVisibility(View.GONE);
            personPartiesCount.setVisibility(View.GONE);
        } else {
            personPartiesCount.setText(String.valueOf(user.parties));
            personParties.setVisibility(View.VISIBLE);
            personPartiesCount.setVisibility(View.VISIBLE);
        }

        //likes count
        if (user.likes == 0) {
            personLikes.setVisibility(View.GONE);
            personLikesCount.setVisibility(View.GONE);
        } else {
            personLikesCount.setText(String.valueOf(user.likes));
            personLikes.setVisibility(View.VISIBLE);
            personLikesCount.setVisibility(View.VISIBLE);
        }

        //about info
        if (user.about == null) {
            personAbout.setVisibility(View.GONE);
            divider.setVisibility(View.GONE);
        } else {
            personAbout.setText(user.about);
            personAbout.setVisibility(View.VISIBLE);
            divider.setVisibility(View.VISIBLE);
        }
    }

}
