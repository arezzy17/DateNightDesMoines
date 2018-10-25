package com.example.arezz.datenightdesmoines;

import android.graphics.Bitmap;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Image extends RealmObject {
    @PrimaryKey
    private String id;
    private String nightId;
    private byte[] imageBitmap;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(byte[] imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public String getNightId() {
        return nightId;
    }

    public void setNightId(String nightId) {
        this.nightId = nightId;
    }
}
