package com.youedata.cd.industries.service.base;

import com.youedata.cd.base.service.IBaseService;
import com.youedata.cd.industries.query.BaseQuery;

import java.util.Map;

/**
 * Created by cdyoue on 2016/6/28.
 */
public interface LocalBaseService<T> extends IBaseService {
    Map<String,Object> searchByPrimaryKey(BaseQuery query);
    Integer selectByCount(BaseQuery query);
}
