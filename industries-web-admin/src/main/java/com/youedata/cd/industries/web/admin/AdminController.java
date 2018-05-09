package com.youedata.cd.industries.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by cdyoue on 2016/6/30.
 */
@Controller
@RequestMapping("admin")
public class AdminController {
    @RequestMapping(value = "index",method = RequestMethod.GET)
    public String  showIndex(){

        return "login";
    }
}
