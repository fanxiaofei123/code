package com.youedata.cd.industries.pojo.test;

/**
 * Created by honshe on 2016/5/30.
 */
public class HSDTO {

    private long streetId;
    private float logitude;
    private float latitude;
    private int count;
    private String address;

    public HSDTO() {
    }

    public HSDTO(float logitude, float latitude) {
        this.logitude = logitude;
        this.latitude = latitude;
    }

    public long getStreetId() {
        return streetId;
    }

    public void setStreetId(long streetId) {
        this.streetId = streetId;
    }

    public float getLogitude() {
        return logitude;
    }

    public void setLogitude(float logitude) {
        this.logitude = logitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
