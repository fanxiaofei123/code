package com.youedata.cd.industries.web.admin.data;

import com.youedata.cd.common.http.ResponseResult;
import com.youedata.cd.common.util.ExcelUtil;
import com.youedata.cd.common.util.upload.UploadUtil;
import com.youedata.cd.industries.dto.excel.ChangeRecruitmentUpdatesDto;
import com.youedata.cd.industries.pojo.log.UploadLog;
import com.youedata.cd.industries.service.excel.ExcelFailedEnterpriseService;
import com.youedata.cd.industries.service.excel.UploadLogService;
import com.youedata.cd.industries.service.excel.VerifyChangeExcelService;
import com.youedata.cd.industries.web.admin.util.AsyncResultSample;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2016/7/18 0018.
 */
@Controller
@RequestMapping("changeValidata")
public class ChangeRecruitmentValidataController {

    @Autowired
    private UploadLogService uploadLogService;

    @Autowired
    private VerifyChangeExcelService verifyChangeExcelService;

    @Autowired
    private ExcelFailedEnterpriseService excelFailedEnterpriseService;

    @RequestMapping(value = "validataButton", method = RequestMethod.POST)
    @ResponseBody
    public AsyncResultSample validataBuilding(HttpSession session,@RequestParam("file") CommonsMultipartFile file) {
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

        List<ChangeRecruitmentUpdatesDto> list = null;
        ExcelUtil<ChangeRecruitmentUpdatesDto> excelUtil = null;
        if (filePath != null) {
            excelUtil = new ExcelUtil(ChangeRecruitmentUpdatesDto.class);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            list= excelUtil.importExcel(null,filePath,sdf,3);
        }
        String msg= verifyChangeExcelService.verifyExcel(session,excelUtil,list,suffix);
        asyncResultSample.setMessage(msg);
        return asyncResultSample;
    }

    @RequestMapping("downloadFailData")//下载失败的变更数据
    @ResponseBody
    public void download(HttpSession session,HttpServletResponse response) throws IOException {
        Object o = session.getAttribute("failChangeExcel");
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
        uploadLog.setType(3);//只查变更数据
        List<UploadLog> list = uploadLogService.select(uploadLog);
        ResponseResult responseResult = new ResponseResult();
        responseResult.setData(list);
        return responseResult;
    }

    @ResponseBody
    @RequestMapping(value = "downloadFailDataForLog", method = RequestMethod.GET)
    public void downloadFailDataForLog(Integer logId, HttpServletResponse response) throws IOException {
        List<ChangeRecruitmentUpdatesDto> list = excelFailedEnterpriseService.selectChangeRecruitmentUpdatesDtoByLogId(logId);
        ExcelUtil<ChangeRecruitmentUpdatesDto> excelUtil = new ExcelUtil(ChangeRecruitmentUpdatesDto.class);

        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment; filename="+ URLEncoder.encode("error.xls", "UTF-8"));
        try {
            excelUtil.exportExcel(list, "Sheet1", list.size(), response.getOutputStream() );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
