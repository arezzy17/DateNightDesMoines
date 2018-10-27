package com.example.arezz.datenightdesmoines;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

public class Night extends RealmObject {

    @PrimaryKey
    private String Id;
    private String Username;
    private String dateName;
    private String imageId;
    private Date date;
    private int rating;


    @LinkingObjects("night")
    public final RealmResults<Event> events = null;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getDateName() {
        return dateName;
    }

    public void setDateName(String dateName) {
        this.dateName = dateName;
    }

    public String getImageId() { return imageId; }

    public void setImageId(String imageId) { this.imageId = imageId; }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public RealmResults<Event> getEvents() { return events; }

}
