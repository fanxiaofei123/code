package com.youedata.cd.industries.dao.base;

import com.youedata.cd.base.dao.IBaseDao;
import com.youedata.cd.industries.query.BaseQuery;

import java.util.List;

/**
 * Created by cdyoue on 2016/6/28.
 */
public interface ILocalBaseDao<T> extends IBaseDao<T> {
    List<T> selectByQuery(BaseQuery query);
    Integer selectByCount(BaseQuery query);
    T searchByPrimaryKey(BaseQuery query);
}
