package com.youedata.cd.industries.service.category.impl;

import com.youedata.cd.base.dao.IBaseDao;
import com.youedata.cd.base.service.impl.BaseServiceImpl;
import com.youedata.cd.industries.dao.category.CategoryDao;
import com.youedata.cd.industries.pojo.category.Category;
import com.youedata.cd.industries.service.category.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("categoryService")
public class CategoryServiceImpl extends BaseServiceImpl<Category> implements CategoryService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public IBaseDao<Category> getBaseDao() {
        return categoryDao;
    }

    @Override
    public List<Category> queryByGrade(Integer grade) {
        return categoryDao.queryByGrade(grade);
    }

    @Override
    public List<Category> selectCategoryById(Integer parentId) {
        return  categoryDao.selectCategoryById(parentId);
    }
}