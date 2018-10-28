package com.example.arezz.datenightdesmoines;
import java.util.Date;

import io.realm.RealmObject;

public class Event extends RealmObject implements Comparable<Event>{

    public String eventType;
    public String eventName;
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

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int compareTo(Event e){
        if(e.getEventType() != null && this.getEventType() != null) {
            return e.getEventType().compareTo(this.getEventType());
        }
        else{
            return -1;
        }
    }
}
