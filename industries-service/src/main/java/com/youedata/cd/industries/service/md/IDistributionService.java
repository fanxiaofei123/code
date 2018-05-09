package com.youedata.cd.industries.service.md;

import java.util.List;
import java.util.Map;

import com.youedata.cd.base.service.IBaseService;
import com.youedata.cd.common.http.ResponseResult;
import com.youedata.cd.industries.pojo.md.Distribution;

/**
 * Created by Administrator on 2017/10/16.
 */

public interface IDistributionService  extends IBaseService<Distribution> {
    List<Map<Object, Object>> getList();

    ResponseResult<Map<String,Object>> getIndustryList(Integer fyear,Integer pyear);

    ResponseResult<List<Map<String,Object>>> getStatisticsList();


    ResponseResult<List<Map<String,Object>>> getPatensVitality(Integer authorize);

    ResponseResult<List<Map<String,Object>>> getPatensTime(String patentType);
}
