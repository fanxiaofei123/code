package com.youedata.cd.industries.service.excel;

import com.youedata.cd.industries.dao.enterprise.EnterpriseDtoFailDao;
import com.youedata.cd.industries.dto.excel.ChangeRecruitmentUpdatesDto;
import com.youedata.cd.industries.dto.excel.EnterpriseDataUpdatesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by cdyoue on 2016/7/20.
 */
@Service
public class ExcelFailedEnterpriseService {
    @Autowired
    private EnterpriseDtoFailDao enterpriseDtoFailDao;

    public List<EnterpriseDataUpdatesDto> selectEnterpriseDtoByLogId(Integer id){
        List<EnterpriseDataUpdatesDto> enterpriseDataUpdatesDtos = enterpriseDtoFailDao.selectEnterpriseDtoByLogId(id);
        return enterpriseDataUpdatesDtos;
    }

    public List<ChangeRecruitmentUpdatesDto> selectChangeRecruitmentUpdatesDtoByLogId(Integer id) {
        return enterpriseDtoFailDao.selectChangeRecruitmentUpdatesDtoByLogId(id);
    }
}
