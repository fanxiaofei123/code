package com.youedata.cd.index.pojo;

import com.google.common.collect.Lists;
import com.youedata.cd.base.pojo.BaseDomain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class IndexData extends BaseDomain implements Serializable {
    private static final long serialVersionUID = 33961101334420736L;

    /**
     * 指数编号
     */
    private Long indexId;

    /**
     * 产业id
     */
    private Long industryId;

    /**
     * 行业id
     */
    private Long tradeId;

    /**
     * 对应的企业
     */
    private Long enterpriseId;

    /**
     * 数据
     */
    private BigDecimal data;

    /**
     * 原始数据
     */
    private BigDecimal rawData;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 指数对象
     */
    private IndexDefinition indexDefinition;

    /**
     * 界面上保存参数用的
     */
    private String startYear;

    private String endYear;

    /**
     * 界面上保存参数用的
     */
    private String startMonth;

    private String endMonth;

    private List<IndexData> indexDataChildList = Lists.newArrayList();

    public Long getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Long industryId) {
        this.industryId = industryId;
    }

    public Long getTradeId() {
        return tradeId;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }

    /**
     * @return 指数编号
     */
    public Long getIndexId() {
        return indexId;
    }

    /**
     * @param indexId 指数编号
     */
    public void setIndexId(Long indexId) {
        this.indexId = indexId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    /**
     * @return 数据
     */
    public BigDecimal getData() {
        return data;
    }

    /**
     * @param data 数据
     */
    public void setData(BigDecimal data) {
        this.data = data;
    }

    public BigDecimal getRawData() {
        return rawData;
    }

    public void setRawData(BigDecimal rawData) {
        this.rawData = rawData;
    }

    public IndexDefinition getIndexDefinition() {
        return indexDefinition;
    }

    public void setIndexDefinition(IndexDefinition indexDefinition) {
        this.indexDefinition = indexDefinition;
    }

    public List<IndexData> getIndexDataChildList() {
        return indexDataChildList;
    }

    public void setIndexDataChildList(List<IndexData> indexDataChildList) {
        this.indexDataChildList = indexDataChildList;
    }

    public String getStartYear() {
        return startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public String getEndYear() {
        return endYear;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    public String getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }

    public String getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
    }
}