package com.youedata.cd.industries.pojo.md;


public class Patents {
    private static final long serialVersionUID = 612297179878634051L;

    /**
     * 关联Subjects表id
     */
    private Integer patentsId;

    /**
     * 权利人属性：申请人/发明人/专利权人，用","隔开
     */
    private String rightHolder;

    /**
     * 标题
     */
    private String title;

    /**
     * 地址
     */
    private String address;

    /**
     * 申请日
     */
    private String filingDate;

    /**
     * 公告日
     */
    private String announcementDate;

    /**
     * 专利类型：发明专利/实用新型/外观设计
     */
    private String patentType;

    /**
     * 申请人
     */
    private String applicant;

    /**
     * 发明人
     */
    private String inventor;

    /**
     * 申请号
     */
    private String applicationNumber;

    /**
     * 公告号
     */
    private String announcementNumber;

    /**
     * 专利权人
     */
    private String holder;

    /**
     * xml类型
     */
    private String xmlType;

    /**
     * @return 关联Subjects表id
     */
    public Integer getPatentsId() {
        return patentsId;
    }

    /**
     * @param patentsId 
	 *            关联Subjects表id
     */
    public void setPatentsId(Integer patentsId) {
        this.patentsId = patentsId;
    }

    /**
     * @return 权利人属性：申请人/发明人/专利权人，用","隔开
     */
    public String getRightHolder() {
        return rightHolder;
    }

    /**
     * @param rightHolder 
	 *            权利人属性：申请人/发明人/专利权人，用","隔开
     */
    public void setRightHolder(String rightHolder) {
        this.rightHolder = rightHolder;
    }

    /**
     * @return 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title 
	 *            标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return 地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address 
	 *            地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return 申请日
     */
    public String getFilingDate() {
        return filingDate;
    }

    /**
     * @param filingDate 
	 *            申请日
     */
    public void setFilingDate(String filingDate) {
        this.filingDate = filingDate;
    }

    /**
     * @return 公告日
     */
    public String getAnnouncementDate() {
        return announcementDate;
    }

    /**
     * @param announcementDate 
	 *            公告日
     */
    public void setAnnouncementDate(String announcementDate) {
        this.announcementDate = announcementDate;
    }

    /**
     * @return 专利类型：发明专利/实用新型/外观设计
     */
    public String getPatentType() {
        return patentType;
    }

    /**
     * @param patentType 
	 *            专利类型：发明专利/实用新型/外观设计
     */
    public void setPatentType(String patentType) {
        this.patentType = patentType;
    }

    /**
     * @return 申请人
     */
    public String getApplicant() {
        return applicant;
    }

    /**
     * @param applicant 
	 *            申请人
     */
    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    /**
     * @return 发明人
     */
    public String getInventor() {
        return inventor;
    }

    /**
     * @param inventor 
	 *            发明人
     */
    public void setInventor(String inventor) {
        this.inventor = inventor;
    }

    /**
     * @return 申请号
     */
    public String getApplicationNumber() {
        return applicationNumber;
    }

    /**
     * @param applicationNumber 
	 *            申请号
     */
    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    /**
     * @return 公告号
     */
    public String getAnnouncementNumber() {
        return announcementNumber;
    }

    /**
     * @param announcementNumber 
	 *            公告号
     */
    public void setAnnouncementNumber(String announcementNumber) {
        this.announcementNumber = announcementNumber;
    }

    /**
     * @return 专利权人
     */
    public String getHolder() {
        return holder;
    }

    /**
     * @param holder 
	 *            专利权人
     */
    public void setHolder(String holder) {
        this.holder = holder;
    }

    /**
     * @return xml类型
     */
    public String getXmlType() {
        return xmlType;
    }

    /**
     * @param xmlType 
	 *            xml类型
     */
    public void setXmlType(String xmlType) {
        this.xmlType = xmlType;
    }
}