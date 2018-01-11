package com.memeswow.Adapters;

import java.io.Serializable;

/**
 * Created by Prateek on 1/10/2018.
 * Base user class for User profile . Contains details about the user
 */

public class UserSkeleton implements Serializable {
    private String id;
    private String fname;
    private String phone;
    private int coins;
    private String imgURL;
    private String email;
    private String about;


    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }
    public UserSkeleton(){
        fname="";
        email="";
        phone="";
        about="";
        imgURL="";

    }



}
