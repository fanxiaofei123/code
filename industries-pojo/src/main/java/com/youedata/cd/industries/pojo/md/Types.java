package com.youedata.cd.industries.pojo.md;

import com.youedata.cd.base.pojo.BaseDomain;
import java.io.Serializable;

public class Types extends BaseDomain implements Serializable {
    private static final long serialVersionUID = 100924107247459204L;

    /**
     * 专利类型：0发明专利，1实用新型，2外观设计
     */
    private Integer patentType;

    /**
     * IPC分类分类首字母
     */
    private String ipcf;

    /**
     * 专利从申请到公开所需平均天数
     */
    private Float consume;

    /**
     * @return 专利类型：0发明专利，1实用新型，2外观设计
     */
    public Integer getPatentType() {
        return patentType;
    }

    /**
     * @param patentType 
	 *            专利类型：0发明专利，1实用新型，2外观设计
     */
    public void setPatentType(Integer patentType) {
        this.patentType = patentType;
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
     * @return 专利从申请到公开所需平均天数
     */
    public Float getConsume() {
        return consume;
    }

    /**
     * @param consume 
	 *            专利从申请到公开所需平均天数
     */
    public void setConsume(Float consume) {
        this.consume = consume;
    }
}