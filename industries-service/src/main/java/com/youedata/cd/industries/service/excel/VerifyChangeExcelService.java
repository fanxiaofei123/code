package com.youedata.cd.industries.service.excel;

import com.youedata.cd.common.util.ExcelUtil;
import com.youedata.cd.common.util.MyLevenshtein;
import com.youedata.cd.industries.dao.enterprise.EnterpriseDao;
import com.youedata.cd.industries.dao.enterprise.EnterpriseLogMapper;
import com.youedata.cd.industries.dto.excel.ChangeRecruitmentUpdatesDto;
import com.youedata.cd.industries.pojo.enterprise.EnterpriseLog;
import com.youedata.cd.industries.pojo.enterprise.EnterpriseResult;
import com.youedata.cd.industries.pojo.enterprise.EnterprisebaseResult;
import com.youedata.cd.industries.pojo.log.UploadLog;
import com.youedata.cd.industries.query.enterprise.EnterpriseQuery;
import com.youedata.cd.industries.service.enterprise.EnterpriseBaseService;
import com.youedata.cd.industries.service.enterprise.EnterpriseService;
import com.youedata.cd.industries.service.street.MatchByAddress;
import com.youedata.cd.industries.service.util.Address2Position;
import com.youedata.cd.industries.service.util.BeanUtil2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2016/7/18 0018.
 */
@Service
public class VerifyChangeExcelService {

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private EnterpriseDao enterpriseDao;

    @Autowired
    private EnterpriseBaseService enterpriseBaseService;

    @Autowired
    private EnterpriseLogMapper enterpriseLogMapper;
    @Autowired
    private MatchByAddress matchByAddress;

