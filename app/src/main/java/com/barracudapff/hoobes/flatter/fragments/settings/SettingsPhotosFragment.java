package com.barracudapff.hoobes.flatter.fragments.settings;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.barracudapff.hoobes.flatter.R;
import com.barracudapff.hoobes.flatter.activities.settings.ProfileSettingsActivity;
import com.barracudapff.hoobes.flatter.database.models.User;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class SettingsPhotosFragment extends SettingsBaseFragment {
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    User user;

    private ProgressDialog mProgressDialog;
    private int id;
    private String imKey;

    public SettingsPhotosFragment() {
    }

    public static SettingsPhotosFragment newInstance() {
        SettingsPhotosFragment settingsPhotosFragment = new SettingsPhotosFragment();
        return settingsPhotosFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        user = User.getCurrent(getContext());
        View view = inflater.inflate(R.layout.fragment_settings_photos, container, false);
        recyclerView = view.findViewById(R.id.profile_images_list);
        recyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);

        recyclerView.setAdapter(new RecyclerView.Adapter<ViewHolder>() {
            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_profle_photo, viewGroup, false));
            }

            @Override
            public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
                if (i == getItemCount() - 1) {
                    viewHolder.bindOnView(user.uid, i, v -> {
                    });
                } else {
                    viewHolder.bindOnView(user.uid, i);
                }
            }

            @Override
            public int getItemViewType(int position) {
                return position;
            }

            @Override
            public int getItemCount() {
                int size = 0;
                for (String key : user.profile_photo_url.keySet()) {
                    if (!user.profile_photo_url.get(key).equals("none"))
                        size++;
                }
                return size + 1;
            }
        });

        return view;
    }

    protected void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage(getString(R.string.loading));
        }

        mProgressDialog.show();
    }

    protected void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_confirm, menu);
    }

    @Override
    public void update() {
        Toolbar bar = ((ProfileSettingsActivity) getActivity()).getBar();
        if (bar != null) {
            bar.setTitle("Фотографии пользователя");
            bar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_close_24dp));
            bar.setNavigationOnClickListener(v -> ((ProfileSettingsActivity) getActivity()).changePage(0));
            setHasOptionsMenu(true);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 106 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                imKey = String.valueOf(id);
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                showProgressDialog();
                sendToFirebase(bitmap, bitmap, user.uid);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sendToFirebase(Bitmap bitmapFull, Bitmap bitmapLow25, String key) {

        ByteArrayOutputStream baosFull = new ByteArrayOutputStream();
        bitmapFull.compress(Bitmap.CompressFormat.JPEG, 50, baosFull);
        byte[] dataFull = baosFull.toByteArray();
        ByteArrayOutputStream baosLow = new ByteArrayOutputStream();
        bitmapLow25.compress(Bitmap.CompressFormat.JPEG, 25, baosLow);
        byte[] dataLow = baosLow.toByteArray();

        StorageReference storageRefLow = FirebaseStorage.getInstance().getReference()
                .child("images")
                .child("users")
                .child(key)
                .child(User.PROFILE_PHOTOS)
                .child(imKey + "_low_25");
        uploadPicture(storageRefLow, dataLow, true, key);

        StorageReference storageRef = FirebaseStorage.getInstance().getReference()
                .child("images")
                .child("users")
                .child(key)
                .child(User.PROFILE_PHOTOS)
                .child(imKey);
        uploadPicture(storageRef, dataFull, false, key);

    }

    void uploadPicture(StorageReference storageRef, byte[] data, boolean isFull, String key) {
        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.addOnFailureListener(exception -> {
            // Handle unsuccessful uploads
            if (isFull) {
                Toast.makeText(getActivity(), "Error upload", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(taskSnapshot -> {
            if (isFull) {
                hideProgressDialog();
                FirebaseDatabase.getInstance().getReference()
                        .child("users")
                        .child(key)
                        .child(User.PROFILE_PHOTOS)
                        .child("profile_image_" + imKey)
                        .setValue(imKey)
                        .addOnSuccessListener(command -> {
                            user.profile_photo_url.put("profile_image_" + imKey, imKey);
                            recyclerView.getAdapter().notifyDataSetChanged();
                        });
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        View darker;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.viewPagerItem_image1);
            darker = itemView.findViewById(R.id.party_darker);
        }

        public void bindOnView(String uid, int position) {
            FirebaseStorage.getInstance().getReference()
                    .child("images")
                    .child("users")
                    .child(uid)//user.uid
                    .child(User.PROFILE_PHOTOS)
                    .child(String.valueOf((position)))
                    .getDownloadUrl()
                    .addOnCompleteListener(task -> Picasso.get().load(task.getResult())
                            //.placeholder(R.color.lightGray)
                            .error(R.color.lightGray)
                            .into(imageView, new Callback.EmptyCallback() {
                                @Override
                                public void onSuccess() {
                                    darker.setVisibility(View.VISIBLE);
                                }
                            }));
            darker.setOnClickListener(v -> {
                Intent intent = new Intent();
                id = position;
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Отправить нн"), 106);
            });
        }

        public void bindOnView(String uid, int i, View.OnClickListener listener) {
            imageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_photo_library_24dp));
            int paddingDp = 300;
            imageView.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);
            darker.setVisibility(View.GONE);
            imageView.setOnClickListener(v -> {
                if (recyclerView.getAdapter().getItemCount() >= 6) {
                    Toast.makeText(getContext(), "Нельзя добавить более 5 фотографий в профиле", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent();
                intent.setType("image/*");
                id = i;
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Отправить нн"), 106);
            });
        }

    }
}
