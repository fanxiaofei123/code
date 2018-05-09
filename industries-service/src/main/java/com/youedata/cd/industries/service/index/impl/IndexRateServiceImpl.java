package com.youedata.cd.industries.service.index.impl;

import com.youedata.cd.base.dao.IBaseDao;
import com.youedata.cd.base.service.impl.BaseServiceImpl;
import com.youedata.cd.common.util.YoueStringUtils;
import com.youedata.cd.index.pojo.IndexRate;
import com.youedata.cd.industries.dao.index.IndexRateDao;
import com.youedata.cd.industries.service.index.IndexDataRateService;
import com.youedata.cd.industries.service.index.IndexRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("indexRateService")
public class IndexRateServiceImpl extends BaseServiceImpl<IndexRate> implements IndexRateService {
    private static final Logger logger = LoggerFactory.getLogger(IndexRateServiceImpl.class);

    @Autowired
    private IndexRateDao indexRateDao;

    @Autowired
    private IndexDataRateService indexDataRateService;

    @Override
    public IBaseDao<IndexRate> getBaseDao() {
        return indexRateDao;
    }

    @Override
    public long save(IndexRate indexRate) {

        List<IndexRate> indexRateList = new ArrayList<IndexRate>();
        boolean flag = false;
        for (IndexRate indexRa : indexRate.getIndexRateChildList()) {
            IndexRate indexRa_ = this.get(indexRa.getId());
            if (indexRa_ == null) {
                indexRa.setIsDeleted(false);
                indexRa.setCreatedTime(new Date());
                indexRa.setIndustryId(indexRate.getIndustryId());
                indexRateList.add(indexRa);
                flag = true;
            } else if (!YoueStringUtils.formatBigDecimal(indexRa_.getRate()).equals(YoueStringUtils.formatBigDecimal(indexRa.getRate()))) {
                indexRa.setUpdateTime(new Date());
                update(indexRa);
                flag = true;
            }
        }

        if (indexRateList.size() > 0) {
            indexRateDao.batchInsert(indexRateList);
        }
        //权重修改或新增，需要重新计算
        if (flag) {
            Runnable runnable = new Runnable(){
                public void run(){
                    indexDataRateService.recalculateIndexData();
                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
        }
        return 0L;
    }

}