package com.barracudapff.hoobes.flatter.database.models;

public class Picture {
    public String url;
    public long date;
    public long views;

    public Picture() {
    }

    public Picture(String url, long date) {
        this.url = url;
        this.date = date;
        this.views = 0;
    }

    public Picture(String url, long date, long views) {
        this.url = url;
        this.date = date;
        this.views = views;
    }
}
