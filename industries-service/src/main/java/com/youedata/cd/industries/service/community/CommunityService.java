package com.youedata.cd.industries.service.community;

import com.youedata.cd.base.service.IBaseService;
import com.youedata.cd.industries.pojo.community.Community;
import com.youedata.cd.industries.query.BaseQuery;
import com.youedata.cd.industries.service.base.LocalBaseService;

import java.util.List;
import java.util.Map;

public interface CommunityService extends LocalBaseService<Community> {
    List<Community> searchCommunityByStreet(Integer id);
}