package com.youedata.cd.industries.web.admin.address;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by honshe、T on 2016/7/11.
 */
@Controller
@RequestMapping("change")
public class ChangeController {

    @RequestMapping(value = "show",method = RequestMethod.GET)
    public String show() {
        return "change/show";
    }
}
