package com.youedata.cd.industries.pojo.enterprise;

import java.util.Date;
import java.util.Map;

public class EnterpriseLog {
    private String registerNumber;

    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private String changeDate;
    private Boolean isOldAddress;
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_enterprise_log.id
     *
     * @mbggenerated
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_enterprise_log.enterprise_id
     *
     * @mbggenerated
     */
    private Long enterpriseId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_enterprise_log.major_business
     *
     * @mbggenerated
     */
    private String majorBusiness;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_enterprise_log.address
     *
     * @mbggenerated
     */
    private String address;

    public Boolean getIsOldAddress() {
        return isOldAddress;
    }

    public void setIsOldAddress(Boolean oldAddress) {
        isOldAddress = oldAddress;
    }

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_enterprise_log.address_modify_flag
     *
     * @mbggenerated
     */
    private Byte addressModifyFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_enterprise_log.major_business_modify_flag
     *
     * @mbggenerated
     */
    private Byte majorBusinessModifyFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_enterprise_log.building_id
     *
     * @mbggenerated
     */
    private Long buildingId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_enterprise_log.community_id
     *
     * @mbggenerated
     */
    private Long communityId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_enterprise_log.street_id
     *
     * @mbggenerated
     */
    private Long streetId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_enterprise_log.version_id
     *
     * @mbggenerated
     */
    private Integer versionId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_enterprise_log.id
     *
     * @return the value of t_enterprise_log.id
     *
     * @mbggenerated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_enterprise_log.id
     *
     * @param id the value for t_enterprise_log.id
     *
     * @mbggenerated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_enterprise_log.enterprise_id
     *
     * @return the value of t_enterprise_log.enterprise_id
     *
     * @mbggenerated
     */
    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_enterprise_log.enterprise_id
     *
     * @param enterpriseId the value for t_enterprise_log.enterprise_id
     *
     * @mbggenerated
     */
    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_enterprise_log.major_business
     *
     * @return the value of t_enterprise_log.major_business
     *
     * @mbggenerated
     */
    public String getMajorBusiness() {
        return majorBusiness;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_enterprise_log.major_business
     *
     * @param majorBusiness the value for t_enterprise_log.major_business
     *
     * @mbggenerated
     */
    public void setMajorBusiness(String majorBusiness) {
        this.majorBusiness = majorBusiness == null ? null : majorBusiness.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_enterprise_log.address
     *
     * @return the value of t_enterprise_log.address
     *
     * @mbggenerated
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_enterprise_log.address
     *
     * @param address the value for t_enterprise_log.address
     *
     * @mbggenerated
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_enterprise_log.address_modify_flag
     *
     * @return the value of t_enterprise_log.address_modify_flag
     *
     * @mbggenerated
     */
    public Byte getAddressModifyFlag() {
        return addressModifyFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_enterprise_log.address_modify_flag
     *
     * @param addressModifyFlag the value for t_enterprise_log.address_modify_flag
     *
     * @mbggenerated
     */
    public void setAddressModifyFlag(Byte addressModifyFlag) {
        this.addressModifyFlag = addressModifyFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_enterprise_log.major_business_modify_flag
     *
     * @return the value of t_enterprise_log.major_business_modify_flag
     *
     * @mbggenerated
     */
    public Byte getMajorBusinessModifyFlag() {
        return majorBusinessModifyFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_enterprise_log.major_business_modify_flag
     *
     * @param majorBusinessModifyFlag the value for t_enterprise_log.major_business_modify_flag
     *
     * @mbggenerated
     */
    public void setMajorBusinessModifyFlag(Byte majorBusinessModifyFlag) {
        this.majorBusinessModifyFlag = majorBusinessModifyFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_enterprise_log.building_id
     *
     * @return the value of t_enterprise_log.building_id
     *
     * @mbggenerated
     */
    public Long getBuildingId() {
        return buildingId;
    }

    private Double latitude;
    private Double longitude;

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_enterprise_log.building_id
     *
     * @param buildingId the value for t_enterprise_log.building_id
     *
     * @mbggenerated
     */
    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_enterprise_log.community_id
     *
     * @return the value of t_enterprise_log.community_id
     *
     * @mbggenerated
     */
    public Long getCommunityId() {
        return communityId;
    }

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_enterprise_log.community_id
     *
     * @param communityId the value for t_enterprise_log.community_id
     *
     * @mbggenerated
     */
    public void setCommunityId(Long communityId) {
        this.communityId = communityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_enterprise_log.street_id
     *
     * @return the value of t_enterprise_log.street_id
     *
     * @mbggenerated
     */
    public Long getStreetId() {
        return streetId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_enterprise_log.street_id
     *
     * @param streetId the value for t_enterprise_log.street_id
     *
     * @mbggenerated
     */
    public void setStreetId(Long streetId) {
        this.streetId = streetId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_enterprise_log.version_id
     *
     * @return the value of t_enterprise_log.version_id
     *
     * @mbggenerated
     */
    public Integer getVersionId() {
        return versionId;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_enterprise_log.version_id
     *
     * @param versionId the value for t_enterprise_log.version_id
     *
     * @mbggenerated
     */

    public void setVersionId(Integer versionId) {
        this.versionId = versionId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "EnterpriseLog{" +
                "id='" + id + '\'' +
                ", enterpriseId=" + enterpriseId +
                ", majorBusiness='" + majorBusiness + '\'' +
                ", address='" + address + '\'' +
                ", addressModifyFlag=" + addressModifyFlag +
                ", majorBusinessModifyFlag=" + majorBusinessModifyFlag +
                ", buildingId=" + buildingId +
                ", communityId=" + communityId +
                ", streetId=" + streetId +
                ", versionId=" + versionId +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }


    public String enterpriseLogMapper(Map parm) {
        return null;
    }
}