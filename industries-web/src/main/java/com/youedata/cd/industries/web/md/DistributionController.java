package com.youedata.cd.industries.web.md;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.youedata.cd.common.http.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.youedata.cd.industries.service.md.IDistributionService;

/**
 * Created by Administrator on 2017/10/16.
 */
@Controller
@RequestMapping("distribution")
public class DistributionController {
    @Autowired
    private IDistributionService service;

    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ResponseEntity list() {
        List<Map<Object, Object>> list = service.getList();
        //JSONObject js = new JSONObject();
        //js.put("jss",list);
       ResponseEntity<List> entity = new ResponseEntity(list, HttpStatus.OK);
        return entity;
    }

    @ResponseBody
    @RequestMapping(value = "industry",method = RequestMethod.GET)
    public ResponseResult<Map<String,Object>> industry(@RequestParam(value = "fyear",required = false) Integer fyear,@RequestParam(value = "pyear",required = false) Integer pyear){
        return service.getIndustryList(fyear,pyear);
    }

    @ResponseBody
    @RequestMapping(value = "/statistics",method = RequestMethod.GET)
    public ResponseResult<List<Map<String,Object>>> statistics(){
        return service.getStatisticsList();
    }

    @RequestMapping(value = "/vitality",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<List<Map<String,Object>>> patensVitality(@RequestParam(value = "authorize", required = false) Integer authorize) {
        return service.getPatensVitality(authorize);
    }
    @RequestMapping(value = "/time",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<List<Map<String,Object>>> patensTime(@RequestParam(value = "patentType", required = true) String patentType) {
        return service.getPatensTime(patentType);
    }

}
