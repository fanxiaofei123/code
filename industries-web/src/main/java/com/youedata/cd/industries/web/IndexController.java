package com.youedata.cd.industries.web;

import com.youedata.cd.industries.pojo.test.Enterprise;
import com.youedata.cd.industries.pojo.test.HSDTO;
import com.youedata.cd.industries.pojo.test.MapCondition;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cdyoue on 2016/5/19.
 */
@Controller
public class IndexController {
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "heatMap", method = RequestMethod.GET)
    public String heatMap() {
        return "heatMap";
    }

    @RequestMapping(value = "change", method = RequestMethod.GET)
    public String change() {
        return "change";
    }

    @RequestMapping(value = "demo", method = RequestMethod.GET)
    public String demo() {
        return "pagination";
    }

    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.GET)
    ResponseEntity list() {
        List<HSDTO> hsdtos = new ArrayList<HSDTO>();
        for (int i = 0; i < 10; i++) {
            float rnd = (float) (0.1 * Math.random() * i);
            HSDTO hsdto = new HSDTO(116.297018f + rnd, 39.858693f + rnd);
            hsdto.setAddress("1234");
            hsdto.setStreetId(i);
            hsdto.setCount(1234);
            hsdtos.add(hsdto);
        }
        ResponseEntity<List> entity = new ResponseEntity(hsdtos, HttpStatus.OK);
        return entity;
    }

    @ResponseBody
    @RequestMapping(value = "enterprise", method = RequestMethod.GET)
    List<Enterprise> findByCondition(MapCondition condition) {
        List<Enterprise> list = new ArrayList<Enterprise>();
        int j;
        if (condition.getIndustryId().equals("")) {
            j = 1;
        } else {
            j = Integer.parseInt(condition.getIndustryId());

        }
        for (int i = 1; i <= j * 5; i++) {
            Enterprise enterprise = new Enterprise();
            enterprise.setId(i + "");
            enterprise.setName("我是第" + i + "家企业");
            enterprise.setBusiness("这是第" + i + "个行业");
            enterprise.setAddress("这是第" + i + "条街");
            list.add(enterprise);
        }
        return list;
    }
}
