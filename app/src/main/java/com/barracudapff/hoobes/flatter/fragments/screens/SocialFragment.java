package com.barracudapff.hoobes.flatter.fragments.screens;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.barracudapff.hoobes.flatter.R;
import com.barracudapff.hoobes.flatter.activities.PersonActivity;
import com.barracudapff.hoobes.flatter.database.models.User;
import com.firebase.ui.common.ChangeEventType;
import com.firebase.ui.database.ChangeEventListener;
import com.firebase.ui.database.FirebaseArray;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.makeramen.roundedimageview.RoundedImageView;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * A simple {@link Fragment} subclass.
 */
public class SocialFragment extends Fragment {
    RecyclerView mRecyclerView;
    public static final int ROW_3 = 3;
    public static final int ROW_2 = 2;

    private boolean isNextRow2 = false;
    FirebaseArray<User> array;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Query postsQuery = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("users");
        array = new FirebaseArray<>(postsQuery, snapshot -> snapshot.getValue(User.class));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_social, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Flatters");
        mRecyclerView = view.findViewById(R.id.users_grid);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        array.addChangeEventListener(new ChangeEventListener() {
            @Override
            public void onChildChanged(@NonNull ChangeEventType type, @NonNull DataSnapshot snapshot, int newIndex, int oldIndex) {

            }

            @Override
            public void onDataChanged() {
                mRecyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onError(@NonNull DatabaseError databaseError) {
            }
        });
        isNextRow2 = false;
        mRecyclerView.setAdapter(new RecyclerView.Adapter<RowViewHolder>() {
            @NonNull
            @Override
            public RowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                if (isNextRow2) {
                    isNextRow2 = false;
                    return new RowViewHolder(LayoutInflater.from(viewGroup.getContext())
                            .inflate(R.layout.item_user_row, viewGroup, false), ROW_2);
                } else {
                    isNextRow2 = true;
                    return new RowViewHolder(LayoutInflater.from(viewGroup.getContext())
                            .inflate(R.layout.item_user_row, viewGroup, false), ROW_3);
                }

            }

            @Override
            public void onBindViewHolder(@NonNull RowViewHolder holder, int position) {
                if (holder.type == ROW_2) {
                    try {
                        holder.bindOnView2(
                                array.get((position / 2 + position % 2) * 5 - 2),
                                array.get((position / 2 + position % 2) * 5 - 1));
                    } catch (IndexOutOfBoundsException e) {
                        holder.bindOnView2(
                                array.get((position / 2 + position % 2) * 5 - 2),
                                null);
                    }
                } else
                    try {
                        holder.bindOnView3(array.get((position / 2 + position % 2) * 5),
                                array.get((position / 2 + position % 2) * 5 + 1),
                                array.get((position / 2 + position % 2) * 5 + 2));
                    } catch (IndexOutOfBoundsException e) {
                        try {
                            holder.bindOnView3(array.get((position / 2 + position % 2) * 5),
                                    array.get((position / 2 + position % 2) * 5 + 1),
                                    null);
                        } catch (IndexOutOfBoundsException e1) {
                            holder.bindOnView3(array.get((position / 2 + position % 2) * 5),
                                    null,
                                    null);
                        }
                    }
            }

            @Override
            public int getItemCount() {
                int size = (array.size() / 5) * 2;
                int mSize = (array.size() - size / 2 * 5);
                int add = 0;
                switch (mSize) {
                    case 0:
                        add = 0;
                        break;
                    case 1:
                        add = 1;
                        break;
                    case 2:
                        add = 1;
                        break;
                    case 3:
                        add = 1;
                        break;
                    case 4:
                        add = 2;
                        break;
                }
                return size + add;
            }
        });

        return view;
    }

    class RowViewHolder extends RecyclerView.ViewHolder {
        private final Transformation TRANSFORMATION;

        ConstraintLayout constraintLayout;
        RoundedImageView roundedImageView1;
        RoundedImageView roundedImageView2;
        RoundedImageView roundedImageView3;
        TextView textView1;
        TextView textView2;
        TextView textView3;

        public int type;

        public RowViewHolder(@NonNull View itemView, int type) {
            super(itemView);
            TRANSFORMATION = new RoundedTransformationBuilder()
                    .borderColor(Color.BLACK)
                    .borderWidthDp(1)
                    .cornerRadiusDp(32)
                    .oval(false)
                    .build();
            this.type = type;
            roundedImageView1 = itemView.findViewById(R.id.user_image_1);
            roundedImageView2 = itemView.findViewById(R.id.user_image_2);
            roundedImageView3 = itemView.findViewById(R.id.user_image_3);
            textView1 = itemView.findViewById(R.id.user_text_1);
            textView2 = itemView.findViewById(R.id.user_text_2);
            textView3 = itemView.findViewById(R.id.user_text_3);
            constraintLayout = itemView.findViewById(R.id.user_layout);
        }

        public void bindOnView2(User user1, User user2) {
            roundedImageView3.setVisibility(View.GONE);
            textView3.setVisibility(View.GONE);
            setImage(user1, roundedImageView1);
            textView1.setText(user1.first_name);
            if (user2 != null) {
                setImage(user2, roundedImageView2);
                textView2.setText(user2.first_name);
            }
        }

        public void bindOnView3(User user1, User user2, User user3) {
            roundedImageView3.setVisibility(View.VISIBLE);
            textView3.setVisibility(View.VISIBLE);
            setImage(user1, roundedImageView1);
            textView1.setText(user1.first_name);
            if (user2 != null) {
                setImage(user2, roundedImageView2);
                textView2.setText(user2.first_name);
            }
            if (user3 != null) {
                setImage(user3, roundedImageView3);
                textView3.setText(user3.first_name);
            }

        }

        private void setImage(User user, RoundedImageView imageView) {
            imageView.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), PersonActivity.class);
                User.putInIntent(intent, user);
                getActivity().startActivityForResult(intent, 109);
            });
            FirebaseStorage.getInstance().getReference()
                    .child("images")
                    .child("users")
                    .child(user.uid)
                    .child(User.PROFILE_PHOTOS)
                    .child("0_low_25")
                    .getDownloadUrl()
                    .addOnSuccessListener(uri -> Picasso.get()
                            .load(uri)
                            .fit()
                            .centerCrop()
                            .transform(TRANSFORMATION)
                            .into(imageView))
                    .addOnFailureListener(command -> {
                        imageView.setBackground(getResources().getDrawable(R.drawable.background_user_circle));
                    });
        }
    }
}
