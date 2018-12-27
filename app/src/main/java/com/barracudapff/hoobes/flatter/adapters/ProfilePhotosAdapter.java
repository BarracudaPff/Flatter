package com.barracudapff.hoobes.flatter.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.barracudapff.hoobes.flatter.R;
import com.barracudapff.hoobes.flatter.database.models.User;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ProfilePhotosAdapter extends PagerAdapter {
    private LayoutInflater layoutInflater;

    private User user;
    private int size = 0;

    public ProfilePhotosAdapter(Context context) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setUser(User user) {
        this.user = user;
        for (String key : user.profile_photo_url.keySet()) {
            if (!user.profile_photo_url.get(key).equals("none"))
                size++;
        }
    }

    @Override
    public int getCount() {
        return size;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = layoutInflater.inflate(R.layout.item_profle_photo, container, false);
        ImageView imageView = itemView.findViewById(R.id.viewPagerItem_image1);
        View darker = itemView.findViewById(R.id.party_darker);

        FirebaseStorage.getInstance().getReference()
                .child("images")
                .child("users")
                .child(user.uid)
                .child(User.PROFILE_PHOTOS)
                .child((position + 1) + ".jpg")
                .getDownloadUrl()
                .addOnCompleteListener(task -> {
                    Picasso.get().load(task.getResult())
                            //.placeholder(R.color.lightGray)
                            .error(R.color.lightGray)
                            .into(imageView, new Callback.EmptyCallback(){
                                @Override
                                public void onSuccess() {
                                    darker.setVisibility(View.VISIBLE);
                                }
                            })
                    ;

                    container.addView(itemView);
                });
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }
}
