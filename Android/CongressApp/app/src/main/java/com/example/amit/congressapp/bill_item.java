package com.example.amit.congressapp;

/**
 * Created by Amit on 11/17/2016.
 */

public class bill_item {


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getIntro() {
        return Intro;
    }

    public void setIntro(String intro) {
        Intro = intro;
    }

    private String Id;
    private String Title;
    private String Intro;


    public bill_item(String Id, String Title,String Intro){
        this.Id = Id;
        this.Title = Title;
        this.Intro = Intro;
    }
}
