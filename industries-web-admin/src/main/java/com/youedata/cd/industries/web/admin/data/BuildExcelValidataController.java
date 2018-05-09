package com.youedata.cd.industries.web.admin.data;

import com.youedata.cd.common.http.ResponseResult;
import com.youedata.cd.common.util.ExcelUtil;
import com.youedata.cd.common.util.upload.UploadUtil;
import com.youedata.cd.industries.dto.excel.BuildingDataUpdatesDto;
import com.youedata.cd.industries.pojo.log.UploadLog;
import com.youedata.cd.industries.service.excel.FailedBuildingService;
import com.youedata.cd.industries.service.excel.UploadLogService;
import com.youedata.cd.industries.service.excel.VerifyBuildingExcelService;
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

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Administrator on 2016/7/18 0018.
 */
@Controller
@RequestMapping("buildingValidata")
public class BuildExcelValidataController  {

    @Autowired
    private VerifyBuildingExcelService verifyBuildingExcelService;

    @Autowired
    private UploadLogService uploadLogService;

    @ResponseBody
    @RequestMapping(value = "validataButton", method = RequestMethod.POST)
    public AsyncResultSample validataEnterprise(HttpSession session, @RequestParam("file") CommonsMultipartFile file) {
        AsyncResultSample asyncResultSample = new AsyncResultSample();
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1,
                fileName.length());
        if (!("xls".equals(suffix)) && !("xlsx".equals(suffix))) {
            asyncResultSample.setStatus(800);
            asyncResultSample.setMessage(suffix);
            return asyncResultSample;
        }

        //文件上传到临时文件夹
        UploadUtil uploadUtil = new UploadUtil();
        String filePath = uploadUtil.upload(file);//得到路径

        List<BuildingDataUpdatesDto> list = null;
        ExcelUtil<BuildingDataUpdatesDto> excelUtil = null;
        if (filePath != null) {
            excelUtil = new ExcelUtil(BuildingDataUpdatesDto.class);
            list = excelUtil.importExcel(null, filePath, null,5);
        }

        String msg = verifyBuildingExcelService.verifyExcel(session, excelUtil, list, suffix);
        asyncResultSample.setMessage(msg);

        return asyncResultSample;
    }

    /**
     * lj 修改
     * @param session
     * @param response
     * @throws IOException
     */
    @RequestMapping("downloadFailData")//下载失败的楼宇
    @ResponseBody
    public void download(HttpSession session,HttpServletResponse response) throws IOException {
        Object o = session.getAttribute("failBuildExcel");
        if (o != null) {
            String sessionFileName = o.toString();
            String filePath = this.getClass().getClassLoader().getResource("/../../").getPath()+"temporaryExcelDirectory/"+ sessionFileName;
            File file=new File(filePath);
            String fileName=new String(sessionFileName.getBytes("UTF-8"),"iso-8859-1");//为了解决中文名称乱码问题
            response.setContentType("APPLICATION/OCTET-STREAM");
            response.setHeader("Content-Disposition", "attachment; filename="+ URLEncoder.encode(fileName, "UTF-8"));
            byte[] bytes = FileUtils.readFileToByteArray(file);

            response.getOutputStream().write(bytes);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        }
    }

    @ResponseBody
    @RequestMapping(value = "listLogs", method = RequestMethod.GET)
    public ResponseResult listLogs() {
        UploadLog uploadLog = new UploadLog();
        uploadLog.setType(2);//只查楼宇
        List<UploadLog> list = uploadLogService.select(uploadLog);
        ResponseResult responseResult = new ResponseResult();
        responseResult.setData(list);
        return responseResult;
    }

    @Autowired
    private FailedBuildingService failedBuildingService;

    @ResponseBody
    @RequestMapping(value = "downloadFailDataForLog", method = RequestMethod.GET)
    public void downloadFailDataForLog(Integer logId, HttpServletResponse response) throws IOException {
        List<BuildingDataUpdatesDto> list = failedBuildingService.selectFailedBuildingByLogId(logId);;
        ExcelUtil<BuildingDataUpdatesDto> excelUtil = new ExcelUtil(BuildingDataUpdatesDto.class);
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment; filename="+ URLEncoder.encode("error.xls", "UTF-8"));
        try {
            excelUtil.exportExcel(list, "Sheet1", list.size(), response.getOutputStream() );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
