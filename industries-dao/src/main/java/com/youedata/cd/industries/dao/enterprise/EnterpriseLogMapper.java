package com.youedata.cd.industries.dao.enterprise;


import com.youedata.cd.industries.dto.excel.ChangeRecruitmentUpdatesDto;
import com.youedata.cd.industries.pojo.enterprise.EnterpriseLog;
import com.youedata.cd.industries.query.enterprise.EnterpriseQuery;

import java.util.List;
import java.util.Map;

public interface EnterpriseLogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_enterprise_log
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_enterprise_log
     *
     * @mbggenerated
     */
    int insert(EnterpriseLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_enterprise_log
     *
     * @mbggenerated
     */
    int insertSelective(EnterpriseLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_enterprise_log
     *
     * @mbggenerated
     */
    EnterpriseLog selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_enterprise_log
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(EnterpriseLog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_enterprise_log
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(EnterpriseLog record);

    List<EnterpriseLog> selectChangesEnterpriseById(EnterpriseQuery query);

    /*根据楼宇ID和企业ID对应删除楼宇内企业信息*/
    void delByBuildingIdAndEnterpriseId(Map map);
    
    void batchInsert(Map map);
    
    List<EnterpriseLog> queryEnterpriseLog(Map map);
    void updateBuildingId(EnterpriseLog enterpriseLog);

    void insertFailChangeData(List<ChangeRecruitmentUpdatesDto> list);

    void updateByBuilding(Map map);

    String selectEnterpriseLogId(String registerNumber);

    void updateBaseSourceId(EnterpriseLog enterpriseLog);
    String getLogId(Map parm);

    void updateiSModifyLog(String s);

    Long findEnIdByEnName(String name);

    void updateByEnterPriseId(EnterpriseLog enterpriseLog);
}