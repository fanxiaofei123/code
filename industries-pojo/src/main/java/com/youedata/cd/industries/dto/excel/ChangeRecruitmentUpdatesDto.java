package com.youedata.cd.industries.dto.excel;



/**
 * Created by Administrator on 2016/7/11 0011.
 */
public class ChangeRecruitmentUpdatesDto {
    @ExcelVOAttribute(name = "时间", column = "A")
    private String date;
    @ExcelVOAttribute(name = "公司名称", column = "B")
    private String name;
    @ExcelVOAttribute(name = "地址", column = "C")
    private String address;
    @ExcelVOAttribute(name = "错误列", column = "D")
    private String errorColumn;

    private String enterpriseId;

    private Long logId;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    @Override
    public String toString() {
        return "ChangeRecruitmentUpdatesDto{" +
                "date='" + date + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", errorColumn='" + errorColumn + '\'' +
                '}';
    }

    public String getErrorColumn() {
        return errorColumn;
    }

    public void setErrorColumn(String errorColumn) {
        this.errorColumn = errorColumn;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
}
