package com.youedata.cd.industries.service.category;

import com.youedata.cd.base.service.IBaseService;
import com.youedata.cd.industries.pojo.category.Category;

import java.util.List;

public interface CategoryService extends IBaseService<Category> {
    /*查询所有产业/所有行业/所有门类*/
    List<Category> queryByGrade(Integer grade);
    List<Category> selectCategoryById(Integer parentId);
}