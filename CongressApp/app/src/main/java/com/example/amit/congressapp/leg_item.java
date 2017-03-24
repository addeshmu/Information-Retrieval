package com.example.amit.congressapp;

/**
 * Created by Amit on 11/17/2016.
 */

public class leg_item {
    private String image;
    private String bio;
    private String AllInfo;
    private String bioguide;
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setInfo(String AllInfo){this.AllInfo = AllInfo;}

    public String getInfo(){return this.AllInfo;}

    public String getBioguide() {
        return bioguide;
    }

    public void setBioguide(String bioguide) {
        this.bioguide = bioguide;
    }

    public leg_item(String image, String bio, String AllInfo, String bioguide){
        this.image = image;
        this.bio = bio;
        this.AllInfo = AllInfo;
        this.bioguide = bioguide;
    }
}
