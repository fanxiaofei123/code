package com.youedata.cd.index.pojo;

import com.youedata.cd.base.pojo.BaseDomain;
import java.io.Serializable;
import java.math.BigDecimal;

public class IndexDataStatisti extends BaseDomain implements Serializable {
    private static final long serialVersionUID = 793685814607462490L;

    /**
     * 企业id
     */
    private Long enterpriseId;

    /**
     * 指数编号
     */
    private Long indexId;

    /**
     * 所属产业（一产，二产，三产）
     */
    private Long industryId;

    /**
     * 所属行业
     */
    private Long tradeId;

    /**
     * 数据
     */
    private BigDecimal data;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * @return 企业id
     */
    public Long getEnterpriseId() {
        return enterpriseId;
    }

    /**
     * @param enterpriseId
     *            企业id
     */
    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    /**
     * @return 指数编号
     */
    public Long getIndexId() {
        return indexId;
    }

    /**
     * @param indexId
     *            指数编号
     */
    public void setIndexId(Long indexId) {
        this.indexId = indexId;
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
     * @return 数据
     */
    public BigDecimal getData() {
        return data;
    }

    /**
     * @param data
     *            数据
     */
    public void setData(BigDecimal data) {
        this.data = data;
    }

    /**
     * @return 开始时间
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @param startTime
     *            开始时间
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * @return 结束时间
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * @param endTime
     *            结束时间
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}