package com.youedata.cd.industries.service.address.impl;

import com.youedata.cd.base.dao.IBaseDao;
import com.youedata.cd.base.service.impl.BaseServiceImpl;
import com.youedata.cd.industries.dao.point.PointDao;

import com.youedata.cd.industries.pojo.street.Street;
import com.youedata.cd.industries.service.address.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenyongke on 2016/5/31.
 */
@Service
public class PointServiceImpl  implements PointService {
    @Autowired
    private PointDao pointDao;

    @Override
    public void updateLocation(Street street) {
        Map map=new HashMap<>();
        map.put("id",street.getId());
        map.put("lng",street.getLongitude());
        map.put("lat",street.getLatitude());
        pointDao.updateLocation(map);
    }
    public List<Street> selectAllName() {
        List<Street> list = pointDao.selectAllName();
        return list;
    }
}
