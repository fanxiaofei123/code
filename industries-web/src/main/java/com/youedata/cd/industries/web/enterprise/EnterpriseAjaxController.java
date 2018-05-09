package com.youedata.cd.industries.web.enterprise;

import com.youedata.cd.common.http.ResponseResult;
import com.youedata.cd.industries.pojo.enterprise.Enterprise;
import com.youedata.cd.industries.query.BaseQuery;
import com.youedata.cd.industries.query.enterprise.EnterpriseQuery;
import com.youedata.cd.industries.service.enterprise.EnterpriseService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cdyoue on 2016/5/31.
 */
@Controller
@RequestMapping("enterPrise")
public class EnterpriseAjaxController {
    @Autowired
    private EnterpriseService enterpriseService;
    /**
     *任务代号：D001
     * @param query
     * @return
     */
    @RequestMapping(value = "indexSearch",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult indexSearch(EnterpriseQuery query){
        query.setEnterpriseName(query.getEnterpriseName().trim());
        Map<String, Object>  pageList  = enterpriseService.selectByQuery(query);
        ResponseResult responseResult = new ResponseResult();
        responseResult.setData(pageList);
        //设置查询结果类型 1表示企业 2表示楼宇
        responseResult.setMsg("1");
        return responseResult;
    }

    /**
     * 查询企业数
     * @param query
     * @return
     */
    @RequestMapping(value = "selectEnterPriseCounts",method = RequestMethod.GET)
    @ResponseBody
    public  ResponseResult selectEnterPriseCounts(EnterpriseQuery query){
        Integer enterpriseCounts =  enterpriseService.selectByCount(query);
        ResponseResult responseResult = new ResponseResult();
        responseResult.setData(enterpriseCounts);
        responseResult.setMsg("企业数目");
        return responseResult;
    }




    /**
     * 确定变更
     * @return
     */
    @RequestMapping(value = "modifyInfo",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult showChanges(Long id,String address){
        if(id != null && StringUtils.isNotBlank(address)){
            enterpriseService.modifyInfo(id,address);
        }
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(200);
        responseResult.setMsg("企业");
        return responseResult;
    }



    /**
     * 取消
     * @return
     */
    @RequestMapping(value = "cancelModifyInfo",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult cancelModifyInfo(Long id){
        if(id != null){
            enterpriseService.cancelModifyInfo(id);
        }
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(200);
        responseResult.setMsg("企业");
        return responseResult;
    }



    @RequestMapping(value = "addressCancelModifyInfo",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult addressCancelModifyInfo(Long id){
        if(id != null){
            enterpriseService.addressCancelModifyInfo(id);
        }
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(200);
        responseResult.setMsg("企业");
        return responseResult;
    }


    /**
     * 任务代号：D006
     * 通过id显示企业详细信息
     * @param query
     * @return
     */
    @RequestMapping(value = "showDetail",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<Enterprise> getDetail(BaseQuery query){
        Enterprise enterprise = (Enterprise) enterpriseService.searchByPrimaryKey(query).get("enterprise");
        ResponseResult responseResult = new ResponseResult();
        responseResult.setData(enterprise);
        responseResult.setMsg("查询企业详细信息");
        return responseResult;
    }
    /**
     * 查看地址业务变更
     */
    @RequestMapping(value = "showChanges",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<Enterprise> showChanges(EnterpriseQuery query){
        Map<String,Object> changeMap = enterpriseService.getChangeEnterprise(query);
        ResponseResult responseResult = new ResponseResult();
        responseResult.setData(changeMap);
        responseResult.setMsg("企业信息变更列表");
        return responseResult;
    }


    /**
     * 通过企业id查看信息变更历史
     */
    @RequestMapping(value = "selectChangesEnterprise",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<Enterprise> selectChangesEnterpriseById(EnterpriseQuery query){
        //query.setAddressChange("1");
        Map<String,Object> changeMap = enterpriseService.selectChangesEnterpriseById(query);
        ResponseResult responseResult = new ResponseResult();
        responseResult.setData(changeMap);
        responseResult.setMsg("企业信息变更列表");
        return responseResult;
    }


    /**
     * 统计企业信息与地址变更数量
     * changeCountsCondition: 1统计主营业务变更数量 2 统计地址变更数量 3统计两个有变更数量
     * @param query
     * @return
     */
    @RequestMapping(value = "showChangesCounts",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<Enterprise> showChangesCounts(EnterpriseQuery query){
        ResponseResult responseResult = new ResponseResult();
        Integer changeEnterpriseCounts = enterpriseService.getChangeEnterpriseCounts(query);
        responseResult.setData(changeEnterpriseCounts);
        responseResult.setMsg("企业变更数目");
        return responseResult;
    }
}
