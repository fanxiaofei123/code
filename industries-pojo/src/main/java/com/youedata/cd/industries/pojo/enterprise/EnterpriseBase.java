package com.youedata.cd.industries.pojo.enterprise;

import com.youedata.cd.base.pojo.BaseDomain;

import java.io.Serializable;
import java.sql.Date;

public class EnterpriseBase extends BaseDomain implements Serializable  {
	 private static final long serialVersionUID = 336341678439505037L;


	    /**
	     * 企业名称
	     */
	    private String name;

	    /**
	     * 所属行业
	     */
	    private Long tradeId;

	    /**
	     * 所属产业（一产，二产，三产）
	     */
	    private Long industryId;

	    /**
	     * 所属门类名称(产业包含行业，行业包含门类名称)
	     */
	    private Long categoryId;

	    /**
	     * 注册时详细地址
	     */
	    private String registerAddress;

	    /**
	     * 注册时主营业务
	     */
	    private String registerMajorBusiness;

	    /**
	     * 注册资金
	     */
	    private String registerCapital;

	    /**
	     * 员工数量
	     */
	    private String employeeCount;

	    /**
	     * 经营地(如：北京、上海)
	     */
	    private String operateLocation;

	    /**
	     * 注册地(如：北京、上海)
	     */
	    private String registerLocation;

	    /**
	     * 企业类型
	     */
	    private String enterpriseType;

	    /**
	     * 企业营收
	     */
	    private Double revenue;

	    /**
	     * 纳税
	     */
	    private Double tax;

	    /**
	     * 注册日期
	     */
	    private String registerTime;

	    /**
	     * 数据录入时间
	     */
	    private Date createTime;

	    /**
	     * 联系人
	     */
	    private String linkman;

	    /**
	     * 联系电话
	     */
	    private String phoneNumber;

	    /**
	     * 企业简介
	     */
	    private String introduce;

	    /**
	     * 营业状态（0：停业；1：营业）
	     */
	    private String openFlag;

	    /**
	     * 企业信息记录id
	     */
	    private String sourceLogId;

		/*对应的街道ID*/
		private String streetId;

		/*对应的社区ID*/
		private String communityId;
		/*街道名称*/
		private String streetName;
		/*社区名称*/
		private String communityName;
		/*企业地址信息*/
		private String address;
		/*主营业务*/
		private String majorBusiness;
		/*企业注册号*/
	 	private String registerNumber;
		/*行业代码*/
		private String industryCode;
		/*企业上传时间*/
		private  Date uploadDate;
		/*类型*/
		private String type;

//	所属产业名称
	private String categoryName;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	private String buildingId;

	public String getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIndustryCode() {
		return industryCode;
	}

	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}

	public String getRegisterNumber() {
		return registerNumber;
	}

	public void setRegisterNumber(String registerNumber) {
		this.registerNumber = registerNumber;
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

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
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

	/**
	     * @return 企业名称
	     */
	    public String getName() {
	        return name;
	    }

	    /**
	     * @param name 
		 *            企业名称
	     */
	    public void setName(String name) {
	        this.name = name;
	    }

	    /**
	     * @return 所属行业
	     */
	    public Long getTradeId() {
	        return tradeId;
	    }

	    /**
	     * @param tradeId 
		 *            所属行业
	     */
	    public void setTradeId(Long tradeId) {
	        this.tradeId = tradeId;
	    }

	    /**
	     * @return 所属产业（一产，二产，三产）
	     */
	    public Long getIndustryId() {
	        return industryId;
	    }

	    /**
	     * @param industryId 
		 *            所属产业（一产，二产，三产）
	     */
	    public void setIndustryId(Long industryId) {
	        this.industryId = industryId;
	    }

	    /**
	     * @return 所属门类名称(产业包含行业，行业包含门类名称)
	     */
	    public Long getCategoryId() {
	        return categoryId;
	    }

	    /**
	     * @param categoryId 
		 *            所属门类名称(产业包含行业，行业包含门类名称)
	     */
	    public void setCategoryId(Long categoryId) {
	        this.categoryId = categoryId;
	    }

	    /**
	     * @return 注册时详细地址
	     */
	    public String getRegisterAddress() {
	        return registerAddress;
	    }

	    /**
	     * @param registerAddress 
		 *            注册时详细地址
	     */
	    public void setRegisterAddress(String registerAddress) {
	        this.registerAddress = registerAddress;
	    }

	    /**
	     * @return 注册时主营业务
	     */
	    public String getRegisterMajorBusiness() {
	        return registerMajorBusiness;
	    }

	    /**
	     * @param registerMajorBusiness 
		 *            注册时主营业务
	     */
	    public void setRegisterMajorBusiness(String registerMajorBusiness) {
	        this.registerMajorBusiness = registerMajorBusiness;
	    }

	public String getRegisterCapital() {
		return registerCapital;
	}

	public void setRegisterCapital(String registerCapital) {
		this.registerCapital = registerCapital;
	}

	/**
	     * @return 员工数量
	     */
	    public String getEmployeeCount() {
	        return employeeCount;
	    }

	    /**
	     * @param employeeCount 
		 *            员工数量
	     */
	    public void setEmployeeCount(String employeeCount) {
	        this.employeeCount = employeeCount;
	    }

	    /**
	     * @return 经营地(如：北京、上海)
	     */
	    public String getOperateLocation() {
	        return operateLocation;
	    }

	    /**
	     * @param operateLocation 
		 *            经营地(如：北京、上海)
	     */
	    public void setOperateLocation(String operateLocation) {
	        this.operateLocation = operateLocation;
	    }

	    /**
	     * @return 注册地(如：北京、上海)
	     */
	    public String getRegisterLocation() {
	        return registerLocation;
	    }

	    /**
	     * @param registerLocation 
		 *            注册地(如：北京、上海)
	     */
	    public void setRegisterLocation(String registerLocation) {
	        this.registerLocation = registerLocation;
	    }

	    /**
	     * @return 企业类型
	     */
	    public String getEnterpriseType() {
	        return enterpriseType;
	    }

	    /**
	     * @param enterpriseType 
		 *            企业类型
	     */
	    public void setEnterpriseType(String enterpriseType) {
	        this.enterpriseType = enterpriseType;
	    }

	    /**
	     * @return 企业营收
	     */
	    public Double getRevenue() {
	        return revenue;
	    }

	    /**
	     * @param revenue 
		 *            企业营收
	     */
	    public void setRevenue(Double revenue) {
	        this.revenue = revenue;
	    }

	    /**
	     * @return 纳税
	     */
	    public Double getTax() {
	        return tax;
	    }

	    /**
	     * @param tax 
		 *            纳税
	     */
	    public void setTax(Double tax) {
	        this.tax = tax;
	    }


	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	/**
	     * @return 数据录入时间
	     */
	    public Date getCreateTime() {
	        return createTime;
	    }

	    /**
	     * @param createTime 
		 *            数据录入时间
	     */
	    public void setCreateTime(Date createTime) {
	        this.createTime = createTime;
	    }

	    /**
	     * @return 联系人
	     */
	    public String getLinkman() {
	        return linkman;
	    }

	    /**
	     * @param linkman 
		 *            联系人
	     */
	    public void setLinkman(String linkman) {
	        this.linkman = linkman;
	    }

	    /**
	     * @return 联系电话
	     */
	    public String getPhoneNumber() {
	        return phoneNumber;
	    }

	    /**
	     * @param phoneNumber 
		 *            联系电话
	     */
	    public void setPhoneNumber(String phoneNumber) {
	        this.phoneNumber = phoneNumber;
	    }

	    /**
	     * @return 企业简介
	     */
	    public String getIntroduce() {
	        return introduce;
	    }

	    /**
	     * @param introduce 
		 *            企业简介
	     */
	    public void setIntroduce(String introduce) {
	        this.introduce = introduce;
	    }


	    /**
	     * @return 企业信息记录id
	     */
	    public String getSourceLogId() {
	        return sourceLogId;
	    }

	    /**
	     * @param sourceLogId 
		 *            企业信息记录id
	     */
	    public void setSourceLogId(String sourceLogId) {
	        this.sourceLogId = sourceLogId;
	    }

	public String getOpenFlag() {
		return openFlag;
	}

	public void setOpenFlag(String openFlag) {
		this.openFlag = openFlag;
	}

	@Override
	public String toString() {
		return "EnterpriseBase{" +
				"name='" + name + '\'' +
				", tradeId=" + tradeId +
				", industryId=" + industryId +
				", categoryId=" + categoryId +
				", registerAddress='" + registerAddress + '\'' +
				", registerMajorBusiness='" + registerMajorBusiness + '\'' +
				", registerCapital='" + registerCapital + '\'' +
				", employeeCount='" + employeeCount + '\'' +
				", operateLocation='" + operateLocation + '\'' +
				", registerLocation='" + registerLocation + '\'' +
				", enterpriseType='" + enterpriseType + '\'' +
				", revenue=" + revenue +
				", tax=" + tax +
				", registerTime='" + registerTime + '\'' +
				", createTime=" + createTime +
				", linkman='" + linkman + '\'' +
				", phoneNumber='" + phoneNumber + '\'' +
				", introduce='" + introduce + '\'' +
				", openFlag='" + openFlag + '\'' +
				", sourceLogId='" + sourceLogId + '\'' +
				", streetId='" + streetId + '\'' +
				", communityId='" + communityId + '\'' +
				", streetName='" + streetName + '\'' +
				", communityName='" + communityName + '\'' +
				", address='" + address + '\'' +
				", majorBusiness='" + majorBusiness + '\'' +
				", registerNumber='" + registerNumber + '\'' +
				", industryCode='" + industryCode + '\'' +
				", uploadDate=" + uploadDate +
				", type='" + type + '\'' +
				'}';
	}
}