package com.barracudapff.hoobes.flatter.database.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

@IgnoreExtraProperties
public class Message {
    public String sender_uid;
    public String target_uid;
    public String message;

    public long timestamp;

    public Message() {
    }

    public Message(String sender_uid, String target_uid, String message) {
        this.sender_uid = sender_uid;
        this.target_uid = target_uid;
        this.message = message;

        this.timestamp = new Date().getTime();
    }
}