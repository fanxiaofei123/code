package com.youedata.cd.industries.web.building;

import com.youedata.cd.base.service.IBaseService;
import com.youedata.cd.base.web.controller.BaseAjaxController;
import com.youedata.cd.common.http.ResponseResult;
import com.youedata.cd.industries.pojo.building.Building;
import com.youedata.cd.industries.query.BaseQuery;
import com.youedata.cd.industries.query.building.BuildingQuery;
import com.youedata.cd.industries.service.building.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by cdyoue on 2016/6/1.
 */
@Controller
@RequestMapping("building")
public class BuildingAjaxController {
    @Autowired
    private BuildingService buildingService;

    /**
     * 任务代号：D001
     * @param query
     * @return
     */
    @RequestMapping(value = "indexSearch",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult indexSearch(BuildingQuery query){
        query.setBuildingName(query.getBuildingName().trim());
        Map<String, Object> pageList = buildingService.selectByQuery(query);
        ResponseResult responseResult = new ResponseResult();
        //设置查询结果类型 1表示企业 2表示楼宇
        responseResult.setMsg("2");
        responseResult.setData(pageList);
        return responseResult;
    }


    /**
     * 查询楼宇数
     * @param query
     * @return
     */
    @RequestMapping(value = "selectBuildingCounts",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult selectBuildingCounts(BaseQuery query){
        //query.setEnterpriseName("北京京丰制药有限公司");
        Integer buildingCounts = buildingService.selectByCount(query);
        ResponseResult responseResult = new ResponseResult();
        //设置查询结果类型 1表示企业 2表示楼宇
        responseResult.setMsg("楼宇数量");
        responseResult.setData(buildingCounts);
        return responseResult;
    }

    /**
     * 任务代号：
     * 通过楼宇主键id 查询企业D005
     * @param query
     * @return
     */
    @RequestMapping(value = "searchById",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult searchByPrimaryKey(BuildingQuery query){
        //query.setIndustryId("1  ");
        ResponseResult responseResult = new ResponseResult();
        if(query !=null){
            Map pageList = buildingService.searchByPrimaryKey(query);
            responseResult.setMsg("企业楼宇查询成功");
            responseResult.setData(pageList);
        }else{
            responseResult.setMsg("企业楼宇查询失败");
        }
        return responseResult;
    }

    /**
     * 任务代码:D009
     * 查询楼宇详情
     * @param query
     * @return
     */
    @RequestMapping(value = "showDetail",method = RequestMethod.GET)
    @ResponseBody
    public  ResponseResult<Building> getDetail(BaseQuery query){
        Building building = (Building) buildingService.searchByPrimaryKey(query).get("building");
        ResponseResult responseResult = new ResponseResult();
        responseResult.setData(building);
        responseResult.setMsg("查询楼宇详细信息");
        return responseResult;
    }


}
