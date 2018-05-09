package com.youedata.cd.industries.pojo.enterprise;

import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cdyoue on 2016/6/3.
 */
public class EnterpriseResult {
    private Long id;
    private Long communityId;
    private Long streetId;
    private String name;
    private String address;
    private String  majorBusiness;
    private String oldAddress;
    private String oldMajorBusiness;
    private Double latitude;
    private Integer employeeCount;
    private Double  longitude;
    private Byte majorBussinessChange;
    private Byte addressChange;
    private String phoneNumber;
    private String tradeName;
    private Date registerTime;
    private String registerCapital;
    private String openFlag;
    private Integer addressModifyFlag;
    private Integer majorBusinessModifyFlag;
    private Integer isModifyLog;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMajorBusiness() {
        return majorBusiness;
    }

    public void setMajorBusiness(String majorBusiness) {
        this.majorBusiness = majorBusiness;
    }

    public String getOldAddress() {
        return oldAddress;
    }

    public void setOldAddress(String oldAddress) {
        this.oldAddress = oldAddress;
    }

    public String getOldMajorBusiness() {
        return oldMajorBusiness;
    }

    public void setOldMajorBusiness(String oldMajorBusiness) {
        this.oldMajorBusiness = oldMajorBusiness;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getEmployeeCount() {
        if(employeeCount == null ||employeeCount ==0 ){
            return "暂无";
        }
        return String.valueOf(employeeCount);
    }

    public void setEmployeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
    }

    public String getRegisterCapital() {
        return registerCapital;
    }

    public void setRegisterCapital(String registerCapital) {
        this.registerCapital = registerCapital;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Byte getMajorBussinessChange() {
        return majorBussinessChange;
    }

    public void setMajorBussinessChange(Byte majorBussinessChange) {
        this.majorBussinessChange = majorBussinessChange;
    }

    public Byte getAddressChange() {
        return addressChange;
    }

    public void setAddressChange(Byte addressChange) {
        this.addressChange = addressChange;
    }


    public String getPhoneNumber() {
        if(StringUtils.isBlank(phoneNumber)){
            return "暂无";
        }
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTradeName() {
        if(StringUtils.isBlank(tradeName)){
            return "暂无";
        }
        return tradeName;
    }

    public String getRegisterTime() {
        if(registerTime != null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String formatData = sdf.format(registerTime);
            return formatData;

        }else{
            return "暂无";
        }
    }
    public String getOpenFlag() {
//        if(openFlag !=null && openFlag == 0){
//            return "停业";
//        }else if(openFlag !=null && openFlag == 1){
//            return "营业";
//        }

        return openFlag;

    }

    public Long getStreetId() {
        return streetId;
    }

    public void setStreetId(Long streetId) {
        this.streetId = streetId;
    }

    public Long getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    public void setOpenFlag(String openFlag) {
        this.openFlag = openFlag;
    }
    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public Integer getAddressModifyFlag() {
        return addressModifyFlag;
    }

    public void setAddressModifyFlag(Integer addressModifyFlag) {
        this.addressModifyFlag = addressModifyFlag;
    }

    public Integer getMajorBusinessModifyFlag() {
        return majorBusinessModifyFlag;
    }

    public void setMajorBusinessModifyFlag(Integer majorBusinessModifyFlag) {
        this.majorBusinessModifyFlag = majorBusinessModifyFlag;
    }

    public Integer getIsModifyLog() {
        return isModifyLog;
    }

    public void setIsModifyLog(Integer isModifyLog) {
        this.isModifyLog = isModifyLog;
    }


    @Override
    public String toString() {
        return "EnterpriseResult{" +
                "id=" + id +
                ", communityId=" + communityId +
                ", streetId=" + streetId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", majorBusiness='" + majorBusiness + '\'' +
                ", oldAddress='" + oldAddress + '\'' +
                ", oldMajorBusiness='" + oldMajorBusiness + '\'' +
                ", latitude=" + latitude +
                ", employeeCount=" + employeeCount +
                ", longitude=" + longitude +
                ", majorBussinessChange=" + majorBussinessChange +
                ", addressChange=" + addressChange +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", tradeName='" + tradeName + '\'' +
                ", registerTime=" + registerTime +
                ", registerCapital='" + registerCapital + '\'' +
                ", openFlag='" + openFlag + '\'' +
                ", addressModifyFlag=" + addressModifyFlag +
                ", majorBusinessModifyFlag=" + majorBusinessModifyFlag +
                ", isModifyLog=" + isModifyLog +
                '}';
    }
}
