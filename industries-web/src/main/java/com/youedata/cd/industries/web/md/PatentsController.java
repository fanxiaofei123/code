package com.youedata.cd.industries.web.md;

import com.youedata.cd.common.http.ResponseResult;
import com.youedata.cd.industries.pojo.md.Patents;
import com.youedata.cd.industries.service.md.IPatentsService;
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
@RequestMapping("/patents")
public class PatentsController {
    @Autowired
    private IPatentsService service;

    /**
     * 获取某个主体、某类型的专利详细数据
     *
     * @param id          主体ID
     * @param ipcf        行业分类
     * @param currentPage 当前第几页
     * @param limit       每页多少条
     * @param orderBy     按某一列排序
     * @param sort        顺序还是逆序排序
     * @return 列表
     */
    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ResponseEntity list(String id, String ipcf, Integer currentPage, Integer limit, String orderBy, String sort) {
        ResponseResult ret = service.listPageByIpcfAndSubject(id, ipcf, currentPage, limit, orderBy, sort);
        return new ResponseEntity(ret, HttpStatus.OK);
    }

    /**
     * 获取某个专利详细信息
     *
     * @param id    主体ID
     * @param title 专利标题
     * @return 专利详细信息
     */
    @ResponseBody
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public ResponseEntity list(String id, String title) {
        Patents ret = service.getBySubjectAndTitle(id, title);
        return new ResponseEntity(ret, HttpStatus.OK);
    }


}
