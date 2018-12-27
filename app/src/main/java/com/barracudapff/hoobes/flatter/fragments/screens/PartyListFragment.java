package com.barracudapff.hoobes.flatter.fragments.screens;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.barracudapff.hoobes.flatter.R;
import com.barracudapff.hoobes.flatter.database.models.Party;
import com.barracudapff.hoobes.flatter.database.viewHolders.PartyListViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * A simple {@link Fragment} subclass.
 */
public class PartyListFragment extends Fragment {
    private RecyclerView mRecyclerView;

    private DatabaseReference mDatabase;
    FirebaseRecyclerOptions<Party> options;
    private FirebaseRecyclerAdapter<Party, PartyListViewHolder> adapter;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("parties");
        Query postsQuery = mDatabase;

        options = new FirebaseRecyclerOptions.Builder<Party>()
                .setQuery(postsQuery, Party.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Party, PartyListViewHolder>(options) {
            @NonNull
            @Override
            public PartyListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

                return new PartyListViewHolder(inflater.inflate(R.layout.item_party_list, viewGroup, false));
            }

            @Override
            protected void onBindViewHolder(@NonNull PartyListViewHolder holder, int position, @NonNull Party model) {
                holder.bindOnView(getActivity(), model);
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_party_list, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Вечеринки");

        mRecyclerView = view.findViewById(R.id.party_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);
        adapter.startListening();

        return view;
    }

}
