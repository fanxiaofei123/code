package com.youedata.cd.industries.service.excel.impl;

import com.youedata.cd.base.dao.IBaseDao;
import com.youedata.cd.base.service.impl.BaseServiceImpl;
import com.youedata.cd.industries.dao.excel.FailedChangeinfoDao;
import com.youedata.cd.industries.dto.excel.ChangeRecruitmentUpdatesDto;
import com.youedata.cd.industries.dto.excel.FailedChangeinfo;
import com.youedata.cd.industries.service.excel.FailedChangeinfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("failedChangeinfoService")
public class FailedChangeinfoServiceImpl  implements FailedChangeinfoService {
    private static final Logger logger = LoggerFactory.getLogger(FailedChangeinfoServiceImpl.class);

    @Autowired
    private FailedChangeinfoDao failedChangeinfoDao;


    @Override
    public List<ChangeRecruitmentUpdatesDto> selectFailedChangeinfoByLogId(Integer logId) {
        return failedChangeinfoDao.selectFailedChangeinfoByLogId(logId);
    }
}