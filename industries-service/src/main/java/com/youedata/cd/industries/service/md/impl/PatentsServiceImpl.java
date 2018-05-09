package com.youedata.cd.industries.service.md.impl;

import com.sun.javafx.collections.MappingChange;
import com.youedata.cd.base.service.impl.BaseServiceImpl;
import com.youedata.cd.common.http.ResponseResult;
import com.youedata.cd.industries.dao.base.ILocalBaseDao;
import com.youedata.cd.industries.dao.md.PatentsDao;
import com.youedata.cd.industries.pojo.md.Patents;
import com.youedata.cd.industries.service.md.IPatentsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/16.
 */
@Service
public class PatentsServiceImpl implements IPatentsService {
    @Autowired
    private PatentsDao patentsDao;

    @Override
    public ResponseResult listPageByIpcfAndSubject(String id, String ipcf, Integer currentPage, Integer limit, String orderBy, String sort) {
        ResponseResult ret = new ResponseResult();
        //参数校验
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(ipcf)) {
            ret.setCode(400);
            ret.setMsg("主体ID或行业分类不能为空！");
            return ret;
        }
        currentPage = currentPage <= 0 ? 1 : currentPage;
        limit = (limit <= 0 || limit > 1000) ? 10 : limit;
        int start = (currentPage - 1) * limit;
        String orderType;
        if ("desc".equalsIgnoreCase(sort)) {
            orderType = "desc";
        } else {
            orderType = "asc";
        }
        if (StringUtils.isEmpty(orderBy)) {
            //默认按申请时间排序
            orderBy = "announcement_date";
        }
        Map query = new HashMap();
        query.put("id", id);
        query.put("ipcf", ipcf);
        query.put("start", start);
        query.put("limit", limit);
        query.put("orderByClause", orderBy);
        query.put("orderType", orderType);
        try {
            List list = patentsDao.selectByMapPage(query);
            Long count = patentsDao.selectByMapCount(query);
            Map data = new HashMap();
            data.put("data", list);
            data.put("total", count);
            data.put("currentPage", currentPage);
            ret.setData(data);
        } catch (Exception e) {
            e.printStackTrace();
            ret.setCode(400);
            ret.setMsg("系统异常！");
        }
        return ret;
    }

    @Override
    public Patents getBySubjectAndTitle(String id, String title) {
        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(title)) return null;
        Map query = new HashMap();
        query.put("id", id);
        query.put("title", title);
        Patents patents = patentsDao.query(query);
        return patents;
    }


}
