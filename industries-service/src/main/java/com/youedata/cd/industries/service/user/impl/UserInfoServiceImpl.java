package com.youedata.cd.industries.service.user.impl;

import com.youedata.cd.industries.dao.base.ILocalBaseDao;
import com.youedata.cd.industries.dao.user.UserInfoDao;
import com.youedata.cd.industries.pojo.user.UserInfo;
import com.youedata.cd.industries.service.base.impl.LocalBaseServiceImpl;
import com.youedata.cd.industries.service.enterprise.EnterpriseService;
import com.youedata.cd.industries.service.user.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by cdyoue on 2016/7/6.
 */
@Service
public class UserInfoServiceImpl extends LocalBaseServiceImpl<UserInfo> implements UserInfoService{
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    EnterpriseService enterpriseService;

    @Override
    public ILocalBaseDao<UserInfo> getBaseDao() {
        return userInfoDao;
    }

    @Override
    public UserInfo selectByName(String name) {
        return userInfoDao.selectByName(name);
    }
}
