package com.youedata.cd.industries.pojo.street;

import com.youedata.cd.base.pojo.BaseDomain;
import com.youedata.cd.industries.pojo.community.Community;

import java.util.ArrayList;
import java.util.List;

public class Street extends BaseDomain{
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_street.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_street.name
     *
     * @mbggenerated
     */
    /**
     * 社区
     */
    private List<Community> coummunitys = new ArrayList<Community>();
    private String name;
    private Integer enterpriseCounts;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_street.type
     *
     * @mbggenerated
     */
    private Integer type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_street.longitude
     *
     * @mbggenerated
     */
    private Double longitude;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_street.latitude
     *
     * @mbggenerated
     */
    private Double latitude;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_street.id
     *
     * @return the value of t_street.id
     *
     * @mbggenerated
     */
    private int enterpriseCount;

    public int getEnterpriseCount() {
        return enterpriseCount;
    }

    public void setEnterpriseCount(int enterpriseCount) {
        this.enterpriseCount = enterpriseCount;
    }

    public Long getId() {
        return id;
    }

    public Integer getEnterpriseCounts() {
        if(enterpriseCounts == null){
            return 0;
        }
        return enterpriseCounts;
    }

    public void setEnterpriseCounts(Integer enterpriseCounts) {
        this.enterpriseCounts = enterpriseCounts;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_street.id
     *
     * @param id the value for t_street.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_street.name
     *
     * @return the value of t_street.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_street.name
     *
     * @param name the value for t_street.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_street.type
     *
     * @return the value of t_street.type
     *
     * @mbggenerated
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_street.type
     *
     * @param type the value for t_street.type
     *
     * @mbggenerated
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_street.longitude
     *
     * @return the value of t_street.longitude
     *
     * @mbggenerated
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_street.longitude
     *
     * @param longitude the value for t_street.longitude
     *
     * @mbggenerated
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_street.latitude
     *
     * @return the value of t_street.latitude
     *
     * @mbggenerated
     */
    public Double getLatitude() {
        return latitude;
    }

    public List<Community> getCoummunitys() {
        return coummunitys;
    }

    public void setCoummunitys(List<Community> coummunitys) {
        this.coummunitys = coummunitys;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_street.latitude
     *
     * @param latitude the value for t_street.latitude
     *
     * @mbggenerated
     */

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Street() {
    }

    public Street(String name, Double longitude, Double latitude, int enterpriseCount) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.enterpriseCount = enterpriseCount;
    }

    @Override
    public String toString() {
        return "Street{" +
                "id=" + id +
                ", coummunitys=" + coummunitys +
                ", name='" + name + '\'' +
                ", enterpriseCounts=" + enterpriseCounts +
                ", type=" + type +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", enterpriseCount=" + enterpriseCount +
                '}';
    }
}
