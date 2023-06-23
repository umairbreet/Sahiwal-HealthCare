package com.example.sahiwalhealthcare;

public class UserModel {
    String name,phone,userImage,userID;

    public UserModel() {
    }

    public UserModel(String name, String phone, String userImage, String userID) {
        this.name = name;
        this.phone = phone;
        this.userImage = userImage;
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
