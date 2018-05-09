package com.youedata.cd.industries.dao.index;

import com.youedata.cd.base.dao.IBaseDao;
import com.youedata.cd.index.pojo.IndexData;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/8.
 */
public interface IndexDataDao extends IBaseDao<IndexData> {
    List<IndexData> getByMap(IndexData indexData);
    List<IndexData> getMaxByMap(IndexData indexData);
    List<IndexData> getMinByMap(IndexData indexData);
    List<IndexData> getByStartTimeDesc(IndexData indexData);
    List<String> getDataEnterpriseByMap(Map param);
    List<IndexData> getEnterpriseDataList(IndexData indexData);
}
