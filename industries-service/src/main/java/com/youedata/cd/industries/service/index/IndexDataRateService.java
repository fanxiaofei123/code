package com.youedata.cd.industries.service.index;

import java.util.Map;

/**
 * Created by Administrator on 2017/3/21.
 */
public interface IndexDataRateService {

    /**
     * 封装图形参数
     * @param param
     * @return
     */
    Map<String, Object> getIndexData(Map<String, Object> param);

    /**
     * 计算企业总分
     * @param industryId
     * @param tradeId
     * @param enterpriseId
     * @param startTime
     * @param endTime
     * @return
     */
    public Double getDataByEnterpriseId(String industryId, String tradeId, String enterpriseId, String startTime, String endTime);

    /**
     * 重新计算总分
     */
    void recalculateIndexData();
}
