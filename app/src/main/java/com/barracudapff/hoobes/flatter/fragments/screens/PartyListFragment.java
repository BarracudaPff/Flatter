package com.barracudapff.hoobes.flatter.fragments.screens;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.barracudapff.hoobes.flatter.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PartyListFragment extends Fragment {


    public PartyListFragment() {
        // Required empty public constructor
    }

    public static PartyListFragment newInstance() {
        
        Bundle args = new Bundle();
        
        PartyListFragment fragment = new PartyListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_party_list, container, false);
    }

}
