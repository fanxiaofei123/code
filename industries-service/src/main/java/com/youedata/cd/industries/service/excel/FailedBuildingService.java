package com.youedata.cd.industries.service.excel;

import com.youedata.cd.industries.dto.excel.BuildingDataUpdatesDto;

import java.util.List;

public interface FailedBuildingService  {
    public List<BuildingDataUpdatesDto> selectFailedBuildingByLogId(Integer logId);
}