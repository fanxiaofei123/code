package com.youedata.cd.industries.service.enterprise;

import com.youedata.cd.base.service.IBaseService;
import com.youedata.cd.industries.pojo.enterprise.Enterprise;
import com.youedata.cd.industries.pojo.enterprise.EnterpriseResult;
import com.youedata.cd.industries.query.BaseQuery;
import com.youedata.cd.industries.query.enterprise.EnterpriseQuery;
import com.youedata.cd.industries.service.base.LocalBaseService;

import java.util.List;
import java.util.Map;

public interface EnterpriseService extends LocalBaseService<Enterprise>{
    Map<String, Object> selectByQuery(EnterpriseQuery query);
    Map<String,Object> getChangeEnterprise(EnterpriseQuery query);
    Integer getChangeEnterpriseCounts(EnterpriseQuery query);
    Map<String,Object> selectChangesEnterpriseById(EnterpriseQuery query);
    /*根据企业注册号、企业名称、企业地址模糊查询企业列表信息*/
    List<Enterprise> queryByItem(String enterpriseRegistNumber,String enterpriseName,String enterpriseAddress,String dataInformation);
    void deleteByEnterpriseId(Long id);
    /*根据楼宇ID和企业ID对应删除楼宇内企业信息*/
     void delByBuildingIdAndEnterpriseId(Long enterpriseId,Long buildingId);
    void modifyInfo(Long id, String address);

    void cancelModifyInfo(Long id);

    void addressCancelModifyInfo(Long id);
    Boolean isExistCommonLog(Long id,String address);

}