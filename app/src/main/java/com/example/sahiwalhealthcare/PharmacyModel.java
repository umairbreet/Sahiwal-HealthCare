package com.example.sahiwalhealthcare;

public class PharmacyModel {
    String  Address;
    String City;
    String PharmacyName;
    String CloseTime;
    String OwnerName;
    String OpenTime;
    String Phone;
    String ZipCode;
    String OwnerURL;
    String PharmacyURL;


    public PharmacyModel() {
    }

    public PharmacyModel(String address, String city, String pharmacyName, String closeTime, String ownerName,
                         String openTime, String phone, String zipCode, String ownerURL, String pharmacyURL) {
        this.Address = address;
        this.City = city;
        this.PharmacyName = pharmacyName;
        this.CloseTime = closeTime;
        this.OwnerName = ownerName;
        this.OpenTime = openTime;
        this.Phone = phone;
        this.ZipCode = zipCode;
        this.OwnerURL = ownerURL;
        this.PharmacyURL = pharmacyURL;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getPharmacyName() {
        return PharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        PharmacyName = pharmacyName;
    }

    public String getCloseTime() {
        return CloseTime;
    }

    public void setCloseTime(String closeTime) {
        CloseTime = closeTime;
    }

    public String getOwnerName() {
        return OwnerName;
    }

    public void setOwnerName(String ownerName) {
        OwnerName = ownerName;
    }

    public String getOpenTime() {
        return OpenTime;
    }

    public void setOpenTime(String openTime) {
        OpenTime = openTime;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String zipCode) {
        ZipCode = zipCode;
    }

    public String getOwnerURL() {
        return OwnerURL;
    }

    public void setOwnerURL(String ownerURL) {
        OwnerURL = ownerURL;
    }

    public String getPharmacyURL() {
        return PharmacyURL;
    }

    public void setPharmacyURL(String pharmacyURL) {
        PharmacyURL = pharmacyURL;
    }
}
