package com.youedata.cd.industries.dto.excel;


/**
 * Created by Administrator on 2016/7/11 0011.
 */
public class EnterpriseDataUpdatesDto {
    private String id;
    @ExcelVOAttribute(name = "注册号", column = "A")
    private String registerNumber;
    @ExcelVOAttribute(name = "企业名称", column = "B")
    private String enterpriseName;
    //行业代码
    @ExcelVOAttribute(name = "行业代码", column = "C")
    private String hangyeEncode;
    @ExcelVOAttribute(name = "产业", column = "D")
    private String chanye;
    @ExcelVOAttribute(name = "行业", column = "E")
    private String hangye;
    @ExcelVOAttribute(name = "门类", column = "F")
    private String menlei;
    //主营业务
    @ExcelVOAttribute(name = "主营业务", column = "G")
    private String mainBusiness;
    @ExcelVOAttribute(name = "从业人数", column = "H")
    private String employeeNumber;
    @ExcelVOAttribute(name = "联系电话", column = "I")
    private String phone;
    //注册时间
    @ExcelVOAttribute(name = "注册时间", column = "J")
    private String registerDate;
    //注册资金
    @ExcelVOAttribute(name = "注册资金", column = "K")
    private String registerMoneny;
    //状态
    @ExcelVOAttribute(name = "状态", column = "L")
    private String status;
    @ExcelVOAttribute(name = "地址", column = "M")
    private String address;
    @ExcelVOAttribute(name = "所在街道", column = "N")
    private String street;
    @ExcelVOAttribute(name = "所在社区", column = "O")
    private String community;
    @ExcelVOAttribute(name = "本行错误列", column = "P")
    private String errorColumn;

    private String chanyeId;
    private String hangyeId;
    private String menleiId;
    private String streetId;
    private String communityId;
    private String logId;
    private String sourceId;

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getChanyeId() {
        return chanyeId;
    }

    public void setChanyeId(String chanyeId) {
        this.chanyeId = chanyeId;
    }

    public String getHangyeId() {
        return hangyeId;
    }

    public void setHangyeId(String hangyeId) {
        this.hangyeId = hangyeId;
    }

    public String getMenleiId() {
        return menleiId;
    }

    public void setMenleiId(String menleiId) {
        this.menleiId = menleiId;
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

    public String getId() {
        return id;
    }

    public String getErrorColumn() {
        return errorColumn;
    }

    public void setErrorColumn(String errorColumn) {
        this.errorColumn = errorColumn;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getHangyeEncode() {
        return hangyeEncode;
    }

    public void setHangyeEncode(String hangyeEncode) {
        this.hangyeEncode = hangyeEncode;
    }

    public String getChanye() {
        return chanye;
    }

    public void setChanye(String chanye) {
        this.chanye = chanye;
    }

    public String getHangye() {
        return hangye;
    }

    public void setHangye(String hangye) {
        this.hangye = hangye;
    }

    public String getMenlei() {
        return menlei;
    }

    public void setMenlei(String menlei) {
        this.menlei = menlei;
    }

    public String getMainBusiness() {
        return mainBusiness;
    }

    public void setMainBusiness(String mainBusiness) {
        this.mainBusiness = mainBusiness;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getRegisterMoneny() {
        return registerMoneny;
    }

    public void setRegisterMoneny(String registerMoneny) {
        this.registerMoneny = registerMoneny;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "EnterpriseDataUpdatesDto{" +
                "id='" + id + '\'' +
                ", registerNumber='" + registerNumber + '\'' +
                ", enterpriseName='" + enterpriseName + '\'' +
                ", hangyeEncode='" + hangyeEncode + '\'' +
                ", chanye='" + chanye + '\'' +
                ", hangye='" + hangye + '\'' +
                ", menlei='" + menlei + '\'' +
                ", mainBusiness='" + mainBusiness + '\'' +
                ", employeeNumber='" + employeeNumber + '\'' +
                ", phone='" + phone + '\'' +
                ", registerDate='" + registerDate + '\'' +
                ", registerMoneny='" + registerMoneny + '\'' +
                ", status='" + status + '\'' +
                ", address='" + address + '\'' +
                ", street='" + street + '\'' +
                ", community='" + community + '\'' +
                ", errorColumn='" + errorColumn + '\'' +
                ", chanyeId='" + chanyeId + '\'' +
                ", hangyeId='" + hangyeId + '\'' +
                ", menleiId='" + menleiId + '\'' +
                ", streetId='" + streetId + '\'' +
                ", communityId='" + communityId + '\'' +
                '}';
    }

    public EnterpriseDataUpdatesDto() {
    }

}
