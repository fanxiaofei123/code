package com.youedata.cd.industries.service.building.impl;

import com.youedata.cd.industries.dao.base.ILocalBaseDao;
import com.youedata.cd.industries.dao.building.BuildingDao;
import com.youedata.cd.industries.dao.enterprise.EnterpriseBaseMapper;
import com.youedata.cd.industries.dao.enterprise.EnterpriseDao;
import com.youedata.cd.industries.dao.enterprise.EnterpriseLogMapper;
import com.youedata.cd.industries.pojo.building.Building;
import com.youedata.cd.industries.pojo.enterprise.Enterprise;
import com.youedata.cd.industries.pojo.enterprise.EnterpriseBase;
import com.youedata.cd.industries.pojo.enterprise.EnterpriseLog;
import com.youedata.cd.industries.query.building.BuildingQuery;
import com.youedata.cd.industries.service.base.impl.LocalBaseServiceImpl;
import com.youedata.cd.industries.service.building.BuildingService;
import com.youedata.cd.industries.service.enterprise.EnterpriseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("buildingService")
public class BuildingServiceImpl extends LocalBaseServiceImpl<Building> implements BuildingService {
    private static final Logger logger = LoggerFactory.getLogger(BuildingServiceImpl.class);

    @Autowired
    private BuildingDao buildingDao;
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private EnterpriseBaseMapper enterpriseBase;
    @Autowired
    private EnterpriseLogMapper enterpriseLog;

    @Override
    public ILocalBaseDao<Building> getBaseDao() {
        return buildingDao;
    }


    @Override
    public Map<String, Object> selectByQuery(BuildingQuery query) {
        System.err.println("query" +query);
        ILocalBaseDao baseDao = getReadBaseDao();
        List<Building> buildings =  baseDao.selectByQuery(query);
        Map<String, Object> pageList = new HashMap<>();
        Long counts = buildingDao.selectAllCounts(query);
        String enterpriseName = query.getBuildingName();
        query.setBuildingName(null);
        query.setEnterpriseName(enterpriseName);
        Integer enterpriseCounts = enterpriseService.selectByCount(query);
        pageList.put("buildings", buildings);
        pageList.put("enterpriseCounts", enterpriseCounts);
        pageList.put("counts", counts);
        pageList.put("currentPage",query.getCurrentPage());
        return pageList;
    }

    @Override
    public List<Building> queryBuildingInfo(Long id) {
        List<Building> list = new ArrayList<>();
        Map map = new HashMap();
        map.put("id",id);
        list = buildingDao.queryBuildingInfo(map);
        return list;
    }

    @Override
    public List<Building> queryByStreetId(Long id) {
        List<Building> list = new ArrayList<>();
        Map map = new HashMap();
        map.put("id",id);
        list = buildingDao.queryByStreetId(map);
        return list;
    }

    @Override
    public void updateByBuilding(String id,String streetId,String communityId,String[] enterpriseId) {
        Map map = new HashMap();
        map.put("id",id);
        map.put("streetId",streetId);
        map.put("communityId",communityId);
       // map.put("enterpriseId", enterpriseId);
//        buildingDao.delBuildingInfo(map);

//        if(enterpriseId!=null && enterpriseId.length>0){
//        	buildingDao.delEnterpirseInfo(enterpriseId);
//        }
        buildingDao.updateByBuilding(map);
        /**新增新的企业与楼宇的关系
         */
       //Building building= buildingDao.selectByPrimaryKey(id);

       List list=new ArrayList();
       if(enterpriseId!=null && enterpriseId.length>0){
    	   for(int i=0;i<enterpriseId.length;i++){
    		   if(enterpriseId[i]==null || "".equalsIgnoreCase(enterpriseId[i])){
    			   continue;
    		   }
    		   Map mapInfo=new HashMap();
    		   mapInfo.put("enterpriseId", enterpriseId[i]);
    		   mapInfo.put("buildingId", id);
    		   mapInfo.put("streetId", streetId);
    		   mapInfo.put("communityId", communityId);
               enterpriseLog.updateByBuilding(mapInfo);

//    		   List<EnterpriseLog> enterpriseLogList= enterpriseLog.queryEnterpriseLog(mapInfo);
    		   //确定是否存在相同的企业，当不存在时才能添加关联企业
//    		   if(enterpriseLogList==null || enterpriseLogList.size()<1){
//    			   EnterpriseBase enterprise= enterpriseBase.selectById(enterpriseId[i]);
//        		   if(enterprise!=null){
//        			   String uuitStr = UUID.randomUUID().toString().replaceAll("-", "");
//        			   mapInfo.put("id", uuitStr);
//        			   mapInfo.put("major_business", enterprise.getRegisterMajorBusiness()==null?"":enterprise.getRegisterMajorBusiness());
//        			   mapInfo.put("address", enterprise.getRegisterAddress()==null?"":enterprise.getRegisterAddress());
//        			   mapInfo.put("version_id", 1);
//        			   mapInfo.put("address_modify_flag", 0);
//        			   mapInfo.put("major_business_modify_flag", 0);
//        			   list.add(mapInfo);
//        			   enterpriseLog.batchInsert(mapInfo);
//
//        		   }
//    		   }
    	   }
       }
    }

    @Override
    public void deleteByPrimaryKey(Long id) {
        buildingDao.deleteByPrimaryKey(id);
    }

    @Override
    public Long queryEnterpriseCount(Long id) {
        Long enterpriseCount = buildingDao.queryEnterpriseCount(id);
        return enterpriseCount;
    }
}