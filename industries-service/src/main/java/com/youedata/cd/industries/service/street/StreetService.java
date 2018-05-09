package com.youedata.cd.industries.service.street;

import com.youedata.cd.industries.pojo.street.Street;
import com.youedata.cd.industries.query.BaseQuery;
import com.youedata.cd.industries.service.base.LocalBaseService;

import java.util.List;
import java.util.Map;

public interface StreetService extends LocalBaseService<Street> {
    Map<String,Object> searchByPrimaryKey(BaseQuery query);
    List<Street> selectByMap(Street street);
    List<Street> selectAll();
}