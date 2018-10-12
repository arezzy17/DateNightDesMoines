package com.example.arezz.datenightdesmoines;

public class YelpItem {

    private String name;
    private String stars;

    public YelpItem(String name, String stars){
        this.name = name;
        this.stars =stars;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }


}
