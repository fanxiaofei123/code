package com.youedata.cd.industries.service.util;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2016/7/25 0025.
 */
public class BeanUtil2 {

    public BeanUtil2() {
    }

    public int getObjNotNullNumber(Object obj) {
        int number = 0;
        Field[] allFields = obj.getClass().getDeclaredFields();// 得到类的所有field.
        for (Field f : allFields) {
            f.setAccessible(true);
            try {
                Object s = f.get(obj);
                if (s != null) {
                    number += 1;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return number;
    }

//    public static void main(String[] args) {
//        BeanUtil beanUtil = new BeanUtil();
//        EnterpriseDataUpdatesDto enterpriseDataUpdatesDto = new EnterpriseDataUpdatesDto();
//        enterpriseDataUpdatesDto.setId("11");
//        enterpriseDataUpdatesDto.setSourceId("22");
//        System.out.println(beanUtil.getObjNotNullNumber(enterpriseDataUpdatesDto));
//    }
}
