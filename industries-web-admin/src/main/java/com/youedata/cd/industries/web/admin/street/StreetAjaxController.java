package com.youedata.cd.industries.web.admin.street;

import com.alibaba.fastjson.JSONObject;
import com.youedata.cd.base.common.constant.KeyWords;
import com.youedata.cd.base.common.page.PageUtil;
import com.youedata.cd.base.dao.util.dds.DBContextHolder;
import com.youedata.cd.base.msg.SysValue;
import com.youedata.cd.base.service.IBaseService;
import com.youedata.cd.base.web.controller.BaseAjaxController;
import com.youedata.cd.industries.pojo.community.Community;
import com.youedata.cd.industries.pojo.street.Street;
import com.youedata.cd.industries.query.enterprise.EnterpriseQuery;
import com.youedata.cd.industries.service.community.CommunityService;
import com.youedata.cd.industries.service.enterprise.EnterpriseService;
import com.youedata.cd.industries.service.street.StreetOriService;
import com.youedata.cd.industries.service.street.StreetService;
import com.youedata.cd.industries.web.admin.util.Address2Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cdyoue on 2016/7/12.
 */
@Controller
@RequestMapping("/admin/street")
public class StreetAjaxController extends BaseAjaxController<Street> {
    @Autowired
    private StreetOriService streetOriService;
    @Autowired
    private CommunityService communityService;
    @Autowired
    private EnterpriseService enterpriseService;

    @Override
    public IBaseService<Street> getBaseService() {
        return streetOriService;
    }

    @Autowired
    private StreetService streetService;

    @RequestMapping("findCommunity")
    public void searchCommunityByStreet(Integer id, HttpServletRequest request, HttpServletResponse response) {
        System.err.println("communityService" + communityService);
        List<Community> entities = communityService.searchCommunityByStreet(id);
        HashMap map = new HashMap();
        map.put("entities", entities);
        this.writeOkMap(request, response, map);
    }

    @Override
    @RequestMapping(value = "add")
    public void add(Street t, Map model, HttpServletRequest request,
                    HttpServletResponse response, HttpSession session) {
        response.setHeader("Cache-Control", "no-cache");
        boolean isUpdate = (t.getId() != null && t.getId() > 0);

        DBContextHolder.setDbType(DBContextHolder.DB_TYPE_W);
        Address2Position.Point point = Address2Position.findPoint(t.getName());
        Street queryStreet = new Street();
        queryStreet.setName(t.getName());
        List<Street> streets = streetService.selectByMap(queryStreet);
        if(streets != null && streets.size()>0){
            writeFailMsg(request, response, "“"+t.getName()+"”已存在！！");
            return;
        }
        if (point.getLat() != null && point.getLng() != null) {
            t.setLatitude(point.getLat());
            t.setLongitude(point.getLng());
        } else {
            writeFailMsg(request, response, "请输入正确的街道信息");
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
        Long id = (Long.valueOf(PageUtil.getLongId(request)));
        EnterpriseQuery query = new EnterpriseQuery();
        query.setStreetId(id + "");
        Integer count = enterpriseService.selectByCount(query);
        if (count != 0) {
            this.writeFailMsg(request, response, "请先删除企业");
            return;
        }
        Integer communityCount = communityService.selectByCount(query);
        if (communityCount != 0) {
            this.writeFailMsg(request, response, "请先删除社区");
            return;
        }
        long status = this.getBaseService().delete(id);
        new JSONObject();
        if (status > 0L) {
            this.writeOkMsg(request, response, SysValue.DEFAULT_SUCCESS_MSG);
        } else {
            this.writeFailMsg(request, response, SysValue.DEFAULT_FAIL_MSG);
        }

    }

}
