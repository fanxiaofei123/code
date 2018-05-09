package com.youedata.cd.industries.dao.point;

import com.youedata.cd.base.dao.IBaseDao;
import com.youedata.cd.industries.pojo.street.Street;

import java.util.List;
import java.util.Map;

/**
 * Created by chenyongke on 2016/5/31.
 */
public interface PointDao extends IBaseDao<Street> {
    List<Street> selectAllName();
    void updateLocation(Map map);
}
