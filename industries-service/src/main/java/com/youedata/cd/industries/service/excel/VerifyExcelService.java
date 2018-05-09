package com.youedata.cd.industries.service.excel;

import com.youedata.cd.common.util.ExcelUtil;
import com.youedata.cd.industries.dao.category.CategoryDao;
import com.youedata.cd.industries.dao.community.CommunityDao;
import com.youedata.cd.industries.dao.enterprise.EnterpriseDao;
import com.youedata.cd.industries.dao.street.StreetDao;
import com.youedata.cd.industries.dto.excel.EnterpriseDataUpdatesDto;
import com.youedata.cd.industries.pojo.category.CategoryCustom;
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
import java.util.UUID;

/**
 * Created by Administrator on 2016/7/15 0015.
 */
@Service
public class VerifyExcelService {


    @Autowired
    private StreetDao streetDao;//街道

    @Autowired
    private CommunityDao communityDao;//社区

    @Autowired
    private CategoryDao categoryDao;//门类行业查询

    @Autowired
    private EnterpriseDao enterpriseDao;

    public String verityEnterprise(HttpSession session, ExcelUtil excelUtil, List<EnterpriseDataUpdatesDto> list, String suffix) {
        String message = "";
        List<EnterpriseDataUpdatesDto> passList = new ArrayList<>();//通过的数据。
        List<EnterpriseDataUpdatesDto> failList = new ArrayList<>();//未通过的数据。

        if(!verifyTableHead(list.get(0)))
            return "700";
        list.remove(0);

        if (list.size() > 0) {
            BeanUtil2 beanUtil = new BeanUtil2();
            int number = beanUtil.getObjNotNullNumber(list.get(0));
            if (number > 15) {
                return "600";
            }
        }else {
            return "600";
        }

        for (int i = 0; i < list.size(); i++) {
            EnterpriseDataUpdatesDto en = list.get(i);

            //对数据进行校验
            enVali(en, passList, failList);
        }
        message += "success:" + passList.size() + ",fail:" + failList.size();
        //存入成功和失败的excel到临时文件
        try {
            //数据入库
            upload(passList, failList);

            //用session保存上传失败的文件路径
            Date now = new Date();
            if (failList.size() > 0) {
                String fileName = now.getTime() + "failEnExcel" + suffix;
                String filePath = this.getClass().getClassLoader().getResource("/../../").getPath() + "temporaryExcelDirectory/" + fileName;
                OutputStream os = new FileOutputStream(new File(filePath));
//                excelUtil.exportExcel(failList, "sheet", failList.size(), os);//把失败的文件保存
                if (suffix.equals(".xls")) {
                    excelUtil.exportExcel(failList, "sheet", failList.size(), os);//把失败的文件保存
                } else {
                    excelUtil.exportExcel07(failList, "sheet", failList.size(), os);
                }


                session.setAttribute("failEnFile", fileName);
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

    public Boolean verifyTableHead(EnterpriseDataUpdatesDto e){
        Boolean flag = true;
        if(!"注册号".equals(customeTrim(e.getRegisterNumber()))||!"企业名称".equals(customeTrim(e.getEnterpriseName()))||!"行业代码".equals(customeTrim(e.getHangyeEncode()))){
            flag = false;
        }
        if(!"产业".equals(customeTrim(e.getChanye()))||!"行业".equals(customeTrim(e.getHangye()))||!"门类".equals(customeTrim(e.getMenlei()))||!"主营业务".equals(customeTrim(e.getMainBusiness()))){
            flag = false;
        }
        if(!"从业人数".equals(customeTrim(e.getEmployeeNumber()))||!"联系电话".equals(customeTrim(e.getPhone()))||!"注册时间".equals(customeTrim(e.getRegisterDate()))){
            flag = false;
        }
        if (!"注册资金".equals(customeTrim(e.getRegisterMoneny())) || !"状态".equals(customeTrim(e.getStatus())) || !"地址".equals(customeTrim(e.getAddress()))) {
            flag = false;
        }
        if (!"所在街道".equals(customeTrim(e.getStreet())) || !"所在社区".equals(customeTrim(e.getCommunity()))) {
            flag = false;
        }
        return flag;
    }

    public Boolean verifyNullValue(List<EnterpriseDataUpdatesDto> list){
        Boolean flag = true;
        for (EnterpriseDataUpdatesDto enterpriseDataUpdatesDto : list) {
            if (enterpriseDataUpdatesDto.getRegisterNumber() == null) {
                return false;
            }
            if (enterpriseDataUpdatesDto.getEnterpriseName() == null) {
                return false;
            }
            if (enterpriseDataUpdatesDto.getHangyeEncode() == null) {
                return false;
            }
            if (enterpriseDataUpdatesDto.getChanye() == null) {
                return false;
            }
            if (enterpriseDataUpdatesDto.getHangye() == null) {
                return false;
            }
            if (enterpriseDataUpdatesDto.getMenlei() == null) {
                return false;
            }
            if (enterpriseDataUpdatesDto.getMainBusiness() == null) {
                return false;
            }
            if (enterpriseDataUpdatesDto.getAddress() == null) {
                return false;
            }
            if (enterpriseDataUpdatesDto.getStreet() == null) {
                return false;
            }
            if (enterpriseDataUpdatesDto.getCommunity() == null) {
                return false;
            }
        }
        return flag;
    }

    public List<CategoryCustom> selectTradeByIndustry(String name) {
        List<CategoryCustom> list = new ArrayList<>();
        if (name == null || "".equals(name)) {
            return list;
        } else if (name .equals("一产") ) {
            name = "第一产业";
        } else if (name .equals("二产") ) {
            name = "第二产业";
        } else if (name .equals("三产") ) {
            name = "第三产业";
        }
        Integer parentId = categoryDao.findId(name);
        if (parentId == null || "".equals(parentId)) {
            return list;
        } else {
            list = categoryDao.selectTradeById(parentId);
            return list;
        }
    }

    /**
     * 验证一条数据
     * @param en 上传excel的对象
     * @param passList 通过的集合
     * @param failList 验证失败的集合
     * @return
     */
    public void enVali(EnterpriseDataUpdatesDto en,List passList,List failList) {
        int index = 1;
        String message = "";
        Boolean errorFlag = false;//是否出错标志
        String registerNumber = en.getRegisterNumber();//注册码
        if (registerNumber != null && !registerNumber.equals("")) {
            if (!registerNumber.matches("^[0-9]{1,50}$")) {
                errorFlag=true;
                message += index+"、注册号输入有误，请重新输入。";
                index++;
            }
        }else {
            errorFlag=true;
            message += index+"、注册号不能为空，请补充完整。";
            index++;
        }


        String enterpriseName = en.getEnterpriseName();//企业名称
        if (enterpriseName != null && !enterpriseName.equals("")) {
            if (!enterpriseName.matches("^[\\s\\S]{1,50}$")) {

                if(enterpriseName.length() > 50){
                    String substring = enterpriseName.substring(0, 47);
                    en.setEnterpriseName(substring+"...");
                }

            errorFlag=true;
            message += index+"、企业名称输入有误，请重新输入。";
            index++;
            }
        }else {
            errorFlag=true;
            message += index+"、企业名称不能为空，请补充完整。";
            index++;
        }

        String hangyeEncode = en.getHangyeEncode();//行业码
        if (hangyeEncode != null && !hangyeEncode.equals("")) {
            if (!hangyeEncode.matches("^[0-9]{1,20}$")) {
                errorFlag=true;
                message += index+"、行业代码输入有误，请重新输入。";
                index++;
            }
        } else {
            errorFlag=true;
            message += index+"、行业码不能为空，请补充完整。";
            index++;
        }

        String chanye = en.getChanye();//产业
        if (chanye != null && !chanye.equals("")) {
            if (!(chanye.equals("一产") || chanye.equals("二产") || chanye.equals("三产"))) {
                errorFlag=true;
                message += index+"、产业输入有误，仅支持一产、二产、三产的选择";
                index++;
            }
            switch (chanye){
                case "一产":
                    en.setChanyeId("1");
                    break;
                case "二产":
                    en.setChanyeId("2");
                    break;
                case "三产":
                    en.setChanyeId("3");
            }
        }else {
            errorFlag=true;
            message += index+"、产业不能为空，请补充完整。";
            index++;
        }


        String hangye = en.getHangye();//行业
        Boolean hangyeInspectFlag = false;//记录行业是否通过，如果通过则验证产业，如果不通过则不用验证产业。
        if (hangye != null && !hangye.equals("")) {
            List<CategoryCustom> hangyeList = selectTradeByIndustry("第" + chanye + "业");
            for (int x = 0; x < hangyeList.size(); x++) {
                if (hangye.trim().equals(hangyeList.get(x).getName().trim())) {
                    hangyeInspectFlag = true;
                    en.setHangyeId(hangyeList.get(x).getId());
                    break;
                }
                if (x == hangyeList.size() - 1) {
                    errorFlag=true;
                    message += index+"、未找到行业："+hangye+"。";
                    index++;
                }
            }
        }else {
            errorFlag=true;
            message += index+"、行业不能为空，请补充完整。";
            index++;
        }


        if (hangyeInspectFlag) {//门类
            String menlei = en.getMenlei();
            if(menlei!=null&&!menlei.equals("")){
                List<CategoryCustom> menleiList = selectTradeByIndustry(hangye);
                for (int y = 0; y < menleiList.size(); y++) {
                    if (menlei.equals(menleiList.get(y).getName().trim())) {
                        en.setMenleiId(menleiList.get(y).getId());
                        break;
                    }
                    if (y == menleiList.size()-1) {
                        errorFlag=true;
                        message += index+"、未找到门类："+menlei+"。";
                        index++;
                    }
                }
            }else {
                errorFlag=true;
                message += index+"、门类不能为空，请补充完整。";
                index++;
            }

        }

        String mainBusiness = en.getMainBusiness();//主营业务
        if (mainBusiness != null && !mainBusiness.equals("")) {
            if (!mainBusiness.matches("[\\s\\S]{1,300}")) {
                if(mainBusiness.length() > 300){
                    String substring = mainBusiness.substring(0, 297);
                    en.setMainBusiness(substring+"...");
                }
                errorFlag=true;
                message += index+"、主营业务输入有误，请重新输入。";
                index++;
            }
        }else {
            errorFlag=true;
            message += index+"、主营业务不能为空，请补充完整。";
            index++;
        }


        String employeeNumber = en.getEmployeeNumber();//员工人数
        if (employeeNumber!=null&&!employeeNumber.equals("")&&!employeeNumber.matches("^[\\d+]{1,20}$")) {

            if(employeeNumber.length() > 20){
                String substring = employeeNumber.substring(0, 17);
                en.setEmployeeNumber(substring+"...");
            }

            errorFlag=true;
            message += index+"、从业人数输入有误，请重新输入。";
            index++;
        }

        String phone = en.getPhone();//联系电话
        if (phone != null && !phone.matches("^[0-9]{1,30}$")) {

            if(phone.length() > 30){
                String substring = phone.substring(0, 27);
                en.setPhone(substring+"...");
            }

            errorFlag = true;
            message += index + "、联系电话输入有误，请重新输入。";
            index++;
        }

        String registerDate = en.getRegisterDate();//注册时间
        if (registerDate != null && !registerDate.matches("^[0-9]{4}[\\u4e00-\\u9fa5////-]{1}[0-9]{1,2}[\\u4e00-\\u9fa5////-]{0,1}[0-9]{1,2}[\\u4e00-\\u9fa5////-]{0,1}$")) {
            errorFlag = true;
            message += index + "、注册时间格式输入有误，仅支持以下格式：2016年6月1日或2016/6/1，请重新输入。";
            index++;
        }

        String registerMoneny = en.getRegisterMoneny();//注册资金
        if (registerMoneny != null && !registerMoneny.matches("^[\\s\\S]{1,30}$")) {

            if(registerMoneny.length() > 30){
                String substring = registerMoneny.substring(0, 27);
                en.setRegisterMoneny(substring+"...");
            }

            errorFlag = true;
            message += index + "、注册资金输入有误，请重新输入。";
            index++;
        }

        String status = en.getStatus();//状态
        if (status != null && !status.matches("^[\\s\\S]{1,20}$")) {


            if(status.length() > 20){
                String substring = status.substring(0, 17);
                en.setStatus(substring+"...");
            }

            errorFlag = true;
            message += index + "、状态输入有误，请重新输入。";
            index++;
        }

        String address = en.getAddress();//地址
        if (address != null && !address.equals("")) {
            if (!address.matches("^[\\s\\S]{1,200}$")) {

                if(address.length() > 200){
                    String substring = address.substring(0, 197);
                    en.setAddress(substring+"...");
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


        String street = en.getStreet();//街道
        Boolean streetInspectFlag = false;//街道验证是否通过，如果通过就可以验证社区，如果未通过不能验证社区
        if (street != null && !street.equals("")) {
            List<Street> streetList = streetDao.findAllStreetName();
            for (int z = 0; z < streetList.size(); z++) {
                if (street.trim().equals(streetList.get(z).getName().trim())) {
                    streetInspectFlag = true;
                    en.setStreetId(streetList.get(z).getId() + "");
                    break;
                }
                if (z == streetList.size() - 1) {
                    errorFlag = true;
                    message += index + "、未找到街道：" + street+"。";
                    index++;
                }
            }
        }else {
            errorFlag=true;
            message += index+"、街道不能为空，请补充完整。";
            index++;
        }

        if (streetInspectFlag) {//社区
            String community = en.getCommunity();
            if (community != null && !community.equals("")) {
                List<Community> communityList = communityDao.findCommunityByStreet(street.trim());
                for (int m = 0; m < communityList.size(); m++) {
                    if (community.trim().equals(communityList.get(m).getName().trim())) {
                        en.setCommunityId(communityList.get(m).getId() + "");
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
        en.setErrorColumn(null);//置空错误列。
        if(errorFlag){
            en.setErrorColumn(message);
            failList.add(en);
        }else {
            passList.add(en);
        }
    }

    public void upload(List<EnterpriseDataUpdatesDto> successList,List<EnterpriseDataUpdatesDto> failList){
        //存入日志
        UploadLog up = new UploadLog();
        up.setFailCount(failList.size());
        up.setSuccessCount(successList.size());
        up.setType(1);
        enterpriseDao.insertUploadLog(up);
        Long logId = up.getId();

        for (EnterpriseDataUpdatesDto e : successList) {
            e.setLogId(logId + "");//存入日志外键
            String registerNumber = e.getRegisterNumber();
            if (registerNumber == null || registerNumber.equals("")) {
                continue;
            }
            String id = enterpriseDao.selectByRegisterNumber(registerNumber);

            EnterpriseLog enterpriseLog = new EnterpriseLog();
            enterpriseLog.setId(UUID.randomUUID().toString());
            enterpriseLog.setVersionId(1);
            enterpriseLog.setStreetId(Long.parseLong(e.getStreetId()));
            enterpriseLog.setCommunityId(Long.parseLong(e.getCommunityId()));
            enterpriseLog.setMajorBusiness(e.getMainBusiness());
            enterpriseLog.setAddress(e.getAddress());
            enterpriseLog.setAddressModifyFlag(Byte.parseByte("0"));
            enterpriseLog.setMajorBusinessModifyFlag(Byte.parseByte("0"));

            Address2Position.Point point=Address2Position.findPoint(e.getAddress());
            if (point.getLat() != null && point.getLng() != null) {
                enterpriseLog.setLatitude(point.getLat());
                enterpriseLog.setLongitude(point.getLng());
            }

            String baseSourceId = UUID.randomUUID().toString();
            if (id == null) {//如果id为null、新增一条，如果不为null，则更新该条数据内容
                e.setSourceId(baseSourceId);//
                enterpriseDao.insetEnterpriseData(e);//为t_enterprise_base添加一条数据
                enterpriseLog.setEnterpriseId(Long.parseLong(e.getId()));
                enterpriseLog.setId(baseSourceId);
                enterpriseDao.insetEnterpriseLog(enterpriseLog);//给t_enterprise_log 添加一条数据
            }else {
                e.setId(id);
                enterpriseDao.updatesEnterpriseBaseById(e);
                enterpriseLog.setEnterpriseId(Long.parseLong(id));
                enterpriseDao.updatesEnterpriseLogById(enterpriseLog);
            }
        }

        //上传失败数据入库保存
        if (failList.size() > 0) {
            for (EnterpriseDataUpdatesDto ente : failList) {
                ente.setLogId(logId + "");
            }
            enterpriseDao.insertFailList(failList);
        }
    }
}
