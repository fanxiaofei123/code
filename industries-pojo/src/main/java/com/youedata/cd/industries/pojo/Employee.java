package com.youedata.cd.industries.pojo;

import com.youedata.cd.base.pojo.BaseDomain;

import java.io.Serializable;

/**
 * Created by cdyoue on 2016/5/19.
 */
public class Employee extends BaseDomain  implements Serializable{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                '}';
    }
}
