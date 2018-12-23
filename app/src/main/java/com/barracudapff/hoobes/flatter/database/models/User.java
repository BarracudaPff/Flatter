package com.barracudapff.hoobes.flatter.database.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;

@IgnoreExtraProperties
public class User {
    //Must have data
    public String email;
    public String first_name;
    public String second_name;

    //Ask later data
    public HashMap<String, String> profile_photo_url;
    public int ages;
    public String phone;
    public boolean gender;
    public String info;

    //Auto data
    // TODO: 13.09.2018 import auto data

    public User() {
        // Default constructor required
    }

    public User(String email, String first_name, String second_name) {
        this.email = email;
        this.first_name = first_name;
        this.second_name = second_name;
        this.ages = -1;

        prepareProfilePhotoes();
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
                ", profile_photo_url='" + profile_photo_url + '\'' +
                ", ages=" + ages +
                ", phone='" + phone + '\'' +
                ", gender=" + gender +
                ", info='" + info + '\'' +
                '}';
    }
}
