package com.youedata.cd.industries.service.index.chart;

import com.youedata.cd.industries.service.index.IndexDataRateService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created by Administrator on 2017/3/21.
 */
public abstract class ChartServiceImpl {

    @Autowired
    private IndexDataRateService indexDataRateService;

    /**
     * 封装前端echart参数
     * @param param
     * @return
     */
    public abstract Map<String, Object> assembleData(Map<String, Object> param);

    /**
     * 匹配条件
     * @param param
     * @return
     */
    public abstract boolean match(Map<String, Object> param);

    public Map<String, Object> getIndexData(Map<String, Object> param) {
        Map<String, Object> map = indexDataRateService.getIndexData(param);
        return map;
    }
}
