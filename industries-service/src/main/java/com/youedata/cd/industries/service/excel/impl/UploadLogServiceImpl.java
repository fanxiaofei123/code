package com.youedata.cd.industries.service.excel.impl;

import com.youedata.cd.base.dao.IBaseDao;
import com.youedata.cd.base.service.impl.BaseServiceImpl;
import com.youedata.cd.industries.dao.excel.UploadLogDao;
import com.youedata.cd.industries.pojo.log.UploadLog;
import com.youedata.cd.industries.service.excel.UploadLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("uploadLogService")
public class UploadLogServiceImpl extends BaseServiceImpl<UploadLog> implements UploadLogService {
    private static final Logger logger = LoggerFactory.getLogger(UploadLogServiceImpl.class);

    @Autowired
    private UploadLogDao uploadLogDao;

    @Override
    public IBaseDao<UploadLog> getBaseDao() {
        return uploadLogDao;
    }
}