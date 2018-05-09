package com.youedata.cd.industries.service.md.impl;

import java.util.*;

import com.youedata.cd.common.http.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youedata.cd.base.service.impl.BaseServiceImpl;
import com.youedata.cd.industries.dao.base.ILocalBaseDao;
import com.youedata.cd.industries.dao.md.DistributionDao;
import com.youedata.cd.industries.pojo.md.Distribution;
import com.youedata.cd.industries.service.md.IDistributionService;

/**
 * Created by Administrator on 2017/10/16.
 */
@Service
//消除警告
@SuppressWarnings("all")
public class DistributionServiceImpl extends BaseServiceImpl<Distribution> implements IDistributionService {
    @Autowired
    private DistributionDao distributionDao;

    @Override
    public ILocalBaseDao<Distribution> getBaseDao() {
        return distributionDao;
    }


    @Override
    public List<Map<Object, Object>> getList() {
        return distributionDao.queryAll();
    }

   @Override
   public ResponseResult<Map<String,Object>> getIndustryList(Integer fyear,Integer pyear){
        Map<String, Object> result = new HashMap<>();
        ResponseResult<Map<String, Object>> ret = new ResponseResult<>();
        try {
            List<Map<String, Object>> industryList = distributionDao.industryAll(fyear,pyear);
            Map<String, Object> industryMap = distributionDao.industry(fyear,pyear);

            result.put("year",industryMap.get("minYear")+"-"+industryMap.get("maxYear"));

            List<Object> innerList = new ArrayList<>();
            for (Map<String,Object> map : industryList){

                Map<String,Object> innerMap = new HashMap<>();
                innerMap.put("value",map.get("sumAmount"));
                innerMap.put("name",map.get("ipcf"));
                innerList.add(innerMap);
            }

            result.put("total",industryMap.get("countYear"));
            result.put("data",innerList);
        }catch (Exception e){
            e.printStackTrace();
            ret.setMsg("数据查询失败");
            return ret;
        }
       ret.setData(result);
       ret.setCode(200);
       ret.setMsg("数据查询成功");
       return ret;
   }

    @Override
    public ResponseResult<List<Map<String,Object>>> getStatisticsList(){
        List<Map<String, Object>> result = new ArrayList<>();
        ResponseResult<List<Map<String, Object>>> ret = new ResponseResult<>();
        try{
            List<Map<String, Object>> applyList=distributionDao.apply(0);
            List<Map<String, Object>> authoriseList=distributionDao.authorise(1);
            for (Map<String, Object> map1 : applyList){
                Map<String, Object> tempMap = new HashMap<>();
                for (Map<String, Object> map2 : authoriseList) {
                    Integer year= (Integer) map1.get("year");
                    if(year.equals((Integer) map2.get("year"))){
                        tempMap.put("year", map1.get("year"));
                        List<Integer> innerList = new ArrayList<>();
                        innerList.add((Integer) map1.get("amount"));
                        innerList.add((Integer) map2.get("amount"));
                        tempMap.put("data",innerList);
                    }
                }
                result.add(tempMap);

            }
        }catch (Exception e){
            e.printStackTrace();
            ret.setMsg("数据查询失败");
            return ret;
        }

        ret.setData(result);
        ret.setCode(200);
        ret.setMsg("数据查询成功");
        return ret;
    }

    @Override
    public ResponseResult<List<Map<String, Object>>> getPatensVitality(Integer authorize) {
        List<Map<String, Object>> result = new ArrayList<>();
        ResponseResult<List<Map<String, Object>>> ret = new ResponseResult<>();
        try {
            List<Map<String, Object>> dataList = distributionDao.getPatensVitality(authorize);
            for (Map<String, Object> map : dataList) {
                Map<String, Object> tempMap = new HashMap<>();
                tempMap.put("year", map.get("year"));
                List<Map<String, Object>> tempList = new ArrayList<>();

                Map<String, Object> innerMap = new HashMap<>();
                innerMap.put("name", map.get("ipcf"));
                innerMap.put("value", map.get("amount"));
                boolean flag = true;
                for (Map<String, Object> map2 : result) {
                    if (map2.get("year").equals(map.get("year"))) {
                        List<Map<String, Object>> list = (List<Map<String, Object>>) map2.get("data");
                        list.add(innerMap);
                        flag = false;
                    }
                }
                if (flag) {
                    tempList.add(innerMap);
                    tempMap.put("data", tempList);
                    result.add(tempMap);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ret.setMsg("数据查询失败");
            return ret;
        }
        ret.setData(result);
        ret.setCode(200);
        ret.setMsg("数据查询成功");
        return ret;
    }

    @Override
    public ResponseResult<List<Map<String, Object>>> getPatensTime(String patentType) {
        ResponseResult<List<Map<String, Object>>> ret = new ResponseResult<>();
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            list = distributionDao.getPatensTime(patentType);
            for (Map<String, Object> map : list) {
                String name = (String) map.get("name");
                switch (name) {
                    case "A":
                        map.put("fullname", "A:人类生活用品");
                        break;
                    case "B":
                        map.put("fullname", "B:作业运输");
                        break;
                    case "C":
                        map.put("fullname", "C:化学冶金");
                        break;
                    case "D":
                        map.put("fullname", "D:纺织造纸");
                        break;
                    case "E":
                        map.put("fullname", "E:固定建筑物");
                        break;
                    case "F":
                        map.put("fullname", "F:机械工程；照明；加热；武器；爆破");
                        break;
                    case "G":
                        map.put("fullname", "G:物理");
                        break;
                    case "H":
                        map.put("fullname", "H:电学");
                        break;
                }
            }
          /*  Collections.sort(list, new Comparator<Map<String, Object>>() {
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    Float value1 = Float.valueOf(o1.get("value").toString());//name1是从你list里面拿出来的一个
                    Float value2 = Float.valueOf(o2.get("value").toString()); //name1是从你list里面拿出来的第二个name
                    return value1.compareTo(value2);
                }
            });*/

        } catch (Exception e) {
            e.printStackTrace();
            ret.setMsg("数据查询失败");
            return ret;
        }
        ret.setData(list);
        ret.setCode(200);
        ret.setMsg("数据查询成功");
        return ret;
    }

}
