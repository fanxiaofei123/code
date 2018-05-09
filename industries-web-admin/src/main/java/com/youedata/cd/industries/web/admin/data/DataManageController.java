package com.youedata.cd.industries.web.admin.data;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by honshe on 2016/7/11.
 * 数据导入controller
 */
@Controller
@RequestMapping("data")
public class DataManageController {

    @RequestMapping(value = "enterpriseDataUpdates", method = RequestMethod.GET)
    public String enterpriseDataUpdates() {
        return "dataImport/enterpriseDataUpdates";
    }

    @RequestMapping(value = "buildingDataUpdates", method = RequestMethod.GET)
    public String buildingDataUpdates() {
        return "dataImport/buildingDataUpdates";
    }

    @RequestMapping(value = "changeDataUpdates", method = RequestMethod.GET)
    public String changeDataUpdates() {
        return "dataImport/changeDataUpdates";
    }

    @RequestMapping(value = "enterpriseOperationDataUpdates", method = RequestMethod.GET)
    public String enterpriseOperationDataUpdates() {
        return "dataImport/enterpriseOperationDataUpdates";
    }
}
