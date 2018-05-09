package com.youedata.cd.index.pojo;

import com.google.common.collect.Lists;
import com.youedata.cd.base.pojo.BaseDomain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class IndexRate extends BaseDomain implements Serializable {
    private static final long serialVersionUID = 946891460636783818L;

    /**
     * 权重
     */
    private BigDecimal rate;

    /**
     * 指标定义id
     */
    private Long indexId;

    /**
     * 产业id
     */
    private Long industryId;

    /**
     * 指标权重list
     */
    List<IndexRate> indexRateChildList = Lists.newArrayList();
	/**
     * @return 权重
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * @param rate 
	 *            权重
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    /**
     * @return 指标定义id
     */
    public Long getIndexId() {
        return indexId;
    }

    /**
     * @param indexId 
	 *            指标定义id
     */
    public void setIndexId(Long indexId) {
        this.indexId = indexId;
    }

    public Long getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Long industryId) {
        this.industryId = industryId;
    }

    public List<IndexRate> getIndexRateChildList() {
        return indexRateChildList;
    }

    public void setIndexRateChildList(List<IndexRate> indexRateChildList) {
        this.indexRateChildList = indexRateChildList;
    }

}