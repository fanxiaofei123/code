package com.youedata.cd.industries.service.index.impl;

import com.youedata.cd.base.dao.IBaseDao;
import com.youedata.cd.base.service.impl.BaseServiceImpl;
import com.youedata.cd.common.util.YoueStringUtils;
import com.youedata.cd.index.pojo.IndexDefinition;
import com.youedata.cd.index.pojo.IndexRate;
import com.youedata.cd.industries.dao.index.IndexDefinitionDao;
import com.youedata.cd.industries.service.index.IndexDefinitionService;
import com.youedata.cd.industries.service.index.IndexRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("indexDefinitionService")
public class IndexDefinitionServiceImpl extends BaseServiceImpl<IndexDefinition> implements IndexDefinitionService {
    private static final Logger logger = LoggerFactory.getLogger(IndexDefinitionServiceImpl.class);

    @Autowired
    private IndexDefinitionDao indexDefinitionDao;

    @Autowired
    private IndexRateService indexRateService;

    @Override
    public IBaseDao<IndexDefinition> getBaseDao() {
        return indexDefinitionDao;
    }

    public List<IndexDefinition> getIndexDefinitionByIndustry(long industryId) {
        List<IndexDefinition> indexDefinitions = new ArrayList<IndexDefinition>();
        for (IndexDefinition indexDefinition : indexDefinitionDao.selectAll()) {
            IndexRate indexRate = new IndexRate();
            indexRate.setIndexId(indexDefinition.getId());
            indexRate.setIsDeleted(false);
            indexRate.setLimit(1);
            indexRate.setIndustryId(industryId);
            List<IndexRate> indexRates = indexRateService.select(indexRate);
            if (indexRates != null && indexRates.size() > 0) {
                IndexRate indexRate_ = indexRates.get(0);
                indexRate_.setRate(YoueStringUtils.formatBigDecimal(indexRate_.getRate()));
                indexDefinition.setIndexRate(indexRate_);
            }

            indexDefinitions.add(indexDefinition);
        }
        return indexDefinitions;
    }

    @Override
    public List<IndexDefinition> selectByMap(IndexDefinition indexDefinition) {
        return indexDefinitionDao.selectByMap(indexDefinition);
    }
}