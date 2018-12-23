package com.barracudapff.hoobes.flatter.database.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

@IgnoreExtraProperties
public class LastMessage {
    public long last_message_timestamp;
    public long unread_timestamp;
    public boolean unread = false;
    public String last_message;

    public LastMessage() {

    }

    public LastMessage(String last_message) {
        this.last_message = last_message;

        // Initialize to current time
        last_message_timestamp = new Date().getTime();
        unread_timestamp = last_message_timestamp;
    }
}
