package com.youedata.cd.industries.service.address;

import com.youedata.cd.base.service.IBaseService;
import com.youedata.cd.industries.pojo.street.Street;

import java.util.List;

/**
 * Created by chenyongke on 2016/5/31.
 */
public interface PointService  {
    public List<Street> selectAllName();
    public void updateLocation(Street street);
}
