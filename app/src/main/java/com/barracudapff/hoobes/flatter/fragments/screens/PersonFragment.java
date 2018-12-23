package com.barracudapff.hoobes.flatter.fragments.screens;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.barracudapff.hoobes.flatter.MainActivity;
import com.barracudapff.hoobes.flatter.R;
import com.barracudapff.hoobes.flatter.activities.settings.SettingsActivity;
import com.barracudapff.hoobes.flatter.adapters.ProfilePhotosAdapter;
import com.barracudapff.hoobes.flatter.database.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private OnSignOutInterface mListener;
    private ViewPager pagerProfilePhotos;
    private ProfilePhotosAdapter adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

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

    public PersonFragment() {
        // Required empty public constructor
    }

    public static PersonFragment newInstance() {

        Bundle args = new Bundle();

        PersonFragment fragment = new PersonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSignOutInterface) {
            mListener = (OnSignOutInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        user = dataSnapshot.getValue(User.class);
                        adapter.setUser(user);
                        updateUI(user);
                        System.out.println(user);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void init(View view) {
        personName = view.findViewById(R.id.person_name);
        personAge = view.findViewById(R.id.person_age);
        personDot = view.findViewById(R.id.person_dot);
        personPartiesCount = view.findViewById(R.id.person_parties_count);
        personParties = view.findViewById(R.id.person_parties);
        personLikesCount = view.findViewById(R.id.person_likes_count);
        personLikes = view.findViewById(R.id.person_likes);
        personAbout = view.findViewById(R.id.person_about);

        divider = view.findViewById(R.id.person_divider);
        cover = view.findViewById(R.id.person_cover);
        progressBar = view.findViewById(R.id.person_progressBar);

        mSwipeRefreshLayout = view.findViewById(R.id.refresh);
        pagerProfilePhotos = view.findViewById(R.id.person_profile_photos);
    }

    @SuppressLint("SetTextI18n")
    public void updateUI(User user) {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        init(view);
        adapter = new ProfilePhotosAdapter(getContext());

        mSwipeRefreshLayout.setOnRefreshListener(this);
        pagerProfilePhotos.setAdapter(adapter);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_profile, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_settings:
                MainActivity.basicStart(getActivity(), SettingsActivity.class, 103);
                return true;
            case R.id.item_sing_out:
                FirebaseAuth.getInstance().signOut();
                mListener.onSignOutInteraction();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        adapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.postDelayed(() -> {
            mSwipeRefreshLayout.setRefreshing(false);
        }, 1000);
    }

    public interface OnSignOutInterface {
        // TODO: Update argument type and name
        void onSignOutInteraction();
    }
}
