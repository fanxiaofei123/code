package com.youedata.cd.industries.service.street.impl;

import com.youedata.cd.base.dao.IBaseDao;
import com.youedata.cd.base.service.impl.BaseServiceImpl;
import com.youedata.cd.industries.dao.base.ILocalBaseDao;
import com.youedata.cd.industries.dao.street.StreetDao;
import com.youedata.cd.industries.pojo.street.Street;
import com.youedata.cd.industries.query.BaseQuery;
import com.youedata.cd.industries.service.base.LocalBaseService;
import com.youedata.cd.industries.service.base.impl.LocalBaseServiceImpl;
import com.youedata.cd.industries.service.street.StreetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("streetService")
public class StreetServiceImpl extends LocalBaseServiceImpl<Street> implements StreetService {
    private static final Logger logger = LoggerFactory.getLogger(StreetServiceImpl.class);

    @Autowired
    private StreetDao streetDao;


    @Override
    public ILocalBaseDao<Street> getBaseDao() {
        return streetDao;
    }

    @Override
    public Map<String, Object> searchByPrimaryKey(BaseQuery query){
        Street street = streetDao.searchByPrimaryKey(query);
        Map<String, Object> pageList = new HashMap();
        pageList.put("street",street);
        pageList.put("currentPage", query.getCurrentPage());
        return pageList;
    }

    @Override
    public List<Street> selectByMap(Street street) {
        return streetDao.selectByMap(street);
    }

}