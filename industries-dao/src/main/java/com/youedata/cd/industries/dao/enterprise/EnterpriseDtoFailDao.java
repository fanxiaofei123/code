package com.youedata.cd.industries.dao.enterprise;

import com.youedata.cd.industries.dto.excel.ChangeRecruitmentUpdatesDto;
import com.youedata.cd.industries.dto.excel.EnterpriseDataUpdatesDto;

import java.util.List;

/**
 * Created by cdyoue on 2016/7/20.
 */
public interface EnterpriseDtoFailDao {
    List<EnterpriseDataUpdatesDto> selectEnterpriseDtoByLogId(Integer id);

    List<ChangeRecruitmentUpdatesDto> selectChangeRecruitmentUpdatesDtoByLogId(Integer id);
}
