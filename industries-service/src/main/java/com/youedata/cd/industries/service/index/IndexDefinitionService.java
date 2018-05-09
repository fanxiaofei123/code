package com.youedata.cd.industries.service.index;

import com.youedata.cd.base.service.IBaseService;
import com.youedata.cd.index.pojo.IndexDefinition;

import java.util.List;

/**
 * Created by Administrator on 2017/3/8.
 */
public interface IndexDefinitionService extends IBaseService<IndexDefinition> {
    /**
     * 根据指标定义对象查询指标定义
     *
     * @param indexDefinition
     */
    List<IndexDefinition> selectByMap(IndexDefinition indexDefinition);

    List<IndexDefinition> getIndexDefinitionByIndustry(long industryId);
}
