package com.youedata.cd.industries.pojo.heatmap;

/**
 * Created by chenyongke on 2016/7/22.
 */
public class Point {
    private Double lo;
    private Double la;
    //权重
    private Double co;
    //产业id
    private Long category;

    //产业名称
    private String categoryName;

    @Override
    public String toString() {
        return "Point{" +
                "lo=" + lo +
                ", la=" + la +
                '}';
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Double getLa() {
        return la;
    }

    public void setLa(Double la) {
        this.la = la;
    }

    public Double getLo() {

        return lo;
    }

    public void setLo(Double lo) {
        this.lo = lo;
    }

    public Double getCo() {
        return co;
    }

    public void setCo(Double co) {
        this.co = co;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }
}
