package com.example.max.mainwindow.museumpackage;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;

public class Museum {
    //Класс музея
    @PropertyName("name")
    public String mname;

    @PropertyName("rating")
    public float rating=0.0f;

    @PropertyName("splitter")
    public int splitter=0;

    @PropertyName("adress")
    public String adress;

    @PropertyName("ui")
    public String ui;

    @PropertyName("phone")
    public String phone;

    @PropertyName("url")
    public String URL;

    @PropertyName("website")
    public String website;

    @PropertyName("trivia")
    public String trivia;

    @PropertyName("v")
    public double v;

    @PropertyName("v1")
    public double v1;

    @Exclude
    public String getUi() {
        return ui;
    }

    @Exclude
    public void setUi(String ui) {
        this.ui = ui;
    }

    public Museum() {

    }

    public Museum(String mname, String adress, String phone, String URL, String website, String trivia, double v, double v1) {
        this.mname = mname;
        this.adress = adress;
        this.phone = phone;
        this.URL = URL;
        this.website = website;
        this.trivia=trivia;
        this.v=v;
        this.v1=v1;
    }


    float getUsersRatingValue(){
        if(splitter==0){
            return 0;
        }else
            return rating/splitter;
    }
    @Exclude
    public float getRating() {
        return rating;
    }

    @Exclude
    public void setRating(float rating) {
        this.rating = rating;
    }


    @Exclude
    public int getSplitter() {
        return splitter;
    }

    @Exclude
    public void setSplitter(int splitter) {
        this.splitter = splitter;
    }

    @Exclude
    public double getV() {
        return v;
    }

    @Exclude
    public void setV(double v) {
        this.v = v;
    }

    @Exclude
    public double getV1() {
        return v1;
    }

    @Exclude
    public void setV1(double v1) {
        this.v1 = v1;
    }

    @Exclude
    public String getPhone() {
        return phone;
    }

    @Exclude
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Exclude
    public String getMname() {
        return mname;
    }

    @Exclude
    public void setMname(String mname) {
        this.mname = mname;
    }

    @Exclude
    public String getAdress() {
        return adress;
    }

    @Exclude
    public void setAdress(String adress) {
        this.adress = adress;
    }

    @Exclude
    public String getURL() {
        return URL;
    }

    @Exclude
    public void setURL(String URL) {
        this.URL = URL;
    }

    @Exclude
    public String getWebsite() {
        return website;
    }

    @Exclude
    public void setWebsite(String website) {
        this.website = website;
    }

    @Exclude
    public String getTrivia() {
        return trivia;
    }

    @Exclude
    public void setTrivia(String trivia) {
        this.trivia = trivia;
    }
}
