package com.barracudapff.hoobes.flatter.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.barracudapff.hoobes.flatter.R;
import com.barracudapff.hoobes.flatter.activities.chat.ChatActivity;
import com.barracudapff.hoobes.flatter.database.models.User;
import com.barracudapff.hoobes.flatter.database.viewHolders.UserViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;

public class NewSearchFirebaseRecyclerAdapter extends FirebaseRecyclerAdapter<User, UserViewHolder> {
    private Activity activity;

    private final String myEmail;

    /**
     * Initialize a {@link FirebaseRecyclerAdapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public NewSearchFirebaseRecyclerAdapter(@NonNull FirebaseRecyclerOptions<User> options, Activity activity) {
        super(options);
        this.activity = activity;
        myEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
    }

    @Override
    protected void onBindViewHolder(@NonNull UserViewHolder holder, int position, @NonNull User model) {
        // TODO: 06.10.2018 Delete coloring

        holder.bindOnUser(model, getRef(position).getKey());
        holder.layout.setOnClickListener(view -> {
            ChatActivity.setUser(model);
            String s = getRef(position).getKey();
            ChatActivity.setOtherUID(s);
            Intent i = new Intent(activity, ChatActivity.class);
            activity.startActivityForResult(i, 103);

        });
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        return new UserViewHolder(inflater.inflate(R.layout.item_search_member, viewGroup, false));
    }
}
