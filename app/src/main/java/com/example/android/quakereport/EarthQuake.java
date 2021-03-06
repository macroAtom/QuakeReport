package com.example.android.quakereport;

public class EarthQuake {

    private Double mMagnitude;
    private String mLocation;
    /** Time of the earthquake */
    private long mTimeInMilliseconds;


    /**
     * 地震详情URL
     */
    private String mEventUrl;

    /**
     * Constructs a new {@link EarthQuake} object.
     *
     * @param magnitude is the magnitude (size) of the earthquake
     * @param location is the city location of the earthquake
     * @param timeInMilliseconds is the time in milliseconds (from the Epoch) when the
     *  earthquake happened
     */
    public EarthQuake(Double magnitude, String location, long timeInMilliseconds, String eventUrl) {
        this.mMagnitude = magnitude;
        this.mLocation = location;
        this.mTimeInMilliseconds = timeInMilliseconds;
        this.mEventUrl = eventUrl;
    }

    public Double getMagnitude() {
        return mMagnitude;
    }

    public String getLocation() {
        return mLocation;
    }

    /**
     * Returns the time of the earthquake.
     */
    public long getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    public String getEventUrl() {
        return mEventUrl;
    }
}
