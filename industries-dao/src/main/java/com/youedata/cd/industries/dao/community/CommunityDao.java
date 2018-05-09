package com.youedata.cd.industries.dao.community;

import com.youedata.cd.industries.dao.base.ILocalBaseDao;
import com.youedata.cd.industries.pojo.community.Community;

import java.util.List;

public interface CommunityDao extends ILocalBaseDao<Community> {
    List<Community> searchCommunityByStreet(Integer id);
    List<Community> findCommunityByStreet(String streetName);
}