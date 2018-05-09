package com.youedata.cd.industries.dao.enterprise;


import com.youedata.cd.base.dao.IBaseDao;
import com.youedata.cd.industries.pojo.enterprise.EnterpriseBase;
import com.youedata.cd.industries.pojo.enterprise.EnterpriseResult;
import com.youedata.cd.industries.pojo.enterprise.EnterprisebaseResult;

import java.util.List;

public interface EnterpriseBaseMapper extends IBaseDao<EnterpriseBase> {
	
	public EnterpriseBase selectById(String id);

	/*通过企业注册号查询企业*/
	public EnterpriseBase selectByRegisterNumber(String registerNumber);

	//    只查询出公司名称和id
	List<EnterprisebaseResult> selectNameAndId();
}