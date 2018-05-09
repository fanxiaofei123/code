package com.youedata.cd.industries.service.excel;

import com.youedata.cd.base.service.IBaseService;
import com.youedata.cd.industries.dto.excel.ChangeRecruitmentUpdatesDto;
import com.youedata.cd.industries.dto.excel.FailedChangeinfo;

import java.util.List;

public interface FailedChangeinfoService  {
    public List<ChangeRecruitmentUpdatesDto> selectFailedChangeinfoByLogId(Integer logId);
}