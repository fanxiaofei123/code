package com.youedata.cd.industries.service.heatmap;

import com.youedata.cd.industries.pojo.heatmap.Version;
import com.youedata.cd.industries.query.BaseQuery;

import java.util.Calendar;
import java.util.List;

/**
 * Created by chenyongke on 2016/6/6.
 */
public interface HeatMapService {

    List<Version> selectByQuery();

    Version selectByQuery(String begin, String end);
}
