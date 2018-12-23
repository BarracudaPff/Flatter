package com.barracudapff.hoobes.flatter.database.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;

@IgnoreExtraProperties
public class User {
    //Must have data
    public String email;
    public String first_name;
    public String second_name;

    public int parties;
    public int likes;

    public String UID;

    //Ask later data
    public HashMap<String, String> profile_photo_url;
    public int ages;
    public String phone;
    public boolean gender;
    public String about;

    //Auto data
    // TODO: 13.09.2018 import auto data

    public User() {
        // Default constructor required
    }

    public User(String email, String first_name, String second_name, String UID) {
        this.email = email;
        this.first_name = first_name;
        this.second_name = second_name;
        this.ages = -1;
        this.parties = 0;
        this.likes = 0;
        this.UID = UID;

        prepareProfilePhotoes();
    }

    private void prepareProfilePhotoes() {
        profile_photo_url = new HashMap<>();

        for (int i = 0; i < 5; i++) {
            profile_photo_url.put("profile_image_" + i, "none");
        }
    }

    public static String PROFILE_PHOTOS = "profile_photo_url";
}
