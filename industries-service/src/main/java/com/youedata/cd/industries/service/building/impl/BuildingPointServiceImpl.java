package com.youedata.cd.industries.service.building.impl;

import com.youedata.cd.base.dao.IBaseDao;
import com.youedata.cd.base.service.impl.BaseServiceImpl;
import com.youedata.cd.industries.dao.building.BuildingPointMapper;
import com.youedata.cd.industries.pojo.building.BuildingPoint;
import com.youedata.cd.industries.service.building.BuildingPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by chenyongke on 2016/6/7.
 */
//building gps坐标转百度坐标
    @Service
public class BuildingPointServiceImpl extends BaseServiceImpl<BuildingPoint> implements BuildingPointService{
    @Autowired
    private BuildingPointMapper buildingPointMapper;

    @Override
    public IBaseDao<BuildingPoint> getBaseDao() {
        return buildingPointMapper;
    }
    public List<BuildingPoint> findAll(){
        List<BuildingPoint> list = buildingPointMapper.selectAll();
        return list;
    }

}
