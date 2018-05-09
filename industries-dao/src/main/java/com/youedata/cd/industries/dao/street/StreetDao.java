package com.youedata.cd.industries.dao.street;

import com.youedata.cd.industries.dao.base.ILocalBaseDao;
import com.youedata.cd.industries.pojo.street.Street;

import java.util.List;
import java.util.Map;

public interface StreetDao extends ILocalBaseDao<Street> {
    List<Street> findAllStreetName();
    List<Street> selectByMap(Street street);
}