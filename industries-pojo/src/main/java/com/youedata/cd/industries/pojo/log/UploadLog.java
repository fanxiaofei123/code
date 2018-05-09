package com.youedata.cd.industries.pojo.log;

import com.youedata.cd.base.pojo.BaseDomain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/7/19 0019.
 */
public class UploadLog  extends BaseDomain implements Serializable {
    private Long id;
    private Date date;
    private int successCount;
    private int failCount;
    //1 企业  2 楼宇 3 变更数据
    private int type;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UploadLog{" +
                "date=" + date +
                ", successCount=" + successCount +
                ", failCount=" + failCount +
                ", type=" + type +
                '}';
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getFailCount() {
        return failCount;
    }

    public void setFailCount(int failCount) {
        this.failCount = failCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
