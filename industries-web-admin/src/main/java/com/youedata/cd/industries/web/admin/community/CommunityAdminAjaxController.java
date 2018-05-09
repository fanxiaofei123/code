package com.youedata.cd.industries.web.admin.community;

import com.alibaba.fastjson.JSONObject;
import com.youedata.cd.base.common.constant.KeyWords;
import com.youedata.cd.base.common.page.PageUtil;
import com.youedata.cd.base.dao.util.dds.DBContextHolder;
import com.youedata.cd.base.msg.SysValue;
import com.youedata.cd.base.service.IBaseService;
import com.youedata.cd.base.web.controller.BaseAjaxController;
import com.youedata.cd.industries.pojo.community.Community;
import com.youedata.cd.industries.query.enterprise.EnterpriseQuery;
import com.youedata.cd.industries.service.community.CommunityService;
import com.youedata.cd.industries.service.enterprise.EnterpriseService;
import com.youedata.cd.industries.web.admin.util.Address2Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by cdyoue on 2016/7/13.
 */
@Controller
@RequestMapping("admin/community")
public class CommunityAdminAjaxController extends BaseAjaxController<Community> {
    @Autowired
    private CommunityService communityService;
    @Autowired
    private EnterpriseService enterpriseService;
    @Override
    public IBaseService<Community> getBaseService() {
        return communityService;
    }






    @Override
    @RequestMapping(value = "add")
    public void add(Community t, Map model, HttpServletRequest request,
                    HttpServletResponse response, HttpSession session) {
        response.setHeader("Cache-Control", "no-cache");
        boolean isUpdate = (t.getId() != null && t.getId() > 0);
        DBContextHolder.setDbType(DBContextHolder.DB_TYPE_W);
        Address2Position.Point point = Address2Position.findPoint(t.getName());
        if(point.getLat() != null && point.getLng() !=null){
            t.setLatitude(point.getLat());
            t.setLongitude(point.getLng());
        }else{
            writeFailMsg(request, response,"请输入正确的社区信息");
            return;
        }
        long id = getBaseService().saveOrUpdate(t);
        if (id > 0) {
            if (isUpdate) {
                model.put(KeyWords.MSG, SysValue.UPDATE_SUCCESS_MSG);
            } else {
                model.put(KeyWords.MSG, SysValue.ADD_SUCCESS_MSG);
            }
        }
        writeOkMsg(request, response, SysValue.DEFAULT_SUCCESS_MSG);
        DBContextHolder.setDbType(DBContextHolder.DB_TYPE_R);
    }









    @RequestMapping({"delete"})
    public void delete(HttpServletRequest request, HttpServletResponse response, Map model) {


        System.err.println("id:"+Long.valueOf(PageUtil.getLongId(request)));
        Long id = Long.valueOf(PageUtil.getLongId(request));


        new JSONObject();
        System.err.println("id"+id);
        EnterpriseQuery query = new EnterpriseQuery();
        query.setCommunityId(id+"");
        Integer count = enterpriseService.selectByCount(query);
        if(count != 0){
            System.err.println("count"+count);
            this.writeFailMsg(request, response,"请先删除企业");
            return;
        }
        long flag = this.getBaseService().delete(Long.valueOf(PageUtil.getLongId(request)));
        if(flag > 0L) {
            this.writeOkMsg(request, response, SysValue.DEFAULT_SUCCESS_MSG);
        } else {
            this.writeFailMsg(request, response, SysValue.DEFAULT_FAIL_MSG);
        }

    }
}
