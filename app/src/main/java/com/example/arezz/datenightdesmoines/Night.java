package com.example.arezz.datenightdesmoines;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Night extends RealmObject {
    @PrimaryKey
    private String Id;
    private String Username;
    private String dateName;
    private Date date;
    private RealmList<RealmString> eventIds;
    private RealmList<RealmString> imageIds;
    private int rating;


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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public RealmList<RealmString> getEventIds() {
        return eventIds;
    }

    public void setEventIds(RealmList<RealmString> eventIds) {
        this.eventIds = eventIds;
    }

    public RealmList<RealmString> getImageIds() {
        return imageIds;
    }

    public void setImageIds(RealmList<RealmString> imageIds) {
        this.imageIds = imageIds;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
