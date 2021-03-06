package com.youedata.cd.industries.service.index.chart;

/**
 * Created by Administrator on 2017/3/17.
 */

import com.youedata.cd.industries.msg.constant.IndexKeyWords;
import com.youedata.cd.industries.service.index.impl.IndexDataServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 柱状图、折线图数据封装
 * @author Administrator
 *
 */
@Service("barService")
public class BarChartServiceImpl extends ChartServiceImpl{

    @Override
    public Map<String, Object> assembleData(Map<String, Object> param) {
        Map<String, Object> indexMap = getIndexData(param);
        return indexMap;
    }

    @Override
    public boolean match(Map<String, Object> param) {
        String chartType = param.get("chartType") == null ? "" : (String) param
                .get("chartType");
        if (IndexKeyWords.BAR.equals(chartType)) {
            return true;
        }
        return false;
    }
}
