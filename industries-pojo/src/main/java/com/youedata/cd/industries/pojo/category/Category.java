package com.youedata.cd.industries.pojo.category;

import com.youedata.cd.base.pojo.BaseDomain;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

public class Category extends BaseDomain implements Serializable {
    private static final long serialVersionUID = 319944542496897729L;
    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    private String enterpriseCounts;
    /**
     * ��ҵ����
     */
    private String name;

    /**
     * ����ҵid
     */
    private Integer parentId;

    /**
     * ��ҵ�ȼ���1����ҵ��2����ҵ��3�����ࣩ����ҵ������ҵ����ҵ�����������ƣ�
     */
    private Integer grade;


    /**
     * @return ��ҵ����
     */
    public String getName() {
        return name;
    }

    /**
     * @param name ��ҵ����
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return ����ҵid
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * @param parentId ����ҵid
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * @return ��ҵ�ȼ���1����ҵ��2����ҵ��3�����ࣩ����ҵ������ҵ����ҵ�����������ƣ�
     */
    public Integer getGrade() {
        return grade;
    }

    /**
     * @param grade ��ҵ�ȼ���1����ҵ��2����ҵ��3�����ࣩ����ҵ������ҵ����ҵ�����������ƣ�
     */
    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getEnterpriseCounts() {
        return enterpriseCounts;
    }

    public void setEnterpriseCounts(String enterpriseCounts) {
        this.enterpriseCounts = enterpriseCounts;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", enterpriseCounts='" + enterpriseCounts + '\'' +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                ", grade=" + grade +
                '}';
    }
}