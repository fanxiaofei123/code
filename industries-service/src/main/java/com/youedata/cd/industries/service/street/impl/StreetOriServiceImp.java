package com.youedata.cd.industries.service.street.impl;

import com.youedata.cd.base.dao.IBaseDao;
import com.youedata.cd.base.service.impl.BaseServiceImpl;
import com.youedata.cd.industries.dao.street.StreetDao;
import com.youedata.cd.industries.pojo.street.Street;
import com.youedata.cd.industries.service.street.StreetOriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by cdyoue on 2016/7/12.
 */
@Service
public class StreetOriServiceImp extends BaseServiceImpl<Street> implements StreetOriService {
    @Autowired
    private StreetDao streetDao;
    @Override
    public IBaseDao<Street> getBaseDao() {
        return streetDao;
    }
}
