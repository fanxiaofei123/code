package com.youedata.cd.industries.dao.building;

import com.youedata.cd.industries.dao.base.ILocalBaseDao;
import com.youedata.cd.industries.pojo.building.Building;
import com.youedata.cd.industries.dto.excel.BuildingDataUpdatesDto;
import com.youedata.cd.industries.dto.excel.EnterpriseDataUpdatesDto;
import com.youedata.cd.industries.query.building.BuildingQuery;

import java.util.List;
import java.util.Map;

public interface BuildingDao extends ILocalBaseDao<Building> {
    /*按照楼宇id查找对应信息*/
    List<Building> queryBuildingInfo(Map map);

    /*按照街道ID查询对应的社区*/
    List<Building> queryByStreetId(Map map);

    /*按照楼宇ID修改街道、社区*/
    void updateByBuilding(Map map);

    /**
     * 删除楼宇下面所有的关系数据
     */
    void delBuildingInfo(Map map);

    void delEnterpirseInfo(String[] array);

    void addBuildingInfo(List list);

    /*根据地址更新一条新的信息*/
    void updateByAddress(BuildingDataUpdatesDto buildingDataUpdatesDto);

    /*添加一个新的楼宇信息*/
    void insertById(BuildingDataUpdatesDto buildingDataUpdatesDto);

    /*根据地址判断楼宇是否存在*/
    BuildingDataUpdatesDto findByaddress(String address);

    /*根据注册号查找企业*/
    EnterpriseDataUpdatesDto findByRegisterNumber(String registerNumber);

    /*添加验证未通过的数据到t_failed_building表*/
    void insertFailBuilding(BuildingDataUpdatesDto b);

    /* 根据了楼宇ID查找下面对应的企业数量 */
    Long queryEnterpriseCount(Long id);
    Long selectAllCounts(BuildingQuery query);
}