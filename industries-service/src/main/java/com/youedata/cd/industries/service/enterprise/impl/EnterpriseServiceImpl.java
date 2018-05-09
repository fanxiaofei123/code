package com.youedata.cd.industries.service.enterprise.impl;

import com.youedata.cd.base.dao.IBaseDao;
import com.youedata.cd.base.service.impl.BaseServiceImpl;
import com.youedata.cd.industries.dao.base.ILocalBaseDao;
import com.youedata.cd.industries.dao.enterprise.EnterpriseDao;
import com.youedata.cd.industries.dao.enterprise.EnterpriseLogMapper;
import com.youedata.cd.industries.pojo.building.Building;
import com.youedata.cd.industries.pojo.enterprise.Enterprise;
import com.youedata.cd.industries.pojo.enterprise.EnterpriseBase;
import com.youedata.cd.industries.pojo.enterprise.EnterpriseLog;
import com.youedata.cd.industries.pojo.enterprise.EnterpriseResult;
import com.youedata.cd.industries.query.BaseQuery;
import com.youedata.cd.industries.query.enterprise.EnterpriseQuery;
import com.youedata.cd.industries.service.base.impl.LocalBaseServiceImpl;
import com.youedata.cd.industries.service.building.BuildingService;
import com.youedata.cd.industries.service.enterprise.EnterpriseBaseService;
import com.youedata.cd.industries.service.enterprise.EnterpriseService;
import org.apache.taglibs.standard.tag.common.xml.ForEachTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

import java.io.Console;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("enterpriseService")
public class EnterpriseServiceImpl extends LocalBaseServiceImpl<Enterprise> implements EnterpriseService {
    private static final Logger logger = LoggerFactory.getLogger(EnterpriseServiceImpl.class);

    @Autowired
    private EnterpriseDao enterpriseDao;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private EnterpriseBaseService enterpriseBaseService;
    @Autowired
    private EnterpriseLogMapper enterpriseLogMapper;
    @Override
    public ILocalBaseDao<Enterprise> getBaseDao() {
        return enterpriseDao;
    }
    @Override
    public Map<String, Object> selectByQuery(EnterpriseQuery query) {
        List<Enterprise> enterprises =  enterpriseDao.selectByQuery(query);
        Integer counts = enterpriseDao.selectByCount(query);
        String buildingName = query.getEnterpriseName();
        query.setEnterpriseName(null);
        query.setBuildingName(buildingName);
        Integer buildingCounts = buildingService.selectByCount(query);
        Map<String,Object> pageList = new HashMap();
        pageList.put("currentPage",query.getCurrentPage());
        pageList.put("counts", counts);
        pageList.put("enterprises", enterprises);
        pageList.put("buildingCounts", buildingCounts);
        return pageList;
    }
    /**
     * 查看地址与业务变更企业
     * @param query
     * @return
     */
    @Override
    public Map<String,Object> getChangeEnterprise(EnterpriseQuery query) {
        Map<String, Object> changeMap = new HashMap<>();
        System.err.println("query"+query.getEndTime());
        List<EnterpriseResult> changeEnterprises = enterpriseDao.getChangeEnterprise(query);
        changeMap.put("changeEnterprises", changeEnterprises);
        changeMap.put("currentPage", query.getCurrentPage());
        return changeMap;
    }

    /**
     * 企业地址与业务变更企业数
     * @param query
     * @return
     */
    @Override
    public Integer getChangeEnterpriseCounts(EnterpriseQuery query) {
        
        return enterpriseDao.getChangeEnterpriseCounts(query);
    }
    /**
     * 查看变更企业详情
     * @param query
     * @return
     */
    @Override
    public Map<String, Object> selectChangesEnterpriseById(EnterpriseQuery query) {
        Map<String, Object> pageList = new HashMap<>();
        System.err.println("query"+query.getEndTime());
        List<com.youedata.cd.industries.pojo.enterprise.EnterpriseLog> sourceLogs = new ArrayList<>();
        Enterprise enterprise = enterpriseDao.selectEnterpriseById(query);
        if(enterprise != null){
            query.setSourceLogId(enterprise.getSourceLogId());
        }
        System.err.println("enterprise"+enterprise);
        if(query.getIsModifyLog() != null){
            Map logMap = new HashMap();
            logMap.put("isOldAddress",true);
            logMap.put("enterpriseId",query.getId());
            String logId = enterpriseLogMapper.getLogId(logMap);
            EnterpriseLog enterpriseLog = enterpriseLogMapper.selectByPrimaryKey(logId);
            sourceLogs.add(enterpriseLog);
        }else{
            sourceLogs =  enterpriseLogMapper.selectChangesEnterpriseById(query);
        }
        enterprise.setSourceLogs(sourceLogs);
        pageList.put("enterprise", enterprise);
        return pageList;
    }




