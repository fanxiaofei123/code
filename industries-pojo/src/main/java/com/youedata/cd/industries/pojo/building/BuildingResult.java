package com.youedata.cd.industries.pojo.building;

/**
 * Created by cdyoue on 2016/6/22.
 */
public class BuildingResult {
    private Integer e;
    private Double lo;
    private Double la;

    public Integer getE() {
        return e;
    }

    public void setE(Integer e) {
        this.e = e;
    }

    public Double getLo() {
        return lo;
    }

    public void setLo(Double lo) {
        this.lo = lo;
    }

    public Double getLa() {
        return la;
    }

    public void setLa(Double la) {
        this.la = la;
    }

    @Override
    public String toString() {
        return "BuildingResult{" +
                "e=" + e +
                ", lo=" + lo +
                ", la=" + la +
                '}';
    }
}
