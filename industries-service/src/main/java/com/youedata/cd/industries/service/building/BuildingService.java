package com.youedata.cd.industries.service.building;

import com.youedata.cd.industries.pojo.building.Building;
import com.youedata.cd.industries.pojo.community.Community;
import com.youedata.cd.industries.pojo.enterprise.Enterprise;
import com.youedata.cd.industries.query.BaseQuery;
import com.youedata.cd.industries.query.building.BuildingQuery;
import com.youedata.cd.industries.service.base.LocalBaseService;

import java.util.List;
import java.util.Map;

public interface BuildingService extends LocalBaseService<Building> {
    Map<String,Object> selectByQuery(BuildingQuery query);
    List<Building> queryBuildingInfo(Long id);
    /*按照街道ID查询对应的社区*/
    List<Building> queryByStreetId(Long id);
    /*按照楼宇ID修改街道、社区*/
    public void updateByBuilding(String id,String streetId,String communityId,String[] enterpriseId);

    /*按照楼宇ID删除对应信息*/
    void deleteByPrimaryKey(Long id);

    /* 根据了楼宇ID查找下面对应的企业数量 */
    Long queryEnterpriseCount(Long id);


}