package com.barracudapff.hoobes.flatter.database.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;

@IgnoreExtraProperties
public class Party implements Serializable {
    //map data
    public double latitude;
    public double longitude;
    public String name;
    public long dateTime;
    // TODO: 05.10.2018 Rename address
    public String adress;
    public String author;
    public String authorUid;
    public int likes;
    public ArrayList<String> members;
    public ArrayList<String> images;

    //expand data
    public boolean isFree;
    public String about;

    @Exclude
    public String key;

    public Party() {
    }

    public Party(double latitude, double longitude, String name, long dateTime, String adress, String author, String authorUid, int rating, ArrayList<String> members, boolean isFree, String about) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.dateTime = dateTime;
        this.adress = adress;
        this.author = author;
        this.authorUid = authorUid;
        this.likes = rating;
        this.members = members;
        this.isFree = isFree;
        this.about = about;
    }

    @Exclude

    @Override
    public String toString() {
        return "Party{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", name='" + name + '\'' +
                ", dateTime=" + dateTime +
                ", adress='" + adress + '\'' +
                ", author='" + author + '\'' +
                ", authorUid='" + authorUid + '\'' +
                ", likes=" + likes +
                ", members=" + members +
                ", images=" + images +
                ", isFree=" + isFree +
                ", about='" + about + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
