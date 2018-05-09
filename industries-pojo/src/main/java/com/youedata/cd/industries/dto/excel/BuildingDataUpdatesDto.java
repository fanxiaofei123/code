package com.youedata.cd.industries.dto.excel;


/**
 * Created by Administrator on 2016/7/11 0011.
 */
public class BuildingDataUpdatesDto  {
    private String id;
    @ExcelVOAttribute(name = "楼宇名称", column = "A")
    private String buildingName;
    @ExcelVOAttribute(name = "地址", column = "B")
    private String address;
    @ExcelVOAttribute(name = "所在街道", column = "C")
    private String street;
    @ExcelVOAttribute(name = "所在社区", column = "D")
    private String community;
    //企业注册号可能有多个，以逗号分割。
    @ExcelVOAttribute(name = "企业注册号", column = "E")
    private String enRegisterNumbers;
    @ExcelVOAttribute(name = "错误列", column = "F")
    private String errorColumn;
    private String streetId;
    private String communityId;

    private Double longitude;
    private Double latitude;

    //日志外键
    private String logId;

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getStreetId() {
        return streetId;
    }

    public void setStreetId(String streetId) {
        this.streetId = streetId;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public BuildingDataUpdatesDto() {
    }

    @Override
    public String toString() {
        return "BuildingDataUpdatesDto{" +
                "id='" + id + '\'' +
                ", buildingName='" + buildingName + '\'' +
                ", address='" + address + '\'' +
                ", street='" + street + '\'' +
                ", community='" + community + '\'' +
                ", enRegisterNumbers='" + enRegisterNumbers + '\'' +
                ", errorColumn='" + errorColumn + '\'' +
                '}';
    }

    public String getErrorColumn() {
        return errorColumn;
    }

    public void setErrorColumn(String errorColumn) {
        this.errorColumn = errorColumn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getEnRegisterNumbers() {
        return enRegisterNumbers;
    }

    public void setEnRegisterNumbers(String enRegisterNumbers) {
        this.enRegisterNumbers = enRegisterNumbers;
    }
}
