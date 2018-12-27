package com.barracudapff.hoobes.flatter.database.models;

import android.content.Intent;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

@IgnoreExtraProperties
public class Party implements Serializable {
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String NAME = "name";
    public static final String DATE_TIME = "dateTime";
    public static final String ADDRESS = "address";
    public static final String AUTHOR = "author";
    public static final String AUTHOR_UID = "author_uid";
    public static final String MEMBERS = "members";
    public static final String LIKES = "likes";
    public static final String PROFILE_IMAGE = "profile_image";
    public static final String IS_FREE = "isFree";
    public static final String ABOUT = "about";
    public static final String UID = "uid";
    public static final String MEMBER = "member";
    public static final String LIKE = "like";
    //mMap data
    public double latitude;
    public double longitude;
    public String name;
    public long dateTime;
    public String address;
    public String author;
    public String author_uid;
    public int likes;
    public Boolean profile_image;
    public ArrayList<String> members;
    public ArrayList<String> images;

    //expand data
    public boolean isFree;
    public String about;
    public String uid;

    public Party() {
    }

    public Party(double latitude, double longitude, String name, long dateTime, String address, String author, String authorUid, Boolean profile_image, ArrayList<String> members, boolean isFree, String about, String uid) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.dateTime = dateTime;
        this.address = address;
        this.author = author;
        this.author_uid = authorUid;
        this.profile_image = profile_image;
        this.members = members;
        this.isFree = isFree;
        this.about = about;
        this.likes = 0;
        this.uid = uid;
    }

    public static Party getFromIntent(Intent data) {

        return new Party(
                data.getDoubleExtra(LATITUDE, 0)
                , data.getDoubleExtra(LONGITUDE, 0)
                , data.getStringExtra(NAME)
                , data.getLongExtra(DATE_TIME, 0)
                , data.getStringExtra(ADDRESS)
                , data.getStringExtra(AUTHOR)
                , data.getStringExtra(AUTHOR_UID)
                , data.getBooleanExtra(PROFILE_IMAGE, false)
                , data.getStringArrayListExtra(MEMBERS)
                , data.getBooleanExtra(IS_FREE, false)
                , data.getStringExtra(ABOUT)
                , data.getStringExtra(UID)
        );
    }

    public static Intent putInIntent(Intent intent, Party party) {
        intent
                .putExtra(LATITUDE, party.latitude)
                .putExtra(LONGITUDE, party.longitude)
                .putExtra(NAME, party.name)
                .putExtra(DATE_TIME, party.dateTime)
                .putExtra(ADDRESS, party.address)
                .putExtra(AUTHOR, party.author)
                .putExtra(AUTHOR_UID, party.author_uid)
                .putExtra(PROFILE_IMAGE, party.profile_image)
                .putExtra(MEMBERS, party.members)
                .putExtra(IS_FREE, party.isFree)
                .putExtra(ABOUT, party.about)
                .putExtra(UID, party.uid);

        return intent;
    }

    @Override
    public String toString() {
        return "Party{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", name='" + name + '\'' +
                ", dateTime=" + dateTime +
                ", address='" + address + '\'' +
                ", author='" + author + '\'' +
                ", author_uid='" + author_uid + '\'' +
                ", likes=" + likes +
                ", members=" + members +
                ", images=" + images +
                ", isFree=" + isFree +
                ", about='" + about + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Party)) return false;
        Party party = (Party) o;
        return Objects.equals(uid, party.uid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid);
    }
}
