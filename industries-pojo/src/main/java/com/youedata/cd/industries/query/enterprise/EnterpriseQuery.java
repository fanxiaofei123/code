package com.youedata.cd.industries.query.enterprise;

import com.youedata.cd.industries.query.BaseQuery;
import org.apache.commons.lang.StringUtils;

/**
 * Created by cdyoue on 2016/6/3.
 */
public class EnterpriseQuery extends BaseQuery {
    private String buildingId;
    private String addressChange;// 1 地址变更
    private String majorBussinessChange; //1 主营业务变更
    private String changeCountsCondition; // 1统计主营业务变更数量 2 统计地址变更数量 3统计两个有变更数量
    private String majorChangeOrbusinessChange;//主营业务或者地址变更
    private Double longitude; //纬度
    private Double latitude;  //经度
    private String startTime;
    private String endTime;
    private Integer isModifyLog;
    private String sourceLogId;
    public String getAddressChange() {
        return addressChange;
    }

    public void setAddressChange(String addressChange) {
        this.addressChange = addressChange;
    }

    public String getMajorBussinessChange() {
        return majorBussinessChange;
    }

    public void setMajorBussinessChange(String majorBussinessChange) {
        this.majorBussinessChange = majorBussinessChange;
    }
    public  void removeChangeCondition(){
        setAddressChange(null);
        setMajorBussinessChange(null);
        setMajorChangeOrbusinessChange(null);
    }
    public String getChangeCountsCondition() {
        return changeCountsCondition;
    }

    public void setChangeCountsCondition(String changeCountsCondition) {
        this.changeCountsCondition = changeCountsCondition;
    }

    public String getSourceLogId() {
        return sourceLogId;
    }

    public void setSourceLogId(String sourceLogId) {
        this.sourceLogId = sourceLogId;
    }

    public String getMajorChangeOrbusinessChange() {
        return majorChangeOrbusinessChange;
    }

    public void setMajorChangeOrbusinessChange(String majorChangeOrbusinessChange) {
        this.majorChangeOrbusinessChange = majorChangeOrbusinessChange;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

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

    public String getStartTime() {
        if(StringUtils.isNotBlank(startTime)){
            return startTime + "-1";
        }
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        if(StringUtils.isNotBlank(endTime)){
           // String[] split = endTime.split("-");
            return endTime + "-31";
        }
        return endTime;
    }

    public Integer getIsModifyLog() {
        return isModifyLog;
    }

    public void setIsModifyLog(Integer isModifyLog) {
        this.isModifyLog = isModifyLog;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "EnterpriseQuery{" +
                "buildingId='" + buildingId + '\'' +
                ", addressChange='" + addressChange + '\'' +
                ", majorBussinessChange='" + majorBussinessChange + '\'' +
                ", changeCountsCondition='" + changeCountsCondition + '\'' +
                ", majorChangeOrbusinessChange='" + majorChangeOrbusinessChange + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", isModifyLog=" + isModifyLog +
                '}';
    }
}
