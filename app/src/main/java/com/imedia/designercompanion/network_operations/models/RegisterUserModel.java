package com.imedia.designercompanion.network_operations.models;

import com.google.gson.annotations.SerializedName;

public class RegisterUserModel {
    @SerializedName("business_name")
    private String businness_name;
    @SerializedName("fullname")
    private String fullname;
    @SerializedName("username")
    private String username;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("user_type")
    private String user_type;

    public RegisterUserModel(String businness_name, String fullname, String username, String email, String password, String user_type) {
        this.businness_name = businness_name;
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.user_type = user_type;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getBusinness_name() {
        return businness_name;
    }

    public void setBusinness_name(String businness_name) {
        this.businness_name = businness_name;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
