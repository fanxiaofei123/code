package com.youedata.cd.industries.service.enterprise.impl;

import com.youedata.cd.industries.dao.enterprise.EnterpriseLogMapper;
import com.youedata.cd.industries.pojo.enterprise.EnterpriseLog;
import com.youedata.cd.industries.pojo.enterprise.EnterprisebaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youedata.cd.base.dao.IBaseDao;
import com.youedata.cd.base.service.impl.BaseServiceImpl;
import com.youedata.cd.industries.dao.enterprise.EnterpriseBaseMapper;
import com.youedata.cd.industries.pojo.enterprise.EnterpriseBase;
import com.youedata.cd.industries.service.enterprise.EnterpriseBaseService;

import java.util.List;

@Service("EnterpriseBaseService")
public class EnterpriseBaseServiceImpl extends BaseServiceImpl<EnterpriseBase> implements EnterpriseBaseService {
    private static final Logger logger = LoggerFactory.getLogger(EnterpriseBaseServiceImpl.class);

    @Autowired
    private EnterpriseBaseMapper enterpriseBaseDao;

    @Autowired
    private EnterpriseLogMapper enterpriseLogMapper;

    @Override
    public IBaseDao<EnterpriseBase> getBaseDao() {
        return enterpriseBaseDao;
    }

    @Override
    public void updateByPrimaryKeySelective(EnterpriseBase enterpriseBase,EnterpriseLog enterpriseLog) {

        enterpriseLogMapper.updateByPrimaryKeySelective(enterpriseLog);
        enterpriseBaseDao.updateByPrimaryKeySelective(enterpriseBase);
    }

    @Override
    public EnterpriseBase selectByRegisterNumber(String registerNumber) {
        EnterpriseBase enterpriseBase = new EnterpriseBase();
        enterpriseBase = enterpriseBaseDao.selectByRegisterNumber(registerNumber);
        return enterpriseBase;
    }

    @Override
    public List<EnterprisebaseResult> selectNameAndId() {
        return enterpriseBaseDao.selectNameAndId();
    }

}