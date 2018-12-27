package com.barracudapff.hoobes.flatter.database.viewHolders;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.barracudapff.hoobes.flatter.R;
import com.barracudapff.hoobes.flatter.database.models.User;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

public class UserViewHolder extends RecyclerView.ViewHolder {

    public ConstraintLayout layout;
    private final ImageView imageView;
    private View mView;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
        imageView = mView.findViewById(R.id.chat_list_profile_image);
    }

    public void bindOnUser(User user, String key) {
        layout = mView.findViewById(R.id.chat_list_search_layout);

        TextView user_name = mView.findViewById(R.id.chat_list_name);
        TextView user_status = mView.findViewById(R.id.chat_list_text);

        FirebaseStorage.getInstance().getReference().child(key).child("profile").child("image_low_25_0").getDownloadUrl().addOnSuccessListener(uri -> {
            System.out.println(uri + "\n" + uri.getPath());
            Picasso.get().load(uri).error(R.color.lightGray).noFade().into(imageView);
        });

        try {
            String name = user.first_name + " " + user.second_name;
            user_name.setText(name);
            user_status.setText(user.email);
        } catch (Exception e) {
            System.out.println("Old user: " + user);
        }
    }
}
