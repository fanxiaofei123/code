package com.youedata.cd.industries.pojo.dataImport;

/**
 * Created by Administrator on 2016/7/15 0015.
 */
public class ResultObj {
    //合格的文件名称
    private String passFileName;
    //不合格的文件名称
    private String failFileName;

    public String getPassFileName() {
        return passFileName;
    }

    public void setPassFileName(String passFileName) {
        this.passFileName = passFileName;
    }

    public String getFailFileName() {
        return failFileName;
    }

    public void setFailFileName(String failFileName) {
        this.failFileName = failFileName;
    }
}
