package com.imedia.designercompanion.network_operations.models;

import com.google.gson.annotations.SerializedName;

public class CustomerMearsurementModelResponse {
    @SerializedName("_id")
    private String _id;
    @SerializedName("Shoulder")
    private String Shoulder;
    @SerializedName("Burst")
    private String Burst;
    @SerializedName("Underburst")
    private String Underburst;
    @SerializedName("Shoulder to Nipple point")
    private String ShouldertoNipplePoint;
    @SerializedName("Shoulder to Underburst")
    private String ShouldertoUnderburst;
    @SerializedName("Shoulder to Waist")
    private String ShouldertoWaist;
    @SerializedName("Shoulder to Knee")
    private String ShouldertoKnee;
    @SerializedName("Shoulder to Mid length")
    private String ShouldertoMidlength;
    @SerializedName("Waist")
    private String Waist;
    @SerializedName("Hip")
    private String Hip;
    @SerializedName("Full Length")
    private String FullLength;
    @SerializedName("Waist to Knee")
    private String WaisttoKnee;
    @SerializedName("Sleeve Length")
    private String SleeveLength;
    @SerializedName("Round Sleeve")
    private String RoundSleeve;
    @SerializedName("Customer ID")
    private String CustomerID;
    @SerializedName("Off Shoulder")
    private String OffShoulder;
    @SerializedName("Trouser length")
    private String Trouserlength;

    public CustomerMearsurementModelResponse(String _id, String shoulder, String burst, String underburst,
                                             String shouldertoNipplePoint, String shouldertoUnderburst,
                                             String shouldertoWaist, String shouldertoKnee, String shouldertoMidlength,
                                             String waist, String hip, String fullLength, String waisttoKnee,
                                             String sleeveLength, String roundSleeve, String customerID,
                                             String offShoulder, String trouserlength) {
        this._id = _id;
        Shoulder = shoulder;
        Burst = burst;
        Underburst = underburst;
        ShouldertoNipplePoint = shouldertoNipplePoint;
        ShouldertoUnderburst = shouldertoUnderburst;
        ShouldertoWaist = shouldertoWaist;
        ShouldertoKnee = shouldertoKnee;
        ShouldertoMidlength = shouldertoMidlength;
        Waist = waist;
        Hip = hip;
        FullLength = fullLength;
        WaisttoKnee = waisttoKnee;
        SleeveLength = sleeveLength;
        RoundSleeve = roundSleeve;
        CustomerID = customerID;
        OffShoulder = offShoulder;
        Trouserlength = trouserlength;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getShoulder() {
        return Shoulder;
    }

    public void setShoulder(String shoulder) {
        Shoulder = shoulder;
    }

    public String getBurst() {
        return Burst;
    }

    public void setBurst(String burst) {
        Burst = burst;
    }

    public String getUnderburst() {
        return Underburst;
    }

    public void setUnderburst(String underburst) {
        Underburst = underburst;
    }

    public String getShouldertoNipplePoint() {
        return ShouldertoNipplePoint;
    }

    public void setShouldertoNipplePoint(String shouldertoNipplePoint) {
        ShouldertoNipplePoint = shouldertoNipplePoint;
    }

    public String getShouldertoUnderburst() {
        return ShouldertoUnderburst;
    }

    public void setShouldertoUnderburst(String shouldertoUnderburst) {
        ShouldertoUnderburst = shouldertoUnderburst;
    }

    public String getShouldertoWaist() {
        return ShouldertoWaist;
    }

    public void setShouldertoWaist(String shouldertoWaist) {
        ShouldertoWaist = shouldertoWaist;
    }

    public String getShouldertoKnee() {
        return ShouldertoKnee;
    }

    public void setShouldertoKnee(String shouldertoKnee) {
        ShouldertoKnee = shouldertoKnee;
    }

    public String getShouldertoMidlength() {
        return ShouldertoMidlength;
    }

    public void setShouldertoMidlength(String shouldertoMidlength) {
        ShouldertoMidlength = shouldertoMidlength;
    }

    public String getWaist() {
        return Waist;
    }

    public void setWaist(String waist) {
        Waist = waist;
    }

    public String getHip() {
        return Hip;
    }

    public void setHip(String hip) {
        Hip = hip;
    }

    public String getFullLength() {
        return FullLength;
    }

    public void setFullLength(String fullLength) {
        FullLength = fullLength;
    }

    public String getWaisttoKnee() {
        return WaisttoKnee;
    }

    public void setWaisttoKnee(String waisttoKnee) {
        WaisttoKnee = waisttoKnee;
    }

    public String getSleeveLength() {
        return SleeveLength;
    }

    public void setSleeveLength(String sleeveLength) {
        SleeveLength = sleeveLength;
    }

    public String getRoundSleeve() {
        return RoundSleeve;
    }

    public void setRoundSleeve(String roundSleeve) {
        RoundSleeve = roundSleeve;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

    public String getOffShoulder() {
        return OffShoulder;
    }

    public void setOffShoulder(String offShoulder) {
        OffShoulder = offShoulder;
    }

    public String getTrouserlength() {
        return Trouserlength;
    }

    public void setTrouserlength(String trouserlength) {
        Trouserlength = trouserlength;
    }
}
