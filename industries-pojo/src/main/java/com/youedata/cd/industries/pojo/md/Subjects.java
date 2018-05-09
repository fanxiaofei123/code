package com.youedata.cd.industries.pojo.md;


public class Subjects {
    /**
     * 关联Patents表patents_id，等价于专利主体名称
     */
    private Integer subjectsId;

    /**
     * 专利主体名称
     */
    private String subject;

    /**
     * 申请人次数
     */
    private Integer applicant;

    /**
     * 发明人次数
     */
    private Integer inventor;

    /**
     * 专利权人次数
     */
    private Integer holder;

    /**
     * IPC分类分类首字母
     */
    private String ipcf;

    /**
     * 专利状态：0申请，1授权
     */
    //private Integer authorize;

    /**
     * 专利数量排名
     */
    private Integer rank;

    /**
     * 专利数量
     */
    private Integer amount;

    /**
     * @return 关联Patents表patents_id，等价于专利主体名称
     */
    public Integer getSubjectsId() {
        return subjectsId;
    }

    /**
     * @param subjectsId 
	 *            关联Patents表patents_id，等价于专利主体名称
     */
    public void setSubjectsId(Integer subjectsId) {
        this.subjectsId = subjectsId;
    }

    /**
     * @return 专利主体名称
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject 
	 *            专利主体名称
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return 申请人次数
     */
    public Integer getApplicant() {
        return applicant;
    }

    /**
     * @param applicant 
	 *            申请人次数
     */
    public void setApplicant(Integer applicant) {
        this.applicant = applicant;
    }

    /**
     * @return 发明人次数
     */
    public Integer getInventor() {
        return inventor;
    }

    /**
     * @param inventor 
	 *            发明人次数
     */
    public void setInventor(Integer inventor) {
        this.inventor = inventor;
    }

    /**
     * @return 专利权人次数
     */
    public Integer getHolder() {
        return holder;
    }

    /**
     * @param holder 
	 *            专利权人次数
     */
    public void setHolder(Integer holder) {
        this.holder = holder;
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
     * @return 专利数量排名
     */
    public Integer getRank() {
        return rank;
    }

    /**
     * @param rank 
	 *            专利数量排名
     */
    public void setRank(Integer rank) {
        this.rank = rank;
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