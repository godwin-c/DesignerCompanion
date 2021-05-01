package com.imedia.designercompanion.network_operations.models;

import com.google.gson.annotations.SerializedName;

public class LoginUserResponseModel {

    @SerializedName("_id")
    private String _id;
    @SerializedName("user_type")
    private String user_type;
    @SerializedName("username")
    private String username;
    @SerializedName("fullname")
    private String fullname;
    @SerializedName("password")
    private String password;
    @SerializedName("business_name")
    private String business_name;
    @SerializedName("email")
    private String email;

    public LoginUserResponseModel(String _id, String user_type, String username, String fullname, String password, String business_name, String email) {
        this._id = _id;
        this.user_type = user_type;
        this.username = username;
        this.fullname = fullname;
        this.password = password;
        this.business_name = business_name;
        this.email = email;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
