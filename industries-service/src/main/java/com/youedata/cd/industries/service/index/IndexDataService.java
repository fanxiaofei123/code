package com.youedata.cd.industries.service.index;

import com.youedata.cd.base.service.IBaseService;
import com.youedata.cd.index.pojo.IndexData;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/8.
 */
public interface IndexDataService extends IBaseService<IndexData> {
    /**
     * 通过指标对象传入的数据查询
     */
    List<IndexData> getByMap(IndexData indexData);

    /**
     * 通过时间段获取同一个指标中的最大
     */
    List<IndexData> getMaxByMap(IndexData indexData);

    /**
     * 通过时间段获取同一个指标中的最小值
     */
    List<IndexData> getMinByMap(IndexData indexData);

    /**
     * 通过开始时间排序（降序）
     */
    List<IndexData> getByStartTimeDesc(IndexData indexData);

    Map<String, Object> getIndexData(Map<String, Object> param);
}
