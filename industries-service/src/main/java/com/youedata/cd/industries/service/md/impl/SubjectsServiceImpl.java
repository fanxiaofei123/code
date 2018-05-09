package com.youedata.cd.industries.service.md.impl;

import com.youedata.cd.common.http.ResponseResult;
import com.youedata.cd.industries.dao.md.SubjectsDao;
import com.youedata.cd.industries.service.md.ISubjectsService;
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
public class SubjectsServiceImpl implements ISubjectsService {
    @Autowired
    private SubjectsDao subjectsDao;

    @Override
    public ResponseResult listPageByIpcf(String ipcf, Integer currentPage, Integer limit) {
        ResponseResult ret = new ResponseResult();
        //参数校验
        if (StringUtils.isEmpty(ipcf)) {
            ret.setCode(400);
            ret.setData("行业分类不能为空！");
            return ret;
        }
        currentPage = currentPage <= 0 ? 1 : currentPage;
        limit = (limit <= 0 || limit > 1000) ? 10 : limit;
        int start = (currentPage - 1) * limit;
        Map query = new HashMap();
        query.put("ipcf", ipcf);
        query.put("start", start);
        query.put("limit", limit);
        try {
            List list = subjectsDao.selectByMapPage(query);
            Long count = subjectsDao.selectByMapCount(query);
            Map data = new HashMap();
            data.put("data", list);
            data.put("total", count);
            data.put("currentPage", currentPage);
            ret.setData(data);
        } catch (Exception e) {
            e.printStackTrace();
            ret.setCode(500);
            ret.setMsg("系统异常！");
        }
        return ret;
    }

    @Override
    public ResponseResult listById(String id) {
        ResponseResult ret = new ResponseResult();
        if (StringUtils.isEmpty(id)) {
            ret.setCode(400);
            ret.setMsg("主体ID不能为空！");
            return ret;
        }
        try {
            List list = subjectsDao.queryById(id);
            ret.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}
