package com.example.arezz.datenightdesmoines;

import android.graphics.Bitmap;

import io.realm.RealmObject;

public class User extends RealmObject {
    private String username;
    private String password;
    private byte[] profilePic;
    private String userId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(byte[] profilePic) {
        this.profilePic = profilePic;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
