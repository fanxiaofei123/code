package com.youedata.cd.industries.web.admin.utilTest;

import com.youedata.cd.common.util.MD5;

import com.youedata.cd.industries.dao.enterprise.EnterpriseBaseMapper;
import com.youedata.cd.industries.dao.enterprise.EnterpriseLogMapper;
import com.youedata.cd.industries.pojo.enterprise.EnterpriseBase;
import com.youedata.cd.industries.pojo.enterprise.EnterpriseLog;
import com.youedata.cd.industries.pojo.user.UserInfo;
import com.youedata.cd.industries.service.building.BuildingService;
import com.youedata.cd.industries.service.enterprise.EnterpriseBaseService;
import com.youedata.cd.industries.service.enterprise.EnterpriseService;
import com.youedata.cd.industries.service.excel.FailedBuildingService;
import com.youedata.cd.industries.service.excel.FailedChangeinfoService;
import com.youedata.cd.industries.service.excel.UploadLogService;
import com.youedata.cd.industries.service.user.UserInfoService;
import com.youedata.cd.industries.web.admin.util.Address2Position;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jws.Oneway;
import java.util.*;

/**
 * Created by chenyongke on 2016/7/12.
 */
@RunWith(SpringJUnit4ClassRunner.class) // 整合
@ContextConfiguration(locations = "classpath:spring/spring-industries-web.xml") // 加载配置
public class Address2Street {
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private EnterpriseLogMapper enterpriseLogMapper;
    @Autowired
    private EnterpriseBaseMapper enterpriseBaseMapper;
    @Autowired
    private FailedBuildingService failedBuildingService;
    @Autowired
    private EnterpriseBaseService enterpriseBaseService;
    @Autowired
    private UploadLogService uploadLogService;
    @Autowired
    private FailedChangeinfoService failedChangeinfoService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private UserInfoService userInfoService;
    @Test
    public void findAllStreet() {
        EnterpriseLog enterpriseLog = new EnterpriseLog();

        EnterpriseBase enterpriseBase = enterpriseBaseMapper.selectByRegisterNumber("110106002880591");

        enterpriseLog.setEnterpriseId(enterpriseBase.getId());
        enterpriseLog.setBuildingId(6l);
        enterpriseLog.setStreetId(11l);
        enterpriseLog.setCommunityId(2l);
        enterpriseLogMapper.updateByEnterPriseId(enterpriseLog);
    }

    @Test
    public void cancelModifyTest(){
        enterpriseService.cancelModifyInfo(841l);
    }
    @Test
    public void addressCancelModifyTest(){
        enterpriseService.addressCancelModifyInfo(6l);
    }

    @Test
    public void findAllStree2t() {

    }
    @Test
    public void userInfoAuthor() {
        UserInfo admin = userInfoService.selectByName("admin");
        System.err.println("admin:"+admin);
    }

}
