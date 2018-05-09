package com.youedata.cd.industries.service.excel.impl;

import com.youedata.cd.base.dao.IBaseDao;
import com.youedata.cd.base.service.impl.BaseServiceImpl;
import com.youedata.cd.industries.dao.excel.FailedBuildingDao;
import com.youedata.cd.industries.dto.excel.BuildingDataUpdatesDto;
import com.youedata.cd.industries.dto.excel.FailedBuilding;
import com.youedata.cd.industries.service.excel.FailedBuildingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("failedBuildingService")
public class FailedBuildingServiceImpl implements FailedBuildingService {
    private static final Logger logger = LoggerFactory.getLogger(FailedBuildingServiceImpl.class);

    @Autowired
    private FailedBuildingDao failedBuildingDao;



    @Override
    public List<BuildingDataUpdatesDto> selectFailedBuildingByLogId(Integer logId) {
        return failedBuildingDao.selectFailedBuildingByLogId(logId);
    }
}