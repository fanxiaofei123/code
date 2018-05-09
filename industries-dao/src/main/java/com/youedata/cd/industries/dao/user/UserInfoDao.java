package com.youedata.cd.industries.dao.user;

import com.youedata.cd.industries.dao.base.ILocalBaseDao;
import com.youedata.cd.industries.pojo.user.UserInfo;

import java.util.List;

/**
 * Created by cdyoue on 2016/7/6.
 */
public interface UserInfoDao extends ILocalBaseDao<UserInfo> {
    UserInfo selectByName(String name);
}
