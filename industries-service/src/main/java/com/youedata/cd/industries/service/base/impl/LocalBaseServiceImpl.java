package com.youedata.cd.industries.service.base.impl;

import com.youedata.cd.base.service.impl.BaseServiceImpl;
import com.youedata.cd.industries.dao.base.ILocalBaseDao;
import com.youedata.cd.industries.query.BaseQuery;
import com.youedata.cd.industries.query.building.BuildingQuery;
import com.youedata.cd.industries.service.base.LocalBaseService;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.rmi.ServerError;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cdyoue on 2016/6/28.
 */
public abstract  class LocalBaseServiceImpl<T>  extends BaseServiceImpl implements LocalBaseService<T> {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(BaseServiceImpl.class);
    private Class<T> entityClass;
    private String entityName;
    private ILocalBaseDao otherBaseDao;

    public LocalBaseServiceImpl() {
        // 获取子类的Class实例
        Class clazz = getClass();
        // 获取子类直接父类的type
        Type type = clazz.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            // ParameterizedType泛型参数化类型
            ParameterizedType pType = (ParameterizedType) type;
            // 获取实际此类型参数的type
            entityClass = (Class) pType.getActualTypeArguments()[0];
            String str = entityClass.getSimpleName();
            entityName=Character.toLowerCase(str.charAt(0))+str.substring(1, str.length());
        }
    }

    public abstract ILocalBaseDao<T> getBaseDao();
    public ILocalBaseDao<T> getReadBaseDao() {
        return getBaseDao();
    }

    @Override
    public Map<String, Object> searchByPrimaryKey(BaseQuery query) {
        ILocalBaseDao baseDao = getReadBaseDao();
        String obj = entityName;

        T entityName = null;
        try {
            entityName = (T) baseDao.searchByPrimaryKey(query);
        } catch (Exception e) {
            LOGGER.error("通过主键查询错误",e);
        }
        Map<String, Object> pageList = new HashMap();
        pageList.put(obj,entityName);
        pageList.put("currentPage", query.getCurrentPage());
        return pageList;
    }

    @Override
    public Integer selectByCount(BaseQuery query) {
        ILocalBaseDao baseDao = getReadBaseDao();
        Integer count = null;
        try {
            count = baseDao.selectByCount(query);
        } catch (Exception e) {
            LOGGER.error("查询数量出错",e);
        }
        return count;
    }

}
