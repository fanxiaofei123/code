package com.youedata.cd.industries.dao.index;

import com.youedata.cd.base.dao.IBaseDao;
import com.youedata.cd.index.pojo.IndexDataStatisti;

import java.util.List;
import java.util.Map;

public interface IndexDataStatistiDao extends IBaseDao<IndexDataStatisti> {

    List<String> getDataByMap(Map param);

    void batchDeleteIndexDataStatisti(IndexDataStatisti indexDataStatisti);

    Double selectSumByMap(IndexDataStatisti indexDataStatisti);

    List<IndexDataStatisti> statisIndexDataByIndustryOrTradeId(Map param);
}
