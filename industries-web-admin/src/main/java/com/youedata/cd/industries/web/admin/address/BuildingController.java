package com.youedata.cd.industries.web.admin.address;

import com.youedata.cd.base.service.IBaseService;
import com.youedata.cd.base.web.controller.BaseAjaxController;
import com.youedata.cd.base.web.controller.BaseController;
import com.youedata.cd.common.http.ResponseResult;
import com.youedata.cd.industries.dao.enterprise.EnterpriseLogMapper;
import com.youedata.cd.industries.pojo.building.Building;
import com.youedata.cd.industries.pojo.enterprise.EnterpriseBase;
import com.youedata.cd.industries.pojo.enterprise.EnterpriseLog;
import com.youedata.cd.industries.service.building.BuildingService;
import com.youedata.cd.industries.service.enterprise.EnterpriseBaseService;
import com.youedata.cd.industries.service.enterprise.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by honshe、T on 2016/7/11.
 */
@Controller
@RequestMapping("building")
public class BuildingController  extends BaseController<Building> {

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private EnterpriseBaseService enterpriseBaseService;

    @Autowired
    private EnterpriseLogMapper enterpriseLogMapper;

    @Autowired
    private EnterpriseService enterpriseService;




    /*根据楼宇ID查询对应的街道、社区以及包含的企业*/
    @ResponseBody
    @RequestMapping(value = "queryBuildingInfo",method = RequestMethod.POST)
    public List queryBuildingInfo(Model model,Long id){
        List<Building> list = new ArrayList<Building>();
        list = buildingService.queryBuildingInfo(id);
        return list;
    }

    /*根据街道ID查询所包含的社区*/
    @ResponseBody
    @RequestMapping(value = "queryByStreetId",method = RequestMethod.POST)
    public List queryByStreetId(Model model,Long id){
        List<Building> ids = new ArrayList<Building>();
        ids = buildingService.queryByStreetId(id);
        return ids;
    }

    /*根据楼宇ID删除对应信息*/
    @ResponseBody
    @RequestMapping(value = "deleteByPrimaryKey",method = RequestMethod.GET)
    public ResponseResult deleteByPrimaryKey(Long id){
        buildingService.deleteByPrimaryKey(id);
        return new ResponseResult();
    }

    /*根据楼宇ID更新下面的街道、社区、以及企业（可以是多家企业）*/
    @ResponseBody
    @RequestMapping(value = "updateByBuildingId",method = RequestMethod.POST)
    public Map updateByBuildingId(String id,String streetId,String communityId,String[] enterpriseId){
        Map<String, String> backInfo=new HashMap<String, String>();
        Map<String, Integer> map = new HashMap<String, Integer>();
        if (enterpriseId != null){
           for (String enId : enterpriseId){
               Integer num = map.get(enId);
               num = null == num ? 1 : num + 1;
               map.put(enId, num);
           }
            if (enterpriseId.length != map.size())
            {
                backInfo.put("code", "501");
                backInfo.put("msg", "更新失败，存在相同企业");
                return backInfo;
            }
            for (int i=0;i<enterpriseId.length;i++){
                if ("".equals(enterpriseId[i])){
                    backInfo.put("code", "500");
                    backInfo.put("msg", "更新失败,请确认你的企业注册号");
                    return backInfo;
                }
            }
        }
            buildingService.updateByBuilding(id,streetId,communityId,enterpriseId);
	        backInfo.put("code", "200");
	        backInfo.put("msg", "更新成功");
    	return backInfo;
    }


    /*根据企业注册号查询企业*/
    @ResponseBody
    @RequestMapping(value = "selectByPrimaryKey",method = RequestMethod.GET)
    public ResponseResult selectByPrimaryKey(String registerNumber){
        EnterpriseBase enterpriseBase = new EnterpriseBase();
        enterpriseBase = enterpriseBaseService.selectByRegisterNumber(registerNumber);
        ResponseResult responseResult = new ResponseResult();
        if (enterpriseBase==null){
            responseResult.setMsg("未找到相关企业");
        }
        responseResult.setData(enterpriseBase);
        return responseResult;
    }

    /*根据楼宇和企业ID对应删除企业*/
    @ResponseBody
    @RequestMapping(value = "delByBuildingIdAndEnterpriseId",method = RequestMethod.POST)
    public ResponseResult delByBuildingIdAndEnterpriseId(Long enterpriseId,Long buildingId){
        enterpriseService.delByBuildingIdAndEnterpriseId(enterpriseId,buildingId);
        return new ResponseResult();
    }

    /*在对应楼宇、街道、社区下添加企业*/
    @RequestMapping(value = "addEnterprise",method = RequestMethod.GET)
    public void insertSelective(Long buildingId,Long streetId[],Long communityId,String enterpriseId){
        EnterpriseLog enterpriseLog = new EnterpriseLog();
        enterpriseLog.setId(enterpriseId);
        enterpriseLog.setBuildingId(buildingId);
        for (int i=0;i<streetId.length-1;i++){
            enterpriseLog.setStreetId(streetId[i]);
        }
        enterpriseLog.setCommunityId(communityId);
        enterpriseLogMapper.insertSelective(enterpriseLog);
    }

    @ResponseBody
    @RequestMapping(value = "queryEnterpriseCount",method = RequestMethod.GET)
    public ResponseResult queryEnterpriseCount(Long id){
        ResponseResult responseResult = new ResponseResult();
        Long enterpriseCount = buildingService.queryEnterpriseCount(id);
        responseResult.setData(enterpriseCount);
        return responseResult;
    }





//    @Override
//    public IBaseService<Building> getBaseService() {
//        return buildingService;
//    }
//
//    @Override
//    public String getModelPre() {
//
//    }

    @Override
    public IBaseService<Building> get_base_service() {
        return buildingService;
    }

    @Override
    public String get_model_pre() {
        return "/building/";
    }
}





