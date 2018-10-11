package com.example.arezz.datenightdesmoines;
import java.util.Date;

public class Event {
    public Date startTime;
    public Date endTime;
    public String eventType;
    public String nightName;
    public String nightID;
    public String yelpID;

    public String getNightID() {
        return nightID;
    }

    public void setNightID(String nightID) {
        this.nightID = nightID;
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
