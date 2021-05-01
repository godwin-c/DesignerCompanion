package com.imedia.designercompanion.network_operations.models;

import com.google.gson.annotations.SerializedName;

public class CustomerModel {
    @SerializedName("fullname")
    private String fullname;

    @SerializedName("phonenumber")
    private String phonenumber;

    @SerializedName("email")
    private String email;

    public CustomerModel(String fullname, String phonenumber, String email) {
        this.fullname = fullname;
        this.phonenumber = phonenumber;
        this.email = email;
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
}
