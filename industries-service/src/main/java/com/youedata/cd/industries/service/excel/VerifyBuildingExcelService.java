package com.youedata.cd.industries.service.excel;

import com.youedata.cd.common.util.ExcelUtil;
import com.youedata.cd.industries.dao.building.BuildingDao;
import com.youedata.cd.industries.dao.community.CommunityDao;
import com.youedata.cd.industries.dao.enterprise.EnterpriseDao;
import com.youedata.cd.industries.dao.enterprise.EnterpriseLogMapper;
import com.youedata.cd.industries.dao.street.StreetDao;
import com.youedata.cd.industries.dto.excel.BuildingDataUpdatesDto;
import com.youedata.cd.industries.dto.excel.EnterpriseDataUpdatesDto;
import com.youedata.cd.industries.pojo.community.Community;
import com.youedata.cd.industries.pojo.enterprise.EnterpriseLog;
import com.youedata.cd.industries.pojo.log.UploadLog;
import com.youedata.cd.industries.pojo.street.Street;
import com.youedata.cd.industries.service.util.Address2Position;
import com.youedata.cd.industries.service.util.BeanUtil2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/7/18 0018.
 */
@Service
public class VerifyBuildingExcelService {

    @Autowired
    private BuildingDao buildingDao;

    @Autowired
    private StreetDao streetDao;//街道

    @Autowired
    private CommunityDao communityDao;//社区

    @Autowired
    private EnterpriseDao enterpriseDao;

    @Autowired
    private EnterpriseLogMapper enterpriseLogMapper;

