package com.example.sahiwalhealthcare;

public class DoctorModel {
    String  Address;
    String City;
    String ClinicName;
    String CloseTime;
    String DoctorName;
    String Fee;
    String OpenTime;
    String Phone;
    String Specialize;
    String ZipCode;
    String DocUrl;
    String ClinicUrl;


    public DoctorModel() {
    }
    public DoctorModel(String address, String city, String clinicName, String closeTime, String doctorName, String fee, String openTime,
                       String phone, String specialize, String zipCode, String docUrl, String clinicUrl) {
        this.Address = address;
        this.City = city;
        this.ClinicName = clinicName;
        this.CloseTime = closeTime;
        this.DoctorName = doctorName;
        this.Fee = fee;
        this.OpenTime = openTime;
        this.Phone = phone;
        this.Specialize = specialize;
        this.ZipCode = zipCode;
        this.DocUrl = docUrl;
        this.ClinicUrl = clinicUrl;
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

    public String getClinicName() {
        return ClinicName;
    }

    public void setClinicName(String clinicName) {
        ClinicName = clinicName;
    }

    public String getCloseTime() {
        return CloseTime;
    }

    public void setCloseTime(String closeTime) {
        CloseTime = closeTime;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
    }

    public String getFee() {
        return Fee;
    }

    public void setFee(String fee) {
        Fee = fee;
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

    public String getSpecialize() {
        return Specialize;
    }

    public void setSpecialize(String specialize) {
        Specialize = specialize;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String zipCode) {
        ZipCode = zipCode;
    }

    public String getDocUrl() {
        return DocUrl;
    }

    public void setDocUrl(String docUrl) {
        DocUrl = docUrl;
    }

    public String getClinicUrl() {
        return ClinicUrl;
    }

    public void setClinicUrl(String clinicUrl) {
        ClinicUrl = clinicUrl;
    }
}
