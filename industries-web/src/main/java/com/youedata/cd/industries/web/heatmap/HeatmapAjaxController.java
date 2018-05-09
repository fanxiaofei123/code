package com.youedata.cd.industries.web.heatmap;

import com.youedata.cd.common.constant.Constant;
import com.youedata.cd.common.http.ResponseResult;
import com.youedata.cd.industries.pojo.heatmap.Version;
import com.youedata.cd.industries.query.BaseQuery;
import com.youedata.cd.industries.service.heatmap.HeatMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.util.Calendar;
import java.util.List;
import java.util.Map;


/**
 * Created by chenyongke on 2016/6/6.
 */
@Controller
@RequestMapping("heatmap")
public class HeatmapAjaxController {
    @Autowired
    private HeatMapService heatmapService;

    /**
     * 查询热地图展示数据
     * @return
     */
    @RequestMapping(value = "indexSearch",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult selectAll(){
        List<Version> versions = heatmapService.selectByQuery();
        ResponseResult responseResult = new ResponseResult();
        responseResult.setData(versions);
        return responseResult;
    }

    /**
     * 查询热地图展示数据
     * quarter 1-4季度
     * @return
     */
    @RequestMapping(value = "indexSearch1",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult indexSearch1(Integer year,Integer quarter){
        String quarterString,end,begin;
//        if(quarter*3<10){
//            begin = ""+year+"0"+(quarter*3-2)+"01000000";
//        }else{
//            begin = ""+year+(quarter*3-2)+"01000000";
//        }
//
//        if(quarter==4){
//            end = ""+(year+1)+"0101000000";
//        }else{
//            int temp=quarter*3+1;
//            if(temp<10){
//                end = ""+year+"0"+temp+"01000000";
//            }else{
//                end = ""+year+temp+"01000000";
//            }
//        }

        switch (quarter) { // 根据季度设置起止时间
            case 1:
                begin=year + "0101000000";
                end=year + "0331000000";
                break;
            case 2:
                begin=year + "0401000000";
                end=year + "0630000000";
                break;
            case 3:
                begin=year + "0701000000";
                end=year + "0930000000";
                break;
            case 4:
                begin=year + "1001000000";
                end=year + "1231000000";
                break;
            default:
                begin=year + "0101000000";
                end=year + "0331000000";
                break;
        }

        Version version = heatmapService.selectByQuery(begin,end);
        version.setVersion(Integer.valueOf((""+year+quarter)));
        ResponseResult responseResult = new ResponseResult();
        responseResult.setData(version);
        return responseResult;
    }
}