    @Override
    public List<Enterprise> queryByItem(String enterpriseRegistNumber, String enterpriseName, String enterpriseAddress,String dataInformation) {
        Map  map = new HashMap();
        map.put("enterpriseRegistNumber",enterpriseRegistNumber);
        map.put("enterpriseName",enterpriseName);
        map.put("enterpriseAddress",enterpriseAddress);
        map.put("dataInformation",dataInformation);
        List<Enterprise> list = null;
        list = enterpriseDao.queryByItem(map);
        return list;
    }

    @Override
    public void deleteByEnterpriseId(Long id) {
        enterpriseDao.deleteByEnterpriseId(id);
    }

    @Override
    public void delByBuildingIdAndEnterpriseId(Long enterpriseId, Long buildingId) {
        Map map = new HashMap();
        map.put("enterpriseId",enterpriseId);
        map.put("buildingId",buildingId);
        enterpriseLogMapper.delByBuildingIdAndEnterpriseId(map);
    }

    /**
     * 确认告警
     * @param enterpriseId
     * @param address
     */
    @Override
    public void modifyInfo(Long enterpriseId, String address) {
        //存入原地址到历史
        EnterpriseBase enterpriseBase = enterpriseBaseService.get(enterpriseId);
        EnterpriseLog enterpriseLog = new EnterpriseLog();
        enterpriseLog.setIsOldAddress(true);
        enterpriseLog.setId(enterpriseBase.getSourceLogId());
        //是否当前base有注册时间
        EnterpriseLog enterpriseLogBase = enterpriseLogMapper.selectByPrimaryKey(enterpriseBase.getSourceLogId());
        if(enterpriseLogBase !=null && enterpriseLogBase.getDate() == null){
            String registerTime = enterpriseBase.getRegisterTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date parseDate = null;
            try {
                parseDate = sdf.parse(registerTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            enterpriseLog.setDate(parseDate);
            enterpriseLog.setId(enterpriseBase.getSourceLogId());

        }
        enterpriseLogMapper.updateByPrimaryKeySelective(enterpriseLog);
        //得到变更的logId
        Map  parm = new HashMap<>();
        parm.put("enterpriseId", enterpriseId);
        parm.put("address",address);
        String logId =  enterpriseLogMapper.getLogId(parm);
        //确定变更
        Map baseParm = new HashMap();
        baseParm.put("baseId", enterpriseId);
        baseParm.put("isModifyLog",1);
        baseParm.put("sourceId", logId);
        enterpriseDao.updateSourceId(baseParm);
    }
    @Override
    public void cancelModifyInfo(Long id) {
        EnterpriseBase enterpriseBase = enterpriseBaseService.get(id);
        EnterpriseLog enterpriseLogBase = enterpriseLogMapper.selectByPrimaryKey(enterpriseBase.getSourceLogId());
        EnterpriseLog enterpriseLog = new EnterpriseLog();
        if(enterpriseLogBase !=null && enterpriseLogBase.getDate() == null){
            String registerTime = enterpriseBase.getRegisterTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date parseDate = null;
            try {
                parseDate = sdf.parse(registerTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            enterpriseLog.setDate(parseDate);
            enterpriseLog.setId(enterpriseBase.getSourceLogId());
            enterpriseLogMapper.updateByPrimaryKeySelective(enterpriseLog);
        }
        Map baseParm = new HashMap();
        baseParm.put("baseId", id);
        baseParm.put("isModifyLog",2);
        enterpriseDao.updateSourceId(baseParm);
    }

    /**
     * 地址栏取消确认
     * @param id
     */
    @Override
    public void addressCancelModifyInfo(Long id) {
        //查询原地址
        Map logMap = new HashMap();
        logMap.put("isOldAddress",true);
        logMap.put("enterpriseId",id);
        String logId = enterpriseLogMapper.getLogId(logMap);

        //恢复原地址
        Map baseParm = new HashMap();
        baseParm.put("baseId", id);
        baseParm.put("isModifyLog",2);
        baseParm.put("sourceId",logId );
        enterpriseDao.updateSourceId(baseParm);

        //取消标记
        EnterpriseLog enterpriseLog = new EnterpriseLog();
        enterpriseLog.setIsOldAddress(false);
        enterpriseLog.setId(logId);
        enterpriseLogMapper.updateByPrimaryKeySelective(enterpriseLog);
    }

    @Override
    public Boolean isExistCommonLog(Long enterpriseId, String address) {
        Map queryMap = new HashMap();
        queryMap.put("enterpriseId", enterpriseId);
        queryMap.put("address",address);
        List<EnterpriseLog> enterpriseLogs = enterpriseLogMapper.queryEnterpriseLog(queryMap);
        if(enterpriseLogs.size() != 0){
            for (EnterpriseLog e:enterpriseLogs){
                if(  e.getAddressModifyFlag() == 1){
                    return true;
                }
            }
        }
        return false;
    }


}