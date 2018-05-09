package com.youedata.cd.industries.service.heatmap.impl;

import com.youedata.cd.industries.cache.Cache;
import com.youedata.cd.industries.cache.ConstantDataCache;
import com.youedata.cd.industries.dao.heatmap.HeadMapDao;
import com.youedata.cd.industries.pojo.category.Category;
import com.youedata.cd.industries.pojo.enterprise.EnterpriseBase;
import com.youedata.cd.industries.pojo.heatmap.Point;
import com.youedata.cd.industries.pojo.heatmap.Version;
import com.youedata.cd.industries.pojo.street.Street;
import com.youedata.cd.industries.service.heatmap.HeatMapService;
import com.youedata.cd.industries.service.index.IndexDataRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by chenyongke on 2016/6/6.
 */
@Service
public class HeatMapServiceImpl implements HeatMapService, InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(HeatMapServiceImpl.class);
    @Autowired
    private HeadMapDao headMapDao;
    @Autowired
    private IndexDataRateService indexDataRateService;

    @Override
    public List<Version> selectByQuery() {
        Cache versionCache = ConstantDataCache.getCacheInfo("versionCache");
        if (versionCache.isExpired()) {
            try {
                afterPropertiesSet();
                versionCache = ConstantDataCache.getCacheInfo("versionCache");
            } catch (Exception e) {
                LOGGER.error("缓存更新错误", e);
            }
        }
        return (List<Version>) versionCache.getValue();
    }

    @Override
    public Version selectByQuery(String begin, String end) {
        Version version = new Version();
        HashMap<String, Object> map = new HashMap<>();
        map.put("timestampBegin", begin);
        map.put("timestampEnd", end);
        List<EnterpriseBase> enterpriseBases = headMapDao.selectSourceId(map);

        if (enterpriseBases.size() != 0) {
            List<String> sourceLogId = new ArrayList<>();
            List<Double> doubles = new ArrayList<>();
            for (EnterpriseBase enterpriseBase : enterpriseBases) {
                sourceLogId.add(enterpriseBase.getSourceLogId());
//                doubles.add(50.0);
                doubles.add(indexDataRateService.getDataByEnterpriseId(enterpriseBase.getIndustryId().toString(), enterpriseBase.getTradeId().toString(), enterpriseBase.getId().toString(), begin, end));
            }
            List<Point> point = headMapDao.selectEnterprisePoint(sourceLogId);

            for (int i = 0; i < point.size(); i++) {
                point.get(i).setCo(doubles.get(i));
                point.get(i).setCategory(enterpriseBases.get(i).getCategoryId());
                point.get(i).setCategoryName(enterpriseBases.get(i).getCategoryName());
            }

            List<Street> streets = headMapDao.selectCountByStreet(sourceLogId);
            List<Category> categories = headMapDao.selectCountByCategory(map);
            version.setCategorieList(categories);
            version.setStreetList(streets);
            version.setPoint(point);
        }
        return version;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
//        List<Version> versions = new ArrayList<>();
//        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        for (int i = 7; i >= 0 ; i--) {//选择最近8年
//            Version version = new Version();
//            version.setVersion((year-i));
//            versions.add(version);
//        }
////        Version version1 = new Version();
////        version1.setVersion((year-0));
////        versions.add(version1);
//
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
//        Date dateEnd;
//        Date dateBegin;
//        List<String> sourceIds;
//        for (Version version : versions) {
//            dateEnd = simpleDateFormat.parse(String.valueOf(version.getVersion() + 1));
//            dateBegin=simpleDateFormat.parse(String.valueOf(version.getVersion()));
//            Timestamp timestampEnd = new Timestamp(dateEnd.getTime());
//            Timestamp timestampBegin = new Timestamp(dateBegin.getTime());
//            HashMap<String, Object> map = new HashMap<>();
//            map.put("timestampBegin",timestampBegin);
//            map.put("timestampEnd",timestampEnd);
//            List<EnterpriseBase> enterpriseBases = headMapDao.selectSourceId(map);
//
//            if (enterpriseBases.size() != 0) {
//                List<String> sourceLogId=new ArrayList<>();
//                List<Double> doubles=new ArrayList<>();
//                for (EnterpriseBase enterpriseBase:enterpriseBases){
//                    sourceLogId.add(enterpriseBase.getSourceLogId());
//                    doubles.add(indexDataRateService.getDataByEnterpriseId(enterpriseBase.getIndustryId().toString(),enterpriseBase.getTradeId().toString(),enterpriseBase.getId().toString(),dateBegin.toString() , dateEnd.toString()));
//                }
//                List<Point> point = headMapDao.selectEnterprisePoint(sourceLogId);
//
//                for (int i=0;i<point.size();i++) {
//                    point.get(i).setCo(doubles.get(i));
//                    point.get(i).setCategory(enterpriseBases.get(i).getCategoryId());
//                }
//
//                List<Street> streets = headMapDao.selectCountByStreet(sourceLogId);
//                List<Category> categories = headMapDao.selectCountByCategory(map);
//                version.setCategorieList(categories);
//                version.setStreetList(streets);
//                version.setPoint(point);
//            }
//
//        }
//        ConstantDataCache.putCacheInfo("versionCache",versions,1800000);
    }
}
