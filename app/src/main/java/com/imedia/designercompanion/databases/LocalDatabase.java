package com.imedia.designercompanion.databases;

import android.content.Context;
import android.content.SharedPreferences;

import com.imedia.designercompanion.classes.AppUser;
import com.imedia.designercompanion.classes.FileToUpload;

public class LocalDatabase {

    public static class UserDatabase {
        public static final String DB_NAME = "userDetails";

        SharedPreferences userLocalDB;

        public UserDatabase(Context context) {
            userLocalDB = context.getSharedPreferences(DB_NAME, 0);
        }

        public void storeUserData(AppUser appUser) {
            SharedPreferences.Editor editor = userLocalDB.edit();
            editor.putString("fullname", appUser.getFullname());
            editor.putString("username", appUser.getUsername());
            editor.putString("email", appUser.getEmail());
            editor.putString("business_name", appUser.getBusiness_name());
            editor.putString("user_type", appUser.getUser_type());
            editor.commit();
        }

        public AppUser getLoggedInUser() {
            String name = userLocalDB.getString("fullname", "");
            String username = userLocalDB.getString("username", "");
            String email = userLocalDB.getString("email", "");
            String user_type = userLocalDB.getString("user_type", "");
            String business_name = userLocalDB.getString("business_name", "");

            AppUser storesUser = new AppUser(name, username, email, business_name, user_type);
            return storesUser;
        }

        public void setUserLoggedIn(boolean loggedIn) {
            SharedPreferences.Editor editor = userLocalDB.edit();
            editor.putBoolean("loggedIn", loggedIn);
            editor.commit();
        }

        public boolean userIsLoggedIn() {
            boolean userLoggedin = userLocalDB.getBoolean("loggedIn", false);
            return userLoggedin;
        }

        public void clearUserData() {
            SharedPreferences.Editor editor = userLocalDB.edit();
            editor.clear();
            editor.commit();
        }
    }

    public static class OtherDataBase {
        public static final String DB_NAME = "otherDetails";

        SharedPreferences otherLocalDB;

        public OtherDataBase(Context context) {
            otherLocalDB = context.getSharedPreferences(DB_NAME, 0);
        }

        public void storeInfoTemp(String name, String value) {
            SharedPreferences.Editor editor = otherLocalDB.edit();
            editor.putString(name, value);
            editor.commit();
        }

        public void storeFileToUpload(FileToUpload fileToUpload) {
            SharedPreferences.Editor editor = otherLocalDB.edit();
            editor.putString("file_url_to_upload", fileToUpload.getUrl());
            editor.putString("file_description_to_upload", fileToUpload.getDescription());
            editor.commit();
        }

        public String getInfoTemp(String name) {
            String info = otherLocalDB.getString(name, "");
            return info;
        }

        public void clearInfoTemp(String name) {
            SharedPreferences.Editor editor = otherLocalDB.edit();
            editor.remove(name);
            editor.commit();
        }
    }
}
