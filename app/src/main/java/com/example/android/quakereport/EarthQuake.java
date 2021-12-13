package com.example.android.quakereport;

public class EarthQuake {

    private String mMagnitude;
    private String mCity;
    private String mDate;

    public EarthQuake(String magnitude, String city, String date) {
        this.mMagnitude = magnitude;
        this.mCity = city;
        this.mDate = date;
    }

    public String getMagnitude() {
        return mMagnitude;
    }

    public String getCity() {
        return mCity;
    }

    public String getDate() {
        return mDate;
    }
}
