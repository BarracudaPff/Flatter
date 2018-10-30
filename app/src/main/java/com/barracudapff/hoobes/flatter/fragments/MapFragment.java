package com.barracudapff.hoobes.flatter.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.barracudapff.hoobes.flatter.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends DebugFragment {


    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance() {
        
        Bundle args = new Bundle();
        
        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.println("onCreateView");
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();
    }


    @Override
    public void onPause() {
        super.onPause();
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).show();
    }
}
