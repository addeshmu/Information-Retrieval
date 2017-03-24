package com.example.amit.congressapp;

/**
 * Created by Amit on 11/20/2016.
 */

public class com_item {
    public String getCId() {
        return CId;
    }

    public void setCId(String CId) {
        this.CId = CId;
    }

    public String getCTitle() {
        return CTitle;
    }

    public void setCTitle(String CTitle) {
        this.CTitle = CTitle;
    }

    public String getCDate() {
        return CDate;
    }

    public void setCDate(String CDate) {
        this.CDate = CDate;
    }

    private String CId;
    private String CTitle;
    private String CDate;


    public com_item(String CId, String CTitle,String CDate){
        this.CId = CId;
        this.CTitle = CTitle;
        this.CDate = CDate;
    }
}
