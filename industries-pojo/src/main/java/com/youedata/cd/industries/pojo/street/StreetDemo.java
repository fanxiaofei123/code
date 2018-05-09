package com.youedata.cd.industries.pojo.street;

import com.youedata.cd.base.pojo.BaseDomain;

/**
 * Created by chenyongke on 2016/5/30.
 */
public class StreetDemo extends BaseDomain {
    private String name;//街道名称
    private int enterpriseCount;//企业数量
    private double longitude;//经度
    private double latitude;//纬度

    public StreetDemo() {
    }

    public StreetDemo(String name, double latitude, double longitude, int enterpriseCount) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.enterpriseCount = enterpriseCount;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEnterpriseCount() {
        return enterpriseCount;
    }

    public void setEnterpriseCount(int enterpriseCount) {
        this.enterpriseCount = enterpriseCount;
    }

}
