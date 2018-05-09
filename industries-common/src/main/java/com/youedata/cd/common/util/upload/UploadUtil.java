package com.youedata.cd.common.util.upload;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.util.Date;

/**
 * Created by Administrator on 2016/7/14 0014.
 */
public class UploadUtil {
    /**
     * /存入temporaryExcelDirectory目录下
     * @param file
     * @return 返回文件路径
     */
    public String upload(CommonsMultipartFile file){
        String filePath = null;
        if(!file.isEmpty()){
            InputStream in = null;
            BufferedInputStream bis=null;
            BufferedOutputStream bos=null;
            Long date = new Date().getTime();
            String fileName = date + file.getOriginalFilename();
            filePath = this.getClass().getClassLoader().getResource("/../../").getPath()+"temporaryExcelDirectory/"+ fileName;
            try {
                in = file.getInputStream();
                bos = new BufferedOutputStream(new FileOutputStream(filePath));
                bis = new BufferedInputStream(in);
                int n = 0;
                byte[] b = new byte[1024];
                while ((n = bis.read(b)) != -1) {
                    bos.write(b, 0, n);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    bis.close();
                    bos.close();
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return filePath;
    }
}
