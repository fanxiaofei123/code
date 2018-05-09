package com.youedata.cd.industries.dao.index;

import com.youedata.cd.base.dao.IBaseDao;
import com.youedata.cd.index.pojo.IndexDefinition;

import java.util.List;

public interface IndexDefinitionDao extends IBaseDao<IndexDefinition> {
    List<IndexDefinition> selectByMap(IndexDefinition indexDefinition);
}