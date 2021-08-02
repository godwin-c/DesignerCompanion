package com.imedia.designercompanion.network_operations.models;

import com.google.gson.annotations.SerializedName;

public class MyDesignsModel {
    @SerializedName("description")
    private String description;

    @SerializedName("business_name")
    private String business_name;

    @SerializedName("image_link")
    private String image_link;

    public MyDesignsModel(String description, String business_name, String image_link) {
        this.description = description;
        this.business_name = business_name;
        this.image_link = image_link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBusiness_name() {
        return business_name;
    }

    public void setBusiness_name(String business_name) {
        this.business_name = business_name;
    }

    public String getImage_link() {
        return image_link;
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }
}
