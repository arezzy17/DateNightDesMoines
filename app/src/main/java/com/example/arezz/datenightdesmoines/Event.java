package com.example.arezz.datenightdesmoines;
import java.util.Date;

import io.realm.RealmObject;

public class Event extends RealmObject {

    public Date startTime;
    public Date endTime;
    public String eventType;
    public String nightName;
    private Night night;
    public String yelpID;

    public Night getNight() {
        return night;
    }

    public void setNight(Night night) {
        this.night = night;
    }
    public String getYelpID() {
        return yelpID;
    }

    public void setYelpID(String yelpID) {
        this.yelpID = yelpID;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getNightName() {
        return nightName;
    }

    public void setNightName(String nightName) {
        this.nightName = nightName;
    }
}
