package com.youedata.cd.industries.service.index.impl;

import com.youedata.cd.base.common.constant.KeyWords;
import com.youedata.cd.base.dao.IBaseDao;
import com.youedata.cd.base.service.impl.BaseServiceImpl;
import com.youedata.cd.index.pojo.IndexData;
import com.youedata.cd.industries.dao.index.IndexDataDao;
import com.youedata.cd.industries.msg.constant.IndexKeyWords;
import com.youedata.cd.industries.service.index.IndexDataService;
import com.youedata.cd.industries.service.index.chart.ChartServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/8.
 */
@Service("indexDataService")
public class IndexDataServiceImpl extends BaseServiceImpl<IndexData> implements IndexDataService {

    private static final Logger logger = LoggerFactory.getLogger(IndexDataServiceImpl.class);

    @Autowired
    private IndexDataDao indexDataDao;

    List<ChartServiceImpl> chartServiceList;

    @Override
    public IBaseDao<IndexData> getBaseDao() {
        return indexDataDao;
    }

    @Override
    public List<IndexData> getByMap(IndexData indexData) {
        return indexDataDao.getByMap(indexData);
    }

    @Override
    public List<IndexData> getMaxByMap(IndexData indexData) {
        return indexDataDao.getMaxByMap(indexData);
    }

    @Override
    public List<IndexData> getMinByMap(IndexData indexData) {
        return indexDataDao.getMinByMap(indexData);
    }

    @Override
    public List<IndexData> getByStartTimeDesc(IndexData indexData) {
        return indexDataDao.getByStartTimeDesc(indexData);
    }

    @Override
    public Map<String, Object> getIndexData(Map<String, Object> param) {

        for (ChartServiceImpl chartService : chartServiceList) {
            if (chartService.match(param)) {
                try {
                    return chartService.assembleData(param);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Map<String, Object> indexMap = new HashMap<String, Object>();
                    indexMap.put(KeyWords.STATUS, KeyWords.STATUS_FAIL);
                    indexMap.put(KeyWords.MESSAGE, IndexKeyWords.SYS_EXCEPTION +": "+ ex.getMessage());
                    return indexMap;
                }
            }
        }
        return null;
    }

    public void setChartServiceList(List<ChartServiceImpl> chartServiceList) {
        this.chartServiceList = chartServiceList;
    }
}