    public String verifyExcel(HttpSession session, ExcelUtil<ChangeRecruitmentUpdatesDto> excelUtil,
                              List<ChangeRecruitmentUpdatesDto> changeDtoList, String suffix) {
        String message = "";

        //验证表头
        if(!verifyTableHead(changeDtoList.get(0)))
            return "700";
        changeDtoList.remove(0);

        if (changeDtoList.size() > 0) {
            BeanUtil2 beanUtil = new BeanUtil2();
            int number = beanUtil.getObjNotNullNumber(changeDtoList.get(0));
            if (number > 3) {
                return "600";
            }
        }else {
            return "600";
        }

        List<ChangeRecruitmentUpdatesDto> passList = new ArrayList<>();//通过的数据。
        List<ChangeRecruitmentUpdatesDto> failList = new ArrayList<>();//未通过的数据。
        for (int i = 0; i < changeDtoList.size(); i++) {
            ChangeRecruitmentUpdatesDto en = changeDtoList.get(i);
            //对楼宇数据进行校验
            changeVali(en, passList, failList);
        }

        try {
            Date now = new Date();
            upload(passList, failList);
            if (failList.size() > 0) {
                String fileName = now.getTime() + "failChangeExcel." + suffix;
                String filePath = this.getClass().getClassLoader().getResource("/../../").getPath() + "temporaryExcelDirectory/" + fileName;
                OutputStream os = new FileOutputStream(new File(filePath));
                if (suffix.equals("xls")) {
                    excelUtil.exportExcel(failList, "sheet", failList.size(), os);//把失败的文件保存
                } else {
                    excelUtil.exportExcel07(failList, "sheet", failList.size(), os);
                }

                session.setAttribute("failChangeExcel", fileName);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        message += "success:" + passList.size() + ",fail:" + failList.size();
        //存入成功和失败的excel到临时文件
        return message;
    }

    public String customeTrim(String str){
        if (str != null) {
            return str.trim();
        }
        return null;
    }

    //验证表头
    public Boolean verifyTableHead(ChangeRecruitmentUpdatesDto c){
        Boolean flag = true;
        if (!"时间".equals(customeTrim(c.getDate())) || !"公司名称".equals(customeTrim(c.getName())) || !"地址".equals(customeTrim(c.getAddress())))
            flag=false;
        return flag;
    }

    public void upload(List<ChangeRecruitmentUpdatesDto> passList, List<ChangeRecruitmentUpdatesDto> failList) {

        List<EnterprisebaseResult> enterpriseList = enterpriseBaseService.selectNameAndId();
        MyLevenshtein myLevenshtein = new MyLevenshtein();

        float flagFloat = 0;
        String terminalName = "";
        String terminalId = "";

        List<ChangeRecruitmentUpdatesDto> removeDataList = new ArrayList<>();//记录没有找到匹配项的数据
        for (ChangeRecruitmentUpdatesDto recruitmentUpdatesDto : passList) {
            String passName = recruitmentUpdatesDto.getName();
            flagFloat = 0;//初始化
            terminalName = "";
            terminalId = "";
            for (EnterprisebaseResult result : enterpriseList) {
                String enName = result.getName();
                if (enName.contains("有限公司")) {//去掉有限公司和公司两个词
                    enName = enName.replace("有限公司", "");
                }
                if (enName.contains("公司")) {
                    enName = enName.replace("公司", "");
                }
                if (passName.contains("有限公司")) {//去掉有限公司和公司两个词
                    passName = passName.replace("有限公司", "");
                }
                if (passName.contains("公司")) {
                    passName = passName.replace("公司", "");
                }
                float levenshtein = myLevenshtein.levenshtein(enName, passName);//算出匹配度
                if (levenshtein > 0.8 && levenshtein > flagFloat) {
                    flagFloat = levenshtein;
                    terminalName = result.getName();
                    terminalId = result.getId() + "";
                }
            }
            if (!terminalName.equals("")) {//找到了合适的匹配项
                recruitmentUpdatesDto.setName(terminalName);
                recruitmentUpdatesDto.setEnterpriseId(terminalId);
            } else {
                recruitmentUpdatesDto.setErrorColumn("没有找到名字匹配度大于0.8的公司");
                removeDataList.add(recruitmentUpdatesDto);
//                failList.add(recruitmentUpdatesDto);//没有找到匹配项的数据放入failList集合
//                passList.remove(recruitmentUpdatesDto);//
            }
        }

        for (ChangeRecruitmentUpdatesDto r : removeDataList) {
            failList.add(r);//没有找到匹配项的数据放入failList集合
            passList.remove(r);//
        }


        //遍历passlist 把每个对象的address用逗号分开，从新组装一个list赋值给passList
        List<ChangeRecruitmentUpdatesDto> temporaryList = new ArrayList();
        for (ChangeRecruitmentUpdatesDto recruitmentUpdatesDto : passList) {
            if (recruitmentUpdatesDto.getAddress().contains(",")) {//如果地址中有逗号，拆分开组成多个对象。没有逗号，直接加入temporaryList
                String[] addressAyyay = recruitmentUpdatesDto.getAddress().split(",");//多个地址以逗号分割
                List<ChangeRecruitmentUpdatesDto> onlineDataList = new ArrayList<>();

                Boolean flag = false;//记录是否出现相同地址
                for (int i = 0; i < addressAyyay.length; i++) {
                    String address = addressAyyay[i];
                    for (int j = i+1; j < addressAyyay.length; j++) {
                        String address2=addressAyyay[j];
                        if (address.trim().equals(address2.trim())) {
                            flag = true;
                            recruitmentUpdatesDto.setErrorColumn("同一条记录中不能出现相同地址:"+recruitmentUpdatesDto.getAddress());
                            failList.add(recruitmentUpdatesDto);
                            break;
                        }
                    }
                    if(flag){//如果出现相同地址  结束循环
                        break;
                    }else {
                        ChangeRecruitmentUpdatesDto temporaryObj = new ChangeRecruitmentUpdatesDto();
                        BeanUtils.copyProperties(recruitmentUpdatesDto, temporaryObj);
                        temporaryObj.setAddress(address);
                        onlineDataList.add(temporaryObj);
                    }
                }

                if(!flag){//同一条数据中没有出现相同地址
                    Boolean result = false;//记录数据库中是否有相同地址出现 false表示没有相同数据出现
                    String sameAddress = "";
                    for (int i = 0; i < addressAyyay.length; i++) {
                        String address = addressAyyay[i];
                        result = enterpriseService.isExistCommonLog(Long.parseLong(recruitmentUpdatesDto.getEnterpriseId()), address);
                        if(result){
                            sameAddress = address;
                            break;
                        }

                    }
                    if(!result){//数据库中没有出现相同地址
                        temporaryList.addAll(onlineDataList);
                    }else {
                        recruitmentUpdatesDto.setErrorColumn("数据库中存在相同地址:"+sameAddress);
                        failList.add(recruitmentUpdatesDto);
                    }
                }
            } else {
                Boolean result = enterpriseService.isExistCommonLog(Long.parseLong(recruitmentUpdatesDto.getEnterpriseId()), recruitmentUpdatesDto.getAddress());
                if(result){
                    recruitmentUpdatesDto.setErrorColumn("数据库中存在相同地址:"+recruitmentUpdatesDto.getAddress());
                    failList.add(recruitmentUpdatesDto);
                }else {
                    temporaryList.add(recruitmentUpdatesDto);
                }
            }
        }


        passList.clear();
        passList.addAll(temporaryList);


        //地址没有查出的数据加入failList 并且移除passList
        List<ChangeRecruitmentUpdatesDto> uncheckedAddress = new ArrayList<>();
        //填充验证通过的数据入库
        for (ChangeRecruitmentUpdatesDto recruitmentUpdatesDto : passList) {
          /*  Long enterPriseId =  enterpriseLogMapper.findEnIdByEnName(recruitmentUpdatesDto.getName());
            recruitmentUpdatesDto.setEnterpriseId(enterPriseId + "");*/
            List<ChangeRecruitmentUpdatesDto> list = insertLog(recruitmentUpdatesDto.getAddress(), recruitmentUpdatesDto);
            uncheckedAddress.addAll(list);
        }
        for (ChangeRecruitmentUpdatesDto r : uncheckedAddress) {
            failList.add(r);//没有找到匹配项的数据放入failList集合
            passList.remove(r);//
        }


        //存入日志
        UploadLog up = new UploadLog();
        up.setFailCount(failList.size());
        up.setSuccessCount(passList.size());
        up.setType(3);
        enterpriseDao.insertUploadLog(up);
        Long logId = up.getId();


        if (failList.size() != 0) {//失败数据入库
            for (ChangeRecruitmentUpdatesDto changeRecruitmentUpdatesDto : failList) {
                changeRecruitmentUpdatesDto.setLogId(logId);
            }
            enterpriseLogMapper.insertFailChangeData(failList);
        }
    }


    private void changeVali(ChangeRecruitmentUpdatesDto changeEntity, List<ChangeRecruitmentUpdatesDto> passList, List<ChangeRecruitmentUpdatesDto> failList) {
        String message = "";
        Boolean errorFlag = false;//是否出错标志
        int index = 1;

        String date = changeEntity.getDate();
        if (date != null && !date.equals("")) {
            if (!date.matches("^[\\d]{4}[\\u4e00-\\u9fa5\\\\]{1}[\\d]{1,2}[\\u4e00-\\u9fa5\\-\\\\]{0,1}$")) {//变更日期
                errorFlag = true;
                message += index + "、格式输入有误，仅支持以下格式：2016年6月，请重新输入";
                index++;
            }
        } else {
            errorFlag = true;
            message += index + "、日期不能为空，请补充完整。";
            index++;
        }
        String name = changeEntity.getName();

        if (changeEntity.getName() != null && !changeEntity.getName().equals("")) {
            if (!changeEntity.getName().matches("^[\\s\\S]{1,50}$")) {//公司名称

                if(name.length() > 50){
                    String substring = name.substring(0,47);
                    changeEntity.setName(substring+"...");
                }
                errorFlag = true;
                message += index + "、公司名称输入有误，请重新输入。";
                index++;
            }
        } else {
            errorFlag = true;
            message += index + "、公司名称不能为空，请补充完整。";
            index++;
        }

        String address = changeEntity.getAddress();//地址
        if (address != null && !address.equals("")) {
            if (!address.matches("^[\\s\\S]{1,200}$")) {

                if(address.length() > 200){
                    String substring = address.substring(0,197);
                    changeEntity.setAddress(substring+"...");
                }

                errorFlag = true;
                message += index + "、地址输入有误，请重新输入。";
                index++;
            }
        } else {
            errorFlag = true;
            message += index + "、地址不能为空，请补充完整。";
            index++;
        }

        changeEntity.setErrorColumn(null);//置空错误列。
        if (errorFlag) {
            changeEntity.setErrorColumn(message);
            failList.add(changeEntity);
        } else {
            passList.add(changeEntity);
        }
    }

//    public void save(List<String> listPath){
//        ExcelUtil<EnterpriseDataUpdatesDto> excelUtil = new ExcelUtil(EnterpriseDataUpdatesDto.class);
//        List<EnterpriseDataUpdatesDto> allList = new ArrayList<>();
//        for (String path : listPath) {
//            List<EnterpriseDataUpdatesDto> temporaryList=excelUtil.importExcel(null,path,null);
//            allList.addAll(temporaryList);
//        }
//        enterpriseDao.insetEnterpriseData(allList);
//    }

    /**
     * 根据地址算出坐标进行判断是否需要进行预警
     *
     * @param address
     */
    public List<ChangeRecruitmentUpdatesDto> insertLog(String address, ChangeRecruitmentUpdatesDto recruitmentUpdatesDto) {

        EnterpriseQuery enterpriseQuery = new EnterpriseQuery();
        List<ChangeRecruitmentUpdatesDto> temporaryList = new ArrayList();//记录地址没有查询出来的数据

        //调用百度方法得到坐标
        Address2Position.Point point = Address2Position.findPoint(address);

        System.err.println("recruitmentUpdatesDto.getName()" + recruitmentUpdatesDto.getName());
        enterpriseQuery.setEnterpriseName(recruitmentUpdatesDto.getName());
        List enterprisesList = enterpriseDao.selectByQuery(enterpriseQuery);//查询
        if (enterprisesList.size() == 0) {
            return null;
        }
        EnterpriseResult e = (EnterpriseResult) enterprisesList.get(0);
        Double latitude = e.getLatitude();
        Double longitude = e.getLongitude();
        if (latitude == null) {
            latitude = 0.0;
        }
        if (longitude == null) {
            longitude = 0.0;
        }
        EnterpriseLog enterpriseLog = enterpriseDao.findByEnterpriseId("" + e.getId());
//            enterpriseLog.setEnterpriseId(e.getId());
//            enterpriseLog.setMajorBusiness(e.getMajorBusiness());
        if (enterpriseLog == null) {
            enterpriseLog = new EnterpriseLog();
            enterpriseLog.setEnterpriseId(e.getId());
        }
        enterpriseLog.setAddress(address);
        enterpriseLog.setLatitude(point.getLat());
        enterpriseLog.setLongitude(point.getLng());
        //得到街道id和社区id
        Map streetCommunityMap = matchByAddress.getStreetAndCommunityId(address);

        if (point.getLat() != null && point.getLng() != null) {
            if (Math.abs(point.getLat() - latitude) > 0.005 || Math.abs(point.getLng() - longitude) > 0.005) {//进行预警
                enterpriseLog.setChangeDate(recruitmentUpdatesDto.getDate());
                enterpriseLog.setAddressModifyFlag(Byte.parseByte("1"));
                if (enterpriseLog.getVersionId() == null) {//如果当前版本号为null，则添加为1 不为null则在原来基础上+1
                    enterpriseLog.setVersionId(1);
                } else {
                    enterpriseLog.setVersionId(enterpriseLog.getVersionId() + 1);
                }
                if (streetCommunityMap.size() == 2) {//如果长度为2 ，能拿到街道和社区
                    Long streetId = (Long) streetCommunityMap.get("streetId");
                    Long communityId = (Long) streetCommunityMap.get("communityId");
                    enterpriseLog.setStreetId(streetId);
                    enterpriseLog.setCommunityId(communityId);
                }
                if (streetCommunityMap.size() == 1) {//长度为1，只能拿到街道
                    Long streetId = (Long) streetCommunityMap.get("streetId");
                    enterpriseLog.setStreetId(streetId);
                }
                //重新生成uuid 并且更新t_enterprise_base表sourceId
                String uuid = UUID.randomUUID().toString();
                enterpriseLog.setId(uuid);
//                Map updateSourecMap = new HashMap();
//                updateSourecMap.put("sourceId", uuid);
//                updateSourecMap.put("baseId", enterpriseLog.getEnterpriseId());
//                enterpriseDao.updateSourceId(updateSourecMap);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy年MM月");
                try {
                    enterpriseLog.setDate(simpleDateFormat.parse(enterpriseLog.getChangeDate()));
                } catch (ParseException e1) {
//                        e1.printStackTrace();
                    simpleDateFormat = new SimpleDateFormat("yy-MM");
                    try {
                        enterpriseLog.setDate(simpleDateFormat.parse(enterpriseLog.getChangeDate()));
                    } catch (ParseException e2) {
                        e2.printStackTrace();
                    }
                }
                enterpriseLog.setAddressModifyFlag(Byte.parseByte("1"));
                enterpriseLogMapper.insert(enterpriseLog);
                Long enterpriseId = e.getId();
                //sign
                Map map = new HashMap();
                map.put("baseId", enterpriseId);
                map.put("isModifyLog", 0);
                enterpriseDao.updateSourceId(map);

            } else {//不预警
                enterpriseLogMapper.updateByPrimaryKey(enterpriseLog);
            }
        } else {
            temporaryList.add(recruitmentUpdatesDto);
            recruitmentUpdatesDto.setErrorColumn("未找到地址：" + address);
        }

        return temporaryList;
//        if (temporaryList.size() != 0) {//地址没有查出的数据加入failList 并且移除passList
//            for (ChangeRecruitmentUpdatesDto r : temporaryList) {
//                failList.add(recruitmentUpdatesDto);
//                passList.remove(recruitmentUpdatesDto);
//            }
//        }

    }
}
