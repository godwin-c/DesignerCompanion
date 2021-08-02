package com.imedia.designercompanion.network_operations.models;

import com.google.gson.annotations.SerializedName;

public class CustomerModel {
    @SerializedName("fullname")
    private String fullname;

    @SerializedName("phonenumber")
    private String phonenumber;

    @SerializedName("email")
    private String email;

   @SerializedName("address")
    private String address;

   @SerializedName("profile_photo")
    private String profile_photo;

    public CustomerModel(String fullname, String phonenumber, String email, String address, String profile_photo) {
        this.fullname = fullname;
        this.phonenumber = phonenumber;
        this.email = email;
        this.address = address;
        this.profile_photo = profile_photo;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }
}
