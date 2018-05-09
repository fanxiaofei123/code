package com.youedata.cd.industries.pojo.md;


import com.youedata.cd.base.pojo.BaseDomain;
import java.io.Serializable;

public class Distribution extends BaseDomain implements Serializable {
    private static final long serialVersionUID = 692917906348367001L;

    /**
     * 申请年份
     */
    private Integer year;

    /**
     * 专利状态：0申请，1授权
     */
    private Integer authorize;

    /**
     * IPC分类分类首字母
     */
    private String ipcf;

    /**
     * 专利数量
     */
    private Integer amount;

    /**
     * @return 申请年份
     */
    public Integer getYear() {
        return year;
    }

    /**
     * @param year 
	 *            申请年份
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * @return 专利状态：0申请，1授权
     */
    public Integer getAuthorize() {
        return authorize;
    }

    /**
     * @param authorize 
	 *            专利状态：0申请，1授权
     */
    public void setAuthorize(Integer authorize) {
        this.authorize = authorize;
    }

    /**
     * @return IPC分类分类首字母
     */
    public String getIpcf() {
        return ipcf;
    }

    /**
     * @param ipcf 
	 *            IPC分类分类首字母
     */
    public void setIpcf(String ipcf) {
        this.ipcf = ipcf;
    }

    /**
     * @return 专利数量
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * @param amount 
	 *            专利数量
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}