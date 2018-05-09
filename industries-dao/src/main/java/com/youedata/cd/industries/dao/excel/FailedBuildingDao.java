package com.youedata.cd.industries.dao.excel;

import com.youedata.cd.base.dao.IBaseDao;
import com.youedata.cd.industries.dto.excel.BuildingDataUpdatesDto;
import com.youedata.cd.industries.dto.excel.FailedBuilding;

import java.util.List;

public interface FailedBuildingDao extends IBaseDao<FailedBuilding> {
    List<BuildingDataUpdatesDto> selectFailedBuildingByLogId(Integer logId);
}