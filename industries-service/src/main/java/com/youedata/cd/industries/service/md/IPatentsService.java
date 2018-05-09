package com.youedata.cd.industries.service.md;

import com.youedata.cd.common.http.ResponseResult;
import com.youedata.cd.industries.pojo.md.Patents;

/**
 * Created by Administrator on 2017/10/16.
 */

public interface IPatentsService {
    ResponseResult listPageByIpcfAndSubject(String id, String ipcf, Integer currentPage, Integer limit, String orderBy, String sort);

    Patents getBySubjectAndTitle(String id, String title);

}
