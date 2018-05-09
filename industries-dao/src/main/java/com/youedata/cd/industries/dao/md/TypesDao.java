package com.youedata.cd.industries.dao.md;


import com.youedata.cd.industries.dao.base.ILocalBaseDao;
import com.youedata.cd.industries.pojo.md.Types;

import java.util.Map;

public interface TypesDao extends ILocalBaseDao<Types> {
    Map<Object, Object> queryAll();

}