package com.youedata.cd.industries.service.community.impl;

import com.youedata.cd.base.dao.IBaseDao;
import com.youedata.cd.base.service.impl.BaseServiceImpl;
import com.youedata.cd.industries.dao.base.ILocalBaseDao;
import com.youedata.cd.industries.dao.community.CommunityDao;
import com.youedata.cd.industries.pojo.community.Community;
import com.youedata.cd.industries.query.BaseQuery;
import com.youedata.cd.industries.service.base.impl.LocalBaseServiceImpl;
import com.youedata.cd.industries.service.community.CommunityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

@Service("communityService")
public class CommunityServiceImpl extends LocalBaseServiceImpl<Community> implements CommunityService {
    private static final Logger logger = getLogger(CommunityServiceImpl.class);
    @Autowired
    private CommunityDao communityDao;
    @Override
    public ILocalBaseDao<Community> getBaseDao() {
        return communityDao;
    }

    @Override
    public List<Community> searchCommunityByStreet(Integer id) {
        return communityDao.searchCommunityByStreet(id);
    }
}