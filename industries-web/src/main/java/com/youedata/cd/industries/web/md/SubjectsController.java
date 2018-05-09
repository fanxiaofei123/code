package com.youedata.cd.industries.web.md;

import com.youedata.cd.common.http.ResponseResult;
import com.youedata.cd.industries.service.md.ISubjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/10/16.
 */
@Controller
@RequestMapping("subjects")
public class SubjectsController {
    @Autowired
    private ISubjectsService service;

    /**
     *“创新能力行业排名”页面列表数据
     * @param ipcf 行业分类
     * @param currentPage 当前第几页
     * @param limit 每页多少条
     * @return 列表
     */
    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ResponseEntity list(String ipcf, Integer currentPage, Integer limit) {
        ResponseResult ret = service.listPageByIpcf(ipcf, currentPage, limit);
        return new ResponseEntity(ret, HttpStatus.OK);
    }

    /**
     * 获取某个主体的专利统计数据
     * @param id 主体ID
     * @return 列表
     */
    @ResponseBody
    @RequestMapping(value = "listById", method = RequestMethod.GET)
    public ResponseEntity listById(String id) {
        ResponseResult ret = service.listById(id);
        return new ResponseEntity(ret, HttpStatus.OK);
    }
}
