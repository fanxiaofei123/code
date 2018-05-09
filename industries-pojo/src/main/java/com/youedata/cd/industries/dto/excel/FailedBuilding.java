package com.youedata.cd.industries.dto.excel;

import com.youedata.cd.base.pojo.BaseDomain;
import java.io.Serializable;

public class FailedBuilding extends BaseDomain implements Serializable {
    private static final long serialVersionUID = 172106118001851223L;


    private String buildingName;

    private String address;

    private String street;

    private String community;

    private String registerNumber;

    private Long logId;



    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }


    @Override
    public String toString() {
        return "FailedBuilding{" +
                "buildingName='" + buildingName + '\'' +
                ", address='" + address + '\'' +
                ", street='" + street + '\'' +
                ", community='" + community + '\'' +
                ", registerNumber='" + registerNumber + '\'' +
                ", logId=" + logId +
                '}';
    }
}