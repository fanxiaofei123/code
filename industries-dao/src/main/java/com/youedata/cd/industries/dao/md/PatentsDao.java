package com.youedata.cd.industries.dao.md;

import com.youedata.cd.industries.dao.base.ILocalBaseDao;
import com.youedata.cd.industries.pojo.md.Patents;

import java.util.Map;


public interface PatentsDao extends ILocalBaseDao<Patents>{

    Map<Object, Object> queryAll();

    Patents query(Map query);

}