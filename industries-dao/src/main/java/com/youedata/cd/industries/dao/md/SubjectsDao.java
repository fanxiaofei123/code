package com.youedata.cd.industries.dao.md;

import com.youedata.cd.industries.dao.base.ILocalBaseDao;
import com.youedata.cd.industries.pojo.md.Subjects;

import java.util.List;
import java.util.Map;


public interface SubjectsDao extends ILocalBaseDao<Subjects>{

    Map<Object, Object> queryAll();

    List queryById(String id);
}