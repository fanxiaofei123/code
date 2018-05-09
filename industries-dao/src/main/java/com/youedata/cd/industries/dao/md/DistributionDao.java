package com.youedata.cd.industries.dao.md;

import java.util.List;
import java.util.Map;

import com.youedata.cd.industries.dao.base.ILocalBaseDao;
import com.youedata.cd.industries.pojo.md.Distribution;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

public interface DistributionDao extends ILocalBaseDao<Distribution> {

    List<Map<Object, Object>> queryAll();

    /**
     * 行业分布年份
     * @return
     */
    Map<String, Object> industry(@Param("fyear") Integer fyear,@Param("pyear") Integer pyear);

    List<Map<String, Object>> industryAll(@Param("fyear") Integer fyear,@Param("pyear") Integer pyear);

    List<Map<String, Object>> getPatensVitality(Integer authorize);

    /**
     * 申请
     * @param authorize
     * @return
     */
    List<Map<String, Object>> apply(Integer authorize);

    /**
     * 授权
     * @param authorize
     * @return
     */

    List<Map<String, Object>> authorise(Integer authorize);

    List<Map<String, Object>> getPatensTime(String patentType);
}