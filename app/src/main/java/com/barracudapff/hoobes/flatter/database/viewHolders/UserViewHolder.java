package com.barracudapff.hoobes.flatter.database.viewHolders;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.barracudapff.hoobes.flatter.R;
import com.barracudapff.hoobes.flatter.database.models.User;
import com.google.firebase.storage.FirebaseStorage;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class UserViewHolder extends RecyclerView.ViewHolder {
    private final static Transformation TRANSFORMATION = new RoundedTransformationBuilder()
            .borderColor(Color.BLACK)
            .borderWidthDp(1)
            .cornerRadiusDp(32)
            .oval(false)
            .build();

    public ConstraintLayout layout;
    private final ImageView imageView;
    private View mView;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
        imageView = mView.findViewById(R.id.chat_list_profile_image1);
    }

    public void bindOnUser(User user, String key) {
        layout = mView.findViewById(R.id.chat_list_search_layout);

        TextView user_name = mView.findViewById(R.id.chat_list_name);
        TextView user_status = mView.findViewById(R.id.chat_list_text);

        FirebaseStorage.getInstance().getReference()
                .child("images")
                .child("users")
                .child(key)
                .child(User.PROFILE_PHOTOS)
                .child("0_low_25")
                .getDownloadUrl()
                .addOnSuccessListener(uri -> Picasso.get()
                        .load(uri)
                        .fit()
                        .centerCrop()
                        .error(R.color.lightGray)
                        .transform(TRANSFORMATION)
                        .into(imageView))
                .addOnFailureListener(command -> Picasso.get()
                        .load(R.color.lightGray)
                        .fit()
                        .centerCrop()
                        .transform(TRANSFORMATION)
                        .into(imageView));

        try {
            String name = user.first_name + " " + user.second_name;
            user_name.setText(name);
            user_status.setText(user.email);
        } catch (Exception e) {
            System.out.println("Old user: " + user);
        }
    }
}
