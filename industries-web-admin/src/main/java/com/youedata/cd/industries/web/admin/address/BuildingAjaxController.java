package com.youedata.cd.industries.web.admin.address;

import com.youedata.cd.base.service.IBaseService;
import com.youedata.cd.base.web.controller.BaseAjaxController;
import com.youedata.cd.industries.pojo.building.Building;
import com.youedata.cd.industries.service.building.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by honshe、T on 2016/7/13.
 */
@Controller
@RequestMapping("/building/abuilding")
public class BuildingAjaxController extends BaseAjaxController<Building> {
    @Autowired
    private BuildingService buildingService;

    @Override
    public IBaseService<Building> getBaseService() {
        return buildingService;
    }


}
