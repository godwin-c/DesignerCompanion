package com.imedia.designercompanion.classes;

public class AppUser {
    private String fullname;
    private String username;
    private String email;
    private String business_name;
    private String user_type;

    public AppUser(String fullname, String username, String email, String business_name, String user_type) {
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.business_name = business_name;
        this.user_type = user_type;
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

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}
