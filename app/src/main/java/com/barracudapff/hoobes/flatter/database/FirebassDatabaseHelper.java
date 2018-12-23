package com.barracudapff.hoobes.flatter.database;

import android.support.annotation.Nullable;

import com.barracudapff.hoobes.flatter.database.models.User;

public class FirebassDatabaseHelper {

    private static User currentUser;

    public FirebassDatabaseHelper() {
    }

    @Nullable
    public static User getCurrentUser() {
        return currentUser;
    }

    @Nullable
    public static void setCurrentUser(User currentUser) {
        FirebassDatabaseHelper.currentUser = currentUser;
    }
}
