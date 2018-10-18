package com.example.arezz.datenightdesmoines;

import android.graphics.Bitmap;

import io.realm.annotations.PrimaryKey;

public class Image {
    @PrimaryKey
    private String id;
    private String nightId;
    private Bitmap imageBitmap;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public String getNightId() {
        return nightId;
    }

    public void setNightId(String nightId) {
        this.nightId = nightId;
    }
}
