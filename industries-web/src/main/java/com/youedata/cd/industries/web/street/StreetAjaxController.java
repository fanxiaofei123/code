package com.youedata.cd.industries.web.street;

import com.youedata.cd.common.http.ResponseResult;
import com.youedata.cd.industries.pojo.street.Street;
import com.youedata.cd.industries.query.BaseQuery;
import com.youedata.cd.industries.service.street.StreetService;
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
@RequestMapping("street")
public class StreetAjaxController {

    @Autowired
    private StreetService streetService;

    /**
     * 通过街道Id查询社区
     * @param query
     * @return
     */
    @RequestMapping(value ="searchById",method = RequestMethod.GET )
    @ResponseBody
    public ResponseResult<Map<String,Object>>  searchByPrimaryKey(BaseQuery query){
        System.err.println("query"+query);
        Map<String,Object>  pageList = streetService.searchByPrimaryKey(query);
        ResponseResult responseResult = new ResponseResult();
        responseResult.setMsg("街道查询成功");
        responseResult.setData(pageList);
        return responseResult;
    }

    /**
     * 查询所有街道
     * @param
     * @return
     */
    @RequestMapping(value ="searchAll",method = RequestMethod.GET )
    @ResponseBody
    public ResponseResult<Map<String,Object>>  searchAll(){
        List<Street> streets = streetService.selectAll();
        ResponseResult responseResult = new ResponseResult();
        responseResult.setMsg("街道查询成功");
        responseResult.setData(streets);
        return responseResult;
    }
}
