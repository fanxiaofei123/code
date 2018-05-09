package com.youedata.cd.industries.service.md.impl;

import com.youedata.cd.base.service.impl.BaseServiceImpl;
import com.youedata.cd.industries.dao.base.ILocalBaseDao;
import com.youedata.cd.industries.dao.md.TypesDao;
import com.youedata.cd.industries.pojo.md.Types;
import com.youedata.cd.industries.service.md.ITypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Administrator on 2017/10/16.
 */
@Service
public class TypesServiceImpl  extends BaseServiceImpl<Types> implements ITypesService{
    @Autowired
    private TypesDao typesDao;

    @Override
    public ILocalBaseDao<Types> getBaseDao() {
        return typesDao;
    }


    @Override
    public Map<Object, Object> getList() {
        return typesDao.queryAll();
    }
}
