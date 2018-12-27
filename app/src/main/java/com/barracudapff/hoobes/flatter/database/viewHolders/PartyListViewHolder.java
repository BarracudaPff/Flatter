package com.barracudapff.hoobes.flatter.database.viewHolders;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.barracudapff.hoobes.flatter.R;
import com.barracudapff.hoobes.flatter.activities.party.PartyActivity;
import com.barracudapff.hoobes.flatter.database.models.Party;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;

public class PartyListViewHolder extends RecyclerView.ViewHolder {
    private CardView partyLayout;
    private TextView nameView;
    private TextView dateView;
    private ImageView profileView;
    private ImageView openView;
    private ImageView darker;

    public PartyListViewHolder(@NonNull View itemView) {
        super(itemView);

        partyLayout = itemView.findViewById(R.id.party_list_item_layout);
        nameView = itemView.findViewById(R.id.party_list_name);
        dateView = itemView.findViewById(R.id.party_list_date);
        profileView = itemView.findViewById(R.id.party_list_profile);
        openView = itemView.findViewById(R.id.party_list_open);
        darker = itemView.findViewById(R.id.party_darker);
        darker.setVisibility(View.INVISIBLE);
    }

    public void bindOnView(Activity activity, Party party) {
        nameView.setText(party.name);
        dateView.setText(
                new SimpleDateFormat("dd MMMM yyyy", activity.getResources().getConfiguration().locale)
                        .format(party.dateTime));
        partyLayout.setOnClickListener(v -> {
            Intent intent = new Intent(activity, PartyActivity.class);
            Party.putInIntent(intent, party);
            activity.startActivityForResult(intent, 106);
        });
        FirebaseStorage.getInstance().getReference()
                .child("parties")
                .child(party.uid)
                .child(Party.PROFILE_IMAGE)
                .getDownloadUrl()
                .addOnCompleteListener(task -> Picasso.get().load(task.getResult())
                        .placeholder(R.color.lightGray)
                        .error(R.color.lightGray)
                        .into(profileView, new Callback.EmptyCallback() {
                            @Override
                            public void onSuccess() {
                                darker.setVisibility(View.VISIBLE);
                            }
                        }));
    }
}
