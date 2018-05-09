package com.youedata.cd.industries.dto.excel;

import com.youedata.cd.base.pojo.BaseDomain;
import java.io.Serializable;

public class FailedChangeinfo extends BaseDomain implements Serializable {
    private static final long serialVersionUID = 460451852296629468L;


    private String enterpriseName;

    private String changeDate;

    private String address;

    private Long logId;



    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    @Override
    public String toString() {
        return "FailedChangeinfo{" +
                "enterpriseName='" + enterpriseName + '\'' +
                ", changeDate='" + changeDate + '\'' +
                ", address='" + address + '\'' +
                ", logId=" + logId +
                '}';
    }
}