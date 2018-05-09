package com.youedata.cd.industries.dao.enterprise;

import com.youedata.cd.industries.dto.excel.EnterpriseDataUpdatesDto;
import com.youedata.cd.industries.dao.base.ILocalBaseDao;
import com.youedata.cd.industries.pojo.enterprise.Enterprise;
import com.youedata.cd.industries.pojo.enterprise.EnterpriseLog;
import com.youedata.cd.industries.pojo.enterprise.EnterpriseResult;
import com.youedata.cd.industries.pojo.log.UploadLog;
import com.youedata.cd.industries.query.enterprise.EnterpriseQuery;

import java.util.List;
import java.util.Map;

public interface EnterpriseDao extends ILocalBaseDao<Enterprise> {
    List<EnterpriseResult> getChangeEnterprise(EnterpriseQuery query);

    Integer getChangeEnterpriseCounts(EnterpriseQuery query);

    List<EnterpriseResult> selectChangesEnterpriseById(EnterpriseQuery query);

    Enterprise selectEnterpriseById(EnterpriseQuery query);

    /*根据企业注册号、企业名称、企业地址模糊查询企业列表信息*/
    List<Enterprise> queryByItem(Map map);

    /*根据企业ID删除对应信息*/
    void deleteByEnterpriseId(Long enterpriseId);

    /*插入企业集合*/
    Long insetEnterpriseData(EnterpriseDataUpdatesDto dto);

    /*根据注册号查找id*/
    String selectByRegisterNumber(String registerNumber);

    /*添加一个enterpriselog对象*/
    void insetEnterpriseLog(EnterpriseLog enterpriseLog);

    /*更新已有的base数据*/
    void updatesEnterpriseBaseById(EnterpriseDataUpdatesDto e);

    /*更新已有的log数据*/
    void updatesEnterpriseLogById(EnterpriseLog e);

    /**
     * 存入日志
     */
    void insertUploadLog(UploadLog u);

    /*存入校验失败数据*/
    void insertFailList(List<EnterpriseDataUpdatesDto> list);

    /*根据enterpriseId查找*/
    EnterpriseLog findByEnterpriseId(String enterpriseId);

    /*修改sourceId*/
    void updateSourceId(Map map);

    //lzs
    List<Enterprise> selectByQuery1(Map<String,Object> map);
    //lzs
    Integer selectByCount1(Map<String,Object> map);

}