package com.youedata.cd.industries.dao.excel;

import com.youedata.cd.base.dao.IBaseDao;
import com.youedata.cd.industries.dto.excel.ChangeRecruitmentUpdatesDto;
import com.youedata.cd.industries.dto.excel.FailedChangeinfo;

import java.util.List;

public interface FailedChangeinfoDao {
    List<ChangeRecruitmentUpdatesDto> selectFailedChangeinfoByLogId(Integer logId);
}