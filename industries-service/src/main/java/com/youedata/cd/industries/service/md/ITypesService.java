package com.youedata.cd.industries.service.md;

import com.youedata.cd.base.service.IBaseService;
import com.youedata.cd.industries.pojo.md.Types;

import java.util.Map;

/**
 * Created by Administrator on 2017/10/16.
 */

public interface ITypesService  extends IBaseService<Types> {
    Map<Object, Object> getList();


}
