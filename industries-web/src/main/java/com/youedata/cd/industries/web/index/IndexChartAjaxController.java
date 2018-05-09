package com.youedata.cd.industries.web.index;

import com.youedata.cd.industries.service.index.IndexDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("aIndexChart")
public class IndexChartAjaxController {

    @Autowired
    private IndexDataService indexDataService;

    @RequestMapping(value = "getIndexData" ,method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getChartData(String chartType, String industryId, String tradeId, String enterpriseId, String startTime, String endTime){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("chartType", chartType); // 图标类型“bar",必传
        param.put("industryId", industryId); // 产业id,必传
        param.put("tradeId", tradeId); // 行业id
        param.put("enterpriseId", enterpriseId); // 企业id
        param.put("startTime", startTime); // 开始时间“2016-01-01”
        param.put("endTime", endTime); // 结束时间“2017-06-30”
        return indexDataService.getIndexData(param);
    }
}
