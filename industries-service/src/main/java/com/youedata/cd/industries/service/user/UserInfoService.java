package com.youedata.cd.industries.service.user;

import com.youedata.cd.industries.pojo.user.UserInfo;
import com.youedata.cd.industries.service.base.LocalBaseService;

/**
 * Created by cdyoue on 2016/7/6.
 */
public interface UserInfoService extends LocalBaseService<UserInfo>{
    UserInfo selectByName(String name);
}
