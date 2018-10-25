package com.example.arezz.datenightdesmoines;

import java.io.Serializable;

public class Rating implements Serializable {
    private String dateName;
    private int rating;

    public String getDateName() {
        return dateName;
    }

    public void setDateName(String dateName) {
        this.dateName = dateName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }


}
