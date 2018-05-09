package com.youedata.cd.industries.service.enterprise;

import com.youedata.cd.base.service.IBaseService;
import com.youedata.cd.industries.pojo.enterprise.EnterpriseBase;
import com.youedata.cd.industries.pojo.enterprise.EnterpriseLog;
import com.youedata.cd.industries.pojo.enterprise.EnterprisebaseResult;

import java.util.List;

public interface EnterpriseBaseService extends IBaseService<EnterpriseBase> {

    public void updateByPrimaryKeySelective(EnterpriseBase enterpriseBase,EnterpriseLog enterpriseLog);

    /*通过企业注册号查询企业*/
    public EnterpriseBase selectByRegisterNumber(String registerNumber);


    List<EnterprisebaseResult> selectNameAndId();


}