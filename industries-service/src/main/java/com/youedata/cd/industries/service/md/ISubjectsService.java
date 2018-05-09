package com.youedata.cd.industries.service.md;

import com.youedata.cd.common.http.ResponseResult;

/**
 * Created by Administrator on 2017/10/16.
 */

public interface ISubjectsService  {
    ResponseResult listPageByIpcf(String ipcf, Integer currentPage, Integer limit);

    ResponseResult listById(String id);

}
