package com.barracudapff.hoobes.flatter.database.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;

@IgnoreExtraProperties
public class Party implements Serializable {
    //mMap data
    public double latitude;
    public double longitude;
    public String name;
    public long dateTime;
    public String address;
    public String author;
    public String author_uid;
    public int likes;
    public String profile_image;
    public ArrayList<String> members;
    public ArrayList<String> images;

    //expand data
    public boolean isFree;
    public String about;
    public String uid;

    public Party() {
    }

    public Party(double latitude, double longitude, String name, long dateTime, String address, String author, String authorUid, String profile_image, ArrayList<String> members, boolean isFree, String about, String uid) {
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
}
