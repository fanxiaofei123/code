package com.youedata.cd.industries.pojo.enterprise;

/**
 * Created by cdyoue on 2016/7/20.
 */
public class EnterprisebaseResult {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "EnterprisebaseResult{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
