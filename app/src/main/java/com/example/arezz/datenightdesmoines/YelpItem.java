package com.example.arezz.datenightdesmoines;

import org.json.JSONObject;

import java.sql.Time;
import java.util.Date;

public class YelpItem {


    private String name;
    private String stars;
    private String price;
    private String address;
    private String displayPhone;
    private String imageUrl;
    private String yelpId;


    public String getYelpId() {
        return yelpId;
    }

    public void setYelpId(String yelpId) {
        this.yelpId = yelpId;
    }

    public YelpItem(){
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDisplayPhone() {
        return displayPhone;
    }

    public void setDisplayPhone(String displayPhone) {
        this.displayPhone = displayPhone;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public static YelpItem GetYelpItemFromBusinessJSON(JSONObject entry){


        YelpItem newItem = new YelpItem();
        newItem.setName(entry.optString("name"));
        newItem.setStars(entry.optDouble("rating")+"");
        newItem.setPrice(entry.optString("price"));

        try {
            newItem.setAddress(entry.optJSONObject("location").optJSONArray("display_address").join(" ").replace("\"",""));
        }
        catch (Exception ex){
            newItem.setAddress("");
        }

        newItem.setDisplayPhone(entry.optString("display_phone"));
        newItem.setImageUrl(entry.optString("image_url"));
        newItem.setYelpId(entry.optString("id"));

        return newItem;
    }

}
