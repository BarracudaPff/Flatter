package com.barracudapff.hoobes.flatter.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.barracudapff.hoobes.flatter.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public class SocialFragment extends Fragment {


    public SocialFragment() {
        // Required empty public constructor
    }

    public static SocialFragment newInstance() {
        
        Bundle args = new Bundle();
        
        SocialFragment fragment = new SocialFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_social, container, false);
        view.findViewById(R.id.floatingActionButton).setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
        });
        return view;
    }

}