    public String verifyExcel(HttpSession session, ExcelUtil<BuildingDataUpdatesDto> excelUtil,
                            List<BuildingDataUpdatesDto> buildingDtoList,String suffix) {
        if(!verifyTableHead(buildingDtoList.get(0)))
            return "700";
        buildingDtoList.remove(0);
        if (buildingDtoList.size() > 0) {
            BeanUtil2 beanUtil = new BeanUtil2();
            int number = beanUtil.getObjNotNullNumber(buildingDtoList.get(0));
            if (number > 5) {
                return "600";
            }
        }else {
            return "600";
        }


        String message = "";
        List<BuildingDataUpdatesDto> passList = new ArrayList<>();//通过的数据。
        List<BuildingDataUpdatesDto> failList = new ArrayList<>();//未通过的数据。
        for (int i = 0; i < buildingDtoList.size(); i++) {
            BuildingDataUpdatesDto en = buildingDtoList.get(i);
            //对楼宇数据进行校验
            buildVali(en, passList, failList);
        }
        message+="success:" + passList.size() + ",fail:" + failList.size();
        //存入成功和失败的excel到临时文件
        try {
            Date now = new Date();
            //数据入库
            upload(passList,failList);

            //失败的数据进行保存，并且把路径存入session
            if (failList.size() > 0) {
                String fileName = now.getTime() + "failBuildExcel." + suffix;
                String filePath = this.getClass().getClassLoader().getResource("/../../").getPath() + "temporaryExcelDirectory/" + fileName;
                OutputStream os = new FileOutputStream(new File(filePath));
                if(suffix.equals("xls")){
                    excelUtil.exportExcel(failList, "sheet", failList.size(), os);//把失败的文件保存
                }else {
                    excelUtil.exportExcel07(failList, "sheet", failList.size(), os);
                }
                session.setAttribute("failBuildExcel", fileName);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return message;
    }

    public String customeTrim(String str){
        if (str != null) {
            return str.trim();
        }
        return null;
    }

    //验证表头
    public Boolean verifyTableHead(BuildingDataUpdatesDto b){
        Boolean flag = true;
        if(!"楼宇名称".equals(customeTrim(b.getBuildingName()))||!"地址".equals(customeTrim(b.getAddress()))||!"所在街道".equals(customeTrim(b.getStreet())))
            flag=false;
        if(!"所在社区".equals(customeTrim(b.getCommunity()))||!"企业注册号".equals(customeTrim(b.getEnRegisterNumbers())))
            flag=false;
        return flag;
    }

    public void upload(List<BuildingDataUpdatesDto> passList,List<BuildingDataUpdatesDto> failList){
        //存入日志
        UploadLog up = new UploadLog();
        up.setFailCount(failList.size());
        up.setSuccessCount(passList.size());
        up.setType(2);
        enterpriseDao.insertUploadLog(up);
        Long logId = up.getId();


        EnterpriseLog enterpriseLogParam = new EnterpriseLog();
        //验证通过数据入库
        for (BuildingDataUpdatesDto b : passList) {
            b.setLogId(logId + "");
            Address2Position.Point point=Address2Position.findPoint(b.getAddress());
            if (point.getLat() != null && point.getLng() != null) {
                b.setLatitude(point.getLat());
                b.setLongitude(point.getLng());
            }
            BuildingDataUpdatesDto buildingDataUpdatesDto = buildingDao.findByaddress(b.getAddress());
            if (buildingDataUpdatesDto == null) {//如果根据地址没有找到楼宇，则添加一条数据。
                buildingDao.insertById(b);
            }else {
                buildingDao.updateByAddress(b);
            }

            String streetId = b.getStreetId();
            String communiytId = b.getCommunityId();
            String buildingId = b.getId();
            //更新注册号
            String registerNumbers = b.getEnRegisterNumbers();
            if (registerNumbers != null && !registerNumbers.equals("")) {
                String[] registerArray = registerNumbers.split(",");
                for (String reNumber : registerArray) {
                    enterpriseLogParam.setRegisterNumber(reNumber);
                    if (buildingId == null) {
                        enterpriseLogParam.setBuildingId(Long.parseLong(buildingDataUpdatesDto.getId()));
                    }else {
                        enterpriseLogParam.setBuildingId(Long.parseLong(buildingId));
                    }
                    enterpriseLogParam.setStreetId(Long.parseLong(streetId));
                    enterpriseLogParam.setCommunityId(Long.parseLong(communiytId));
//                    enterpriseLogParam.set
                    //修改t_enterprise_log表中的build_id

                    enterpriseLogMapper.updateBuildingId(enterpriseLogParam);

                    String logId1=enterpriseLogMapper.selectEnterpriseLogId(reNumber);
                    enterpriseLogParam.setId(logId1);
                    //修改t_enterprise_base表中的source_id
                    enterpriseLogMapper.updateBaseSourceId(enterpriseLogParam);

                    //根据注册号修改 log表中对应企业的街道id和社区id
                }
            }
        }

        //验证未通过数据入库
        for (BuildingDataUpdatesDto b:failList){
            b.setLogId(logId + "");
            buildingDao.insertFailBuilding(b);
        }
    }

    private void buildVali(BuildingDataUpdatesDto build, List<BuildingDataUpdatesDto> passList, List<BuildingDataUpdatesDto> failList) {
        String message = "";
        Boolean errorFlag = false;//是否出错标志
        int index = 1;
        String address = build.getAddress();//地址
        String buildingName = build.getBuildingName();
        if (build.getBuildingName() != null && !build.getBuildingName().equals("")) {
            if (!build.getBuildingName().matches("^[\\s\\S]{1,50}$")) {//楼宇名称
                if(buildingName.length() > 50){
                    String substring = buildingName.substring(0,47);
                    build.setBuildingName(substring+"...");
                }
                errorFlag=true;
                message += index+"、楼宇名称输入有误，请重新输入。";
                index++;
            }
        }else {
            errorFlag=true;
            message += index+"、楼宇名称不能为空，请补充完整。";
            index++;
        }


        if (address != null && !address.equals("")) {//地址
            if (!address.matches("^[\\s\\S]{1,50}$")) {

                if(address.length() > 50){
                    String substring = address.substring(0, 47);
                    build.setAddress(substring+"...");
                }

                errorFlag=true;
                message += index+"、地址输入有误，请重新输入。";
                index++;
            }
        }else {
            errorFlag=true;
            message += index+"、地址不能为空，请补充完整。";
            index++;
        }


        String streetName = build.getStreet();
        Boolean streetInspectFlag = false;//街道验证是否通过，如果通过就可以验证社区，如果未通过不能验证社区
        if (streetName != null && !streetName.equals("")) {
            List<Street> streetList = streetDao.findAllStreetName();
            for (int i = 0; i < streetList.size(); i++) {
                if (streetName.trim().equals(streetList.get(i).getName().trim())) {
                    streetInspectFlag = true;
                    build.setStreetId(streetList.get(i).getId() + "");
                    break;
                }
                if (i == streetList.size() - 1) {
                    errorFlag=true;
                    message += index+"、未找到街道："+streetName+"。";
                    index++;
                }
            }
        }else {
            errorFlag=true;
            message += index+"、街道不能为空，请补充完整。";
            index++;
        }


        if (streetInspectFlag) {//社区
            String community = build.getCommunity();
            if(community!=null&&!community.equals("")){
                List<Community> communityList = communityDao.findCommunityByStreet(streetName.trim());
                for (int m = 0; m < communityList.size(); m++) {
                    if (community.trim().equals(communityList.get(m).getName().trim())) {
                        build.setCommunityId(communityList.get(m).getId() + "");
                        break;
                    }
                    if (m == communityList.size() - 1) {
                        errorFlag=true;
                        message += index+"、未找到社区："+community+"。";
                        index++;
                    }
                }
            }else {
                errorFlag=true;
                message += index+"、社区不能为空，请补充完整。";
                index++;
            }

        }

        String EnRegisterNumbers = build.getEnRegisterNumbers();
        if (EnRegisterNumbers != null && !EnRegisterNumbers.equals("")) {
            String enRegisters[] = EnRegisterNumbers.split(",");
            for (String registerNumber : enRegisters) {
                EnterpriseDataUpdatesDto enterpriseDataUpdatesDto = buildingDao.findByRegisterNumber(registerNumber);
                if (enterpriseDataUpdatesDto == null) {
                    errorFlag=true;
                    message += index+"、未找到以下企业数据-注册号："+registerNumber;
                    index++;
                }
            }
        }else {
            errorFlag=true;
            message += index+"、企业注册号不能为空，请补充完整。";
            index++;
        }

        build.setErrorColumn(null);//置空错误列。
        if(errorFlag){
            build.setErrorColumn(message);
            failList.add(build);
        }else {
            passList.add(build);
        }
    }
}
