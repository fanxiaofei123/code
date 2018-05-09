package com.youedata.cd.index.pojo;

import com.youedata.cd.index.util.TreeEntity;

import java.io.Serializable;
import java.util.List;

public class IndexDefinition extends TreeEntity<IndexDefinition> implements Serializable {
    private static final long serialVersionUID = 977451142276033017L;

    /**
     * 名称
     */
    private String name;

    /**
     * 指标父id
     */
    private Long pid;

    /**
     * 单位
     */
    private String unit;

    /**
     * 指标等级
     */
    private Integer level;

    /**
     * 数据来源
     */
    private String dataSource;

    /**
     * 描述
     */
    private String description;

    /**
     * 指标权重
     */
    private IndexRate indexRate;

    /**
     * @return 名称
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return 指标父id
     */
    public Long getPid() {
        return pid;
    }

    /**
     * @param pid
     *            指标父id
     */
    public void setPid(Long pid) {
        this.pid = pid;
    }

    /**
     * @return 单位
     */
    public String getUnit() {
        return unit;
    }

    /**
     * @param unit
     *            单位
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * @return 指标等级
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * @param level
     *            指标等级
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * @return 数据来源
     */
    public String getDataSource() {
        return dataSource;
    }

    /**
     * @param dataSource
     *            数据来源
     */
    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * @return 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public IndexDefinition getParent() {
        return parent;
    }

    @Override
    public void setParent(IndexDefinition parent) {
        this.parent = parent;
    }

    public IndexRate getIndexRate() {
        return indexRate;
    }

    public void setIndexRate(IndexRate indexRate) {
        this.indexRate = indexRate;
    }

    @Override
    public String toString() {
        return name;
    }

    public static void sortList(List<IndexDefinition> list, List<IndexDefinition> sourcelist, String parentId, boolean cascade){
        for (int i=0; i<sourcelist.size(); i++){
            IndexDefinition e = sourcelist.get(i);
            if (e != null && e.getParent() != null
                    && String.valueOf(e.getParent().getId()).equals(parentId)){
                list.add(e);
                if (cascade){
                    // 判断是否还有子节点, 有则继续获取子节点
                    for (int j=0; j<sourcelist.size(); j++){
                        IndexDefinition child = sourcelist.get(j);
                        if (child.getParent()!=null && child.getParent().getId()!=null
                                && child.getParent().getId().equals(e.getId())){
                            sortList(list, sourcelist, String.valueOf(e.getId()), true);
                            break;
                        }
                    }
                }
            }
        }
    }

    public static String getRootId(){
        return "0";
    }
}