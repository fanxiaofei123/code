package com.youedata.cd.industries.pojo.heatmap;

import com.youedata.cd.industries.pojo.building.Building;
import com.youedata.cd.industries.pojo.category.Category;
import com.youedata.cd.industries.pojo.enterprise.EnterpriseResult;
import com.youedata.cd.industries.pojo.street.Street;

import java.util.List;

/**
 * Created by chenyongke on 2016/6/6.
 */
//热力图返回对象
public class Version  {
    private List<Building> buildingList;
    private List<Street> streetList;
    private List<Integer> enterpriseCounts;
    private List<Category> categorieList;
    private Integer version;
    private List<Point> point;

    public List<Point> getPoint() {
        return point;
    }

    public void setPoint(List<Point> point) {
        this.point = point;
    }

    public List<Building> getBuildingList() {
        return buildingList;
    }

    public void setBuildingList(List<Building> buildingList) {
        this.buildingList = buildingList;
    }

    public List<Street> getStreetList() {
        return streetList;
    }

    public void setStreetList(List<Street> streetList) {
        this.streetList = streetList;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public List<Category> getCategorieList() {
        return categorieList;
    }

    public void setCategorieList(List<Category> categorieList) {
        this.categorieList = categorieList;
    }


    public List<Integer> getEnterpriseCounts() {
        return enterpriseCounts;
    }

    public void setEnterpriseCounts(List<Integer> enterpriseCounts) {
        this.enterpriseCounts = enterpriseCounts;
    }

    @Override
    public String toString() {
        return "Version{" +
                "buildingList=" + buildingList +
                ", streetList=" + streetList +
                ", enterpriseCounts=" + enterpriseCounts +
                ", categorieList=" + categorieList +
                ", version=" + version +
                ", point=" + point +
                '}';
    }
}
