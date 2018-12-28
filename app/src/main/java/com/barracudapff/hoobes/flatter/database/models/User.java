package com.barracudapff.hoobes.flatter.database.models;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;

import static com.barracudapff.hoobes.flatter.adapters.SettingsAdapter.APP_PREFERENCES;

@IgnoreExtraProperties
public class User {
    public static final String EMAIL = "email";
    public static final String F_NAME = "first_name";
    public static final String S_NAME = "second_name";
    public static final String PARTIES = "parties";
    public static final String LIKES = "likes";
    public static final String UID = "uid";
    public static final String PROFILE_PHOTOS = "profile_photo_url";
    public static final String PROFILE_IMAGE_0 = "profile_image_0";
    public static final String PROFILE_IMAGE_1 = "profile_image_1";
    public static final String PROFILE_IMAGE_2 = "profile_image_2";
    public static final String PROFILE_IMAGE_3 = "profile_image_3";
    public static final String PROFILE_IMAGE_4 = "profile_image_4";
    public static final String AGES = "ages";
    public static final String ABOUT = "about";
    //Must have data
    public String email;
    public String first_name;
    public String second_name;

    public int parties;
    public int likes;

    public String uid;

    //Ask later data
    public HashMap<String, String> profile_photo_url;
    public int ages;
    public String about;

    //Auto data
    // TODO: 13.09.2018 import auto data

    public User() {
        // Default constructor required
    }

    public User(String email, String first_name, String second_name, String uid) {
        this.email = email;
        this.first_name = first_name;
        this.second_name = second_name;
        this.ages = -1;
        this.parties = 0;
        this.likes = 0;
        this.uid = uid;

        prepareProfilePhotoes();
    }

    public User(String email, String first_name, String second_name, int parties, int likes, String uid, HashMap<String, String> profile_photo_url, int ages, String about) {
        this.email = email;
        this.first_name = first_name;
        this.second_name = second_name;
        this.parties = parties;
        this.likes = likes;
        this.uid = uid;
        this.profile_photo_url = profile_photo_url;
        this.ages = ages;
        this.about = about;
    }

    public static void saveCurrent(Context context, User user) {
        SharedPreferences sp = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        sp.edit().putString(EMAIL, user.email)
                .putString(F_NAME, user.first_name)
                .putString(S_NAME, user.second_name)
                .putInt(PARTIES, user.parties)
                .putInt(LIKES, user.likes)
                .putString(UID, user.uid)
                .putString(PROFILE_IMAGE_0, user.profile_photo_url.get(PROFILE_IMAGE_0))
                .putString(PROFILE_IMAGE_1, user.profile_photo_url.get(PROFILE_IMAGE_1))
                .putString(PROFILE_IMAGE_2, user.profile_photo_url.get(PROFILE_IMAGE_2))
                .putString(PROFILE_IMAGE_3, user.profile_photo_url.get(PROFILE_IMAGE_3))
                .putString(PROFILE_IMAGE_4, user.profile_photo_url.get(PROFILE_IMAGE_4))
                .putInt(AGES, user.ages)
                .putString(ABOUT, user.about).apply();

    }

    public static User getCurrent(Context context) {
        SharedPreferences sp = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        HashMap<String, String> profile_photo_url = new HashMap<>();
        profile_photo_url.put(PROFILE_IMAGE_0, sp.getString(PROFILE_IMAGE_0, null));
        profile_photo_url.put(PROFILE_IMAGE_1, sp.getString(PROFILE_IMAGE_1, null));
        profile_photo_url.put(PROFILE_IMAGE_2, sp.getString(PROFILE_IMAGE_2, null));
        profile_photo_url.put(PROFILE_IMAGE_3, sp.getString(PROFILE_IMAGE_3, null));
        profile_photo_url.put(PROFILE_IMAGE_4, sp.getString(PROFILE_IMAGE_4, null));

        return new User(
                sp.getString(EMAIL, null),
                sp.getString(F_NAME, null),
                sp.getString(S_NAME, null),
                sp.getInt(PARTIES, 0),
                sp.getInt(LIKES, 0),
                sp.getString(UID, null),
                profile_photo_url,
                sp.getInt(AGES, 0),
                sp.getString(ABOUT, null));
    }

    public static Intent putInIntent(Intent intent, User user) {
        intent
                .putExtra(EMAIL, user.email)
                .putExtra(F_NAME, user.first_name)
                .putExtra(S_NAME, user.second_name)
                .putExtra(PARTIES, user.parties)
                .putExtra(LIKES, user.likes)
                .putExtra(UID, user.uid)
                .putExtra(PROFILE_IMAGE_0, user.profile_photo_url.get(PROFILE_IMAGE_0))
                .putExtra(PROFILE_IMAGE_1, user.profile_photo_url.get(PROFILE_IMAGE_1))
                .putExtra(PROFILE_IMAGE_2, user.profile_photo_url.get(PROFILE_IMAGE_2))
                .putExtra(PROFILE_IMAGE_3, user.profile_photo_url.get(PROFILE_IMAGE_3))
                .putExtra(PROFILE_IMAGE_4, user.profile_photo_url.get(PROFILE_IMAGE_4))
                .putExtra(AGES, user.ages)
                .putExtra(ABOUT, user.about);

        return intent;
    }

    public static User getFromIntent(Intent data) {
        HashMap<String, String> profile_photo_url = new HashMap<>();
        profile_photo_url.put(PROFILE_IMAGE_0, data.getStringExtra(PROFILE_IMAGE_0));
        profile_photo_url.put(PROFILE_IMAGE_1, data.getStringExtra(PROFILE_IMAGE_1));
        profile_photo_url.put(PROFILE_IMAGE_2, data.getStringExtra(PROFILE_IMAGE_2));
        profile_photo_url.put(PROFILE_IMAGE_3, data.getStringExtra(PROFILE_IMAGE_3));
        profile_photo_url.put(PROFILE_IMAGE_4, data.getStringExtra(PROFILE_IMAGE_4));
        return new User(
                data.getStringExtra(EMAIL),
                data.getStringExtra(F_NAME),
                data.getStringExtra(S_NAME),
                data.getIntExtra(PARTIES, 0),
                data.getIntExtra(LIKES, 0),
                data.getStringExtra(UID),
                profile_photo_url,
                data.getIntExtra(AGES, 0),
                data.getStringExtra(ABOUT));
    }

    private void prepareProfilePhotoes() {
        profile_photo_url = new HashMap<>();

        for (int i = 0; i < 5; i++) {
            profile_photo_url.put("profile_image_" + i, "none");
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", first_name='" + first_name + '\'' +
                ", second_name='" + second_name + '\'' +
                ", parties=" + parties +
                ", likes=" + likes +
                ", uid='" + uid + '\'' +
                ", profile_photo_url=" + profile_photo_url +
                ", ages=" + ages +
                ", about='" + about + '\'' +
                '}';
    }
}
