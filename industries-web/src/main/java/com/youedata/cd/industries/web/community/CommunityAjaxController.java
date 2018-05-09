package com.youedata.cd.industries.web.community;

import com.youedata.cd.common.http.ResponseResult;
import com.youedata.cd.industries.query.BaseQuery;
import com.youedata.cd.industries.service.community.CommunityService;
import com.youedata.cd.industries.service.street.StreetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by cdyoue on 2016/6/1.
 */
@Controller
@RequestMapping("community")
public class CommunityAjaxController {
    @Autowired
    private CommunityService communityService;

    /**
     * 任务代号：D007
     * 通过社区Id查询企业
     * @param query
     * @return
     */
    @RequestMapping(value ="searchById",method = RequestMethod.GET )
    @ResponseBody
    public ResponseResult<Map<String,Object>> searchByPrimaryKey(BaseQuery query){
        Map<String,Object>  pageList = communityService.searchByPrimaryKey(query);
        ResponseResult responseResult = new ResponseResult();
        responseResult.setMsg("社区查询成功");
        responseResult.setData(pageList);
        return responseResult;
    }
}
