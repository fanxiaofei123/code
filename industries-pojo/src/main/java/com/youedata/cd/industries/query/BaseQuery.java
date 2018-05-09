package com.youedata.cd.industries.query;

/**
 * Created by cdyoue on 2016/5/31.
 */
public class BaseQuery {
 /**
  * 分页条件
  */
    private Integer currentPage=1;
    private Integer limit=10;
 /**
  * 查询条件
  */
    private String enterpriseName;//企业名称
    private String buildingName;//楼宇名称
    private String industryId;//产业
    private String categoryId;//门类
    private String tradeId;//行业
    private String streetId;//街道
    private String  communityId;//社区
    private Long id;


 public Integer getCurrentPage() {
  return currentPage;
 }
 public Integer getStart(){
    if(currentPage != null && limit != null){
      return (currentPage - 1) * limit;
    }else{
     return null;

    }
 }

 public void setCurrentPage(Integer startPage) {
  this.currentPage = startPage;
 }

 public Integer getLimit() {
  return limit;
 }

 public void setLimit(Integer limit) {
  this.limit = limit;
 }

 public String getEnterpriseName() {
  return enterpriseName;
 }

 public void setEnterpriseName(String enterpriseName) {
  this.enterpriseName = enterpriseName;
 }

 public String getBuildingName() {
  return buildingName;
 }

 public void setBuildingName(String buildingName) {
  this.buildingName = buildingName;
 }

 public String getIndustryId() {
  return industryId;
 }

 public void setIndustryId(String industryId) {
  this.industryId = industryId;
 }

 public String getCategoryId() {
  return categoryId;
 }

 public void setCategoryId(String categoryId) {
  this.categoryId = categoryId;
 }

 public String getTradeId() {
  return tradeId;
 }

 public void setTradeId(String tradeId) {
  this.tradeId = tradeId;
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

 public Long getId() {
  return id;
 }

 public void setId(Long id) {
  this.id = id;
 }
 @Override
 public String toString() {
  return "BaseQuery{" +
          "currentPage=" + currentPage +
          ", limit=" + limit +
          ", enterpriseName='" + enterpriseName + '\'' +
          ", buildingName='" + buildingName + '\'' +
          ", industryId='" + industryId + '\'' +
          ", categoryId='" + categoryId + '\'' +
          ", tradeId='" + tradeId + '\'' +
          ", streetId='" + streetId + '\'' +
          ", communityId='" + communityId + '\'' +
          ", id=" + id +
          '}';
 }
}
