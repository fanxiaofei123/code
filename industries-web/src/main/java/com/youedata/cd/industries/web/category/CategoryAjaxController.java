package com.youedata.cd.industries.web.category;

import com.youedata.cd.base.service.IBaseService;
import com.youedata.cd.base.web.controller.BaseAjaxController;
import com.youedata.cd.common.http.ResponseResult;
import com.youedata.cd.industries.pojo.category.Category;
import com.youedata.cd.industries.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by LocalPin on 2016/5/31.
 */
@Controller
@RequestMapping("category")
public class CategoryAjaxController extends BaseAjaxController<Category>{
    @Autowired
    private CategoryService categoryService;
    @Override
    public IBaseService<Category> getBaseService() {
        return categoryService;
    }
    /**
     * 任务代号：D002
     * 获得所有产业
     * @return
     */
    @RequestMapping(value = "getIndustries",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<Category> getIndustries(){
        Category category = new Category();
        category.setGrade(1);
        category.setLimit(20);
        List<Category> categories = categoryService.select(category);
        ResponseResult responseResult= new ResponseResult();
        responseResult.setData(categories);
        responseResult.setMsg("所有产业数据查询成功");
        return responseResult;
    }

    /**
     * 任务代号：D002
     * 通过产业Id查询行业
     * @param id 点到3
     * @return
     */
    @RequestMapping(value = "getTradeById",method =RequestMethod.GET)
    @ResponseBody
    public ResponseResult<Category> getTradeByIndustryId(Integer id){
        ResponseResult responseResult= new ResponseResult();
        if(id != null){
            Category category = new Category();
            category.setGrade(2);
            category.setParentId(id);
            category.setLimit(Integer.MAX_VALUE);
            List<Category> categories = categoryService.select(category);
            responseResult.setData(categories);
            responseResult.setMsg("通过产业Id查询行业");
        }else{
            responseResult.setMsg("请输入产业id");
        }

        return responseResult;
    }

    /**
     * 任务代号：D003
     * 通过行业Id查询门类
     * @param id
     * @return
     */
    @RequestMapping(value = "getCategoryById",method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<Category> getCategoryByTradeId(Integer id){

        ResponseResult responseResult= new ResponseResult();
        if(id != null){
            Category category = new Category();
            category.setGrade(3);
            category.setParentId(id);
            category.setLimit(Integer.MAX_VALUE);
            List<Category> categories = categoryService.select(category);
            responseResult.setData(categories);
            responseResult.setMsg("通过行业Id查询门类");
        }else{
            responseResult.setMsg("请输入行业id");
        }

        return responseResult;
    }

    /**
     * 通过产业Id查询行业
     */
    @RequestMapping(value = "getByIndustryId",method =RequestMethod.GET)
    @ResponseBody
    public ResponseResult<Category> getByIndustryId(Integer id){
        ResponseResult responseResult= new ResponseResult();
        if(id != null){
            Category category = new Category();
            category.setParentId(id);
            List<Category> categories = categoryService.selectCategoryById(id);
            responseResult.setData(categories);
            responseResult.setMsg("通过产业Id查询行业");
        }else{
            responseResult.setMsg("请输入产业id");
        }

        return responseResult;
    }


}
