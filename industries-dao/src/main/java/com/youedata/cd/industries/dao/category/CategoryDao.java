package com.youedata.cd.industries.dao.category;

import com.youedata.cd.base.dao.IBaseDao;
import com.youedata.cd.industries.pojo.category.Category;
import com.youedata.cd.industries.pojo.category.CategoryCustom;

import java.util.List;

public interface CategoryDao extends IBaseDao<Category> {
    Integer findId(String name);
    List<CategoryCustom> selectTradeById(Integer parentId );
    List<Category> queryByGrade(Integer grade);
    List<Category> selectCategoryById(Integer parentId);
}