package com.youedata.cd.industries.web.admin.data;

import com.youedata.cd.common.http.ResponseResult;
import com.youedata.cd.common.util.ExcelUtil;
import com.youedata.cd.common.util.upload.UploadUtil;
import com.youedata.cd.industries.dto.excel.EnterpriseDataUpdatesDto;
import com.youedata.cd.industries.pojo.log.UploadLog;
import com.youedata.cd.industries.service.excel.ExcelFailedEnterpriseService;
import com.youedata.cd.industries.service.excel.UploadLogService;
import com.youedata.cd.industries.service.excel.VerifyExcelService;
import com.youedata.cd.industries.service.index.IndexDataRateService;
import com.youedata.cd.industries.web.admin.util.AsyncResultSample;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/12 0012.
 */
@Controller
@RequestMapping("enterpriseValidata")
public class EnExcelValidataController {
    @Autowired
    private VerifyExcelService verifyExcelService;

    @Autowired
    private ExcelFailedEnterpriseService excelFailedEnterpriseService;

    @Autowired
    private UploadLogService uploadLogService;

    @Autowired
    private CountExcelData countExcelData;

    @Autowired
    private IndexDataRateService indexDataRateService;

    @RequestMapping(value = "validataButton", method = RequestMethod.POST)
    @ResponseBody
    public AsyncResultSample validataEnterprise(HttpSession session, HttpServletRequest request, @RequestParam("file") CommonsMultipartFile file) {
        AsyncResultSample asyncResultSample = new AsyncResultSample();
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1,
                fileName.length());
        if (!("xls".equals(suffix)) && !("xlsx".equals(suffix))) {
            asyncResultSample.setStatus(800);
            asyncResultSample.setMessage(suffix);
            return asyncResultSample;
        }
        //文件上传到临时文件夹
        UploadUtil uploadUtil = new UploadUtil();
        String filePath = uploadUtil.upload(file);//得到路径

        List<EnterpriseDataUpdatesDto> list = null;
        ExcelUtil<EnterpriseDataUpdatesDto> excelUtil = null;
        if (filePath != null) {
            excelUtil = new ExcelUtil(EnterpriseDataUpdatesDto.class);
            list = excelUtil.importExcel(null, filePath, null, 15);
        }
//        Boolean nullFlag = verifyExcelService.verifyNullValue(list);
//        if(!nullFlag || list.size() == 0){
//            asyncResultSample.setStatus(400);
//            return asyncResultSample;
//        }
        String msg = verifyExcelService.verityEnterprise(session, excelUtil, list, "." + suffix);
        asyncResultSample.setMessage(msg);
        return asyncResultSample;
    }

    @RequestMapping("downloadFailData")
    public ResponseEntity<byte[]> download(HttpSession session) throws IOException {
        Object o = session.getAttribute("failEnFile");
        if (o != null) {
            String sessionFileName = o.toString();
            String filePath = this.getClass().getClassLoader().getResource("/../../").getPath() + "temporaryExcelDirectory/" + sessionFileName;
            File file = new File(filePath);
            HttpHeaders headers = new HttpHeaders();
            String fileName = new String(sessionFileName.getBytes("UTF-8"), "iso-8859-1");//为了解决中文名称乱码问题
            headers.setContentDispositionFormData("attachment", fileName);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                    headers, HttpStatus.CREATED);
        }
        return null;
    }

    @ResponseBody
    @RequestMapping(value = "listLogs", method = RequestMethod.GET)
    public ResponseResult listLogs() {
        UploadLog uploadLog = new UploadLog();
        uploadLog.setType(1);//只查询企业
        List<UploadLog> list = uploadLogService.select(uploadLog);
        ResponseResult responseResult = new ResponseResult();
        responseResult.setData(list);
        return responseResult;
    }

    @ResponseBody
    @RequestMapping(value = "downloadFailDataForLog", method = RequestMethod.GET)
    public void downloadFailDataForLog(Integer logId, HttpServletResponse response) throws IOException {
        List<EnterpriseDataUpdatesDto> list = excelFailedEnterpriseService.selectEnterpriseDtoByLogId(logId);
        ExcelUtil<EnterpriseDataUpdatesDto> excelUtil = new ExcelUtil(EnterpriseDataUpdatesDto.class);

        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("error.xls", "UTF-8"));
        try {
            excelUtil.exportExcel(list, "Sheet1", list.size(), response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "enterpriseOperationData", method = RequestMethod.POST)
    @ResponseBody
    public AsyncResultSample enterpriseOperationData(@RequestParam("file") CommonsMultipartFile file) {
        AsyncResultSample asyncResultSample = new AsyncResultSample();
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            UploadUtil uploadUtil = new UploadUtil();// 文件上传到临时文件夹
            String filePath = uploadUtil.upload(file);// 得到路径
            if ("xls".equals(suffix)) {
                Map<String, Object> resultsMap = countExcelData.read2003Excel(filePath);
                int saveSuccessObjectNumber = (Integer) resultsMap.get("saveSuccessObjectNumber");
                int saveSuccessEnterpriseNumber = (Integer)resultsMap.get("saveSuccessEnterpriseNumber");
                String data = (String)resultsMap.get("data");
                String message = (String)resultsMap.get("message");
                asyncResultSample.setMessage(message+";"+data+";"+saveSuccessEnterpriseNumber+";"+saveSuccessObjectNumber);
            } else if ("xlsx".equals(suffix)) {
                Map<String, Object> resultsMap = countExcelData.read2007Excel(filePath);
                int saveSuccessObjectNumber = (Integer) resultsMap.get("saveSuccessObjectNumber");
                int saveSuccessEnterpriseNumber = (Integer)resultsMap.get("saveSuccessEnterpriseNumber");
                String data = (String)resultsMap.get("data");
                String message = (String)resultsMap.get("message");
                asyncResultSample.setMessage(message+";"+data+";"+saveSuccessEnterpriseNumber+";"+saveSuccessObjectNumber);
            } else {
                asyncResultSample.setStatus(800);
                asyncResultSample.setMessage(suffix);
                return asyncResultSample;
            }
        } else {
            asyncResultSample.setStatus(500);
            asyncResultSample.setMessage("上传文件为空！");
            return asyncResultSample;
        }
        // 导入成功后开启多线程执行指标计算任务
        Runnable runnable = new Runnable(){
            public void run(){
                indexDataRateService.recalculateIndexData();
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();

        return asyncResultSample;
    }
}
