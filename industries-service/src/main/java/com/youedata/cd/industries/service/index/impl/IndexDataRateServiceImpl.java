package com.youedata.cd.industries.service.index.impl;

import com.google.common.collect.Lists;
import com.youedata.cd.base.common.constant.KeyWords;
import com.youedata.cd.common.constant.Constant;
import com.youedata.cd.common.util.DateUtil;
import com.youedata.cd.common.util.IndexUtil;
import com.youedata.cd.common.util.YoueStringUtils;
import com.youedata.cd.index.pojo.IndexData;
import com.youedata.cd.index.pojo.IndexDataStatisti;
import com.youedata.cd.index.pojo.IndexDefinition;
import com.youedata.cd.industries.dao.index.IndexDataDao;
import com.youedata.cd.industries.dao.index.IndexDataStatistiDao;
import com.youedata.cd.industries.dao.index.IndexDefinitionDao;
import com.youedata.cd.industries.msg.constant.IndexKeyWords;
import com.youedata.cd.industries.service.index.IndexDataRateService;
import com.youedata.cd.industries.service.index.IndexDefinitionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 2017/3/17.
 */
@Service("indexDataRateService")
public class IndexDataRateServiceImpl implements IndexDataRateService {
    private static final Logger logger = LoggerFactory.getLogger(IndexDataRateServiceImpl.class);

    @Autowired
    private IndexDataDao indexDataDao;

    @Autowired
    private IndexDataStatistiDao indexDataStatistiDao;

    @Autowired
    private IndexDefinitionService indexDefinitionService;

    @Autowired
    private IndexDefinitionDao indexDefinitionDao;
    /**
     * 封装图形参数
     * @param param
     * @return
     */
    public Map<String, Object> getIndexData(Map<String, Object> param) {

        String industryId = YoueStringUtils.nullObjToEmptyStr(param.get("industryId"));
        String enterpriseId = YoueStringUtils.nullObjToEmptyStr(param.get("enterpriseId"));
        String tradeId = YoueStringUtils.nullObjToEmptyStr(param.get("tradeId"));
        String startTime = YoueStringUtils.nullObjToEmptyStr(param.get("startTime"));
        String endTime = YoueStringUtils.nullObjToEmptyStr(param.get("endTime"));

        Map paramDb = new HashMap();
        paramDb.put("industryId", industryId);
        paramDb.put("enterpriseId", enterpriseId);
        paramDb.put("tradeId", tradeId);
        paramDb.put("startTimeCondition", IndexUtil.assembleTimeCondition(startTime));
        paramDb.put("endTimeCondition", IndexUtil.assembleTimeCondition(endTime));
        paramDb.put("startTime", startTime);
        paramDb.put("endTime", endTime);

        List dataList = indexDataStatistiDao.getDataByMap(param);
        Map returnIndexMap = new HashMap();
        if (dataList.size() > 0) {
            for(int i=0; i< dataList.size(); i++) {
                Map map2 = (Map)dataList.get(i);
                Long indexId2 = (Long)map2.get("indexId");
                Map<String, Double> indexDataMap = new TreeMap<String, Double>();
                boolean falg = false;
                for (int j=0; j< dataList.size(); j++) {
                    Map map1 = (Map)dataList.get(j);
                    Long indexId1 = (Long)map1.get("indexId");
                    if (indexId2.equals(indexId1)) {
                        BigDecimal bigDecimalData = (BigDecimal) map1.get("data");
                        indexDataMap.put((String)map1.get("quarter"), bigDecimalData.doubleValue());
                        falg = true;
                    }
                }
                if (falg) {
                    IndexDefinition indexDefinition = indexDefinitionDao.selectByPrimaryKey((Long)map2.get("indexId"));
                    returnIndexMap.put(indexDefinition.getName(), indexDataMap);
                }
            }
        }

        Map<String, Object> indexMap = new HashMap<String, Object>();
        indexMap.put(IndexKeyWords.KEY_DATAS, returnIndexMap);
        indexMap.put(KeyWords.STATUS, KeyWords.STATUS_OK);
        indexMap.put(KeyWords.MESSAGE, IndexKeyWords.MSG_QUERY_SUCCESS);
        return indexMap;
    }

    public Double getDataByEnterpriseId(String industryId, String tradeId, String enterpriseId, String startTime, String endTime) {

        IndexDataStatisti indexDataStatisti = new IndexDataStatisti();
        indexDataStatisti.setEnterpriseId(YoueStringUtils.strToLong(enterpriseId));
        indexDataStatisti.setIndustryId(YoueStringUtils.strToLong(industryId));
        indexDataStatisti.setTradeId(YoueStringUtils.strToLong(tradeId));
        indexDataStatisti.setStartTime(startTime);
        indexDataStatisti.setEndTime(endTime);

        Double dataSum = indexDataStatistiDao.selectSumByMap(indexDataStatisti);
        return dataSum;
    }

    /**
     * 重新计算总分
     */
    public void recalculateIndexData() {

        String beginTime = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        logger.info("begin recalculateIndexData................. beginTime= " + beginTime);
        Date beginDate = DateUtil.parse(beginTime, "yyyy-MM-dd HH:mm:ss");
        indexDataStatistiDao.batchDeleteIndexDataStatisti(new IndexDataStatisti());

        //计算各企业总分数
        List<IndexData>indexDatas = indexDataDao.getEnterpriseDataList(new IndexData());
        for (IndexData indexData: indexDatas) {
            try {
                List<IndexDataStatisti>oneLevelIndexDataStatistis = statistiEnterpriseAssembleIndexData(String.valueOf(indexData.getIndustryId()), String.valueOf(indexData.getTradeId()), String.valueOf(indexData.getEnterpriseId()), indexData.getStartTime(), indexData.getEndTime());
                if (oneLevelIndexDataStatistis != null && oneLevelIndexDataStatistis.size() > 0) {
                    indexDataStatistiDao.batchInsert(oneLevelIndexDataStatistis);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        //计算产业总分数
        List<IndexDataStatisti>oneLevelIndexDataStatistis = new ArrayList<IndexDataStatisti>();
        Map industryParam = new HashMap();
        industryParam.put("statisType", "");
        List<IndexDataStatisti> indexDataStatistis = indexDataStatistiDao.statisIndexDataByIndustryOrTradeId(industryParam);
        for (IndexDataStatisti indexDataStatisti: indexDataStatistis) {
            indexDataStatisti.setTradeId(null);
            indexDataStatisti.setCreatedTime(new Date());
            indexDataStatisti.setIsDeleted(false);
            oneLevelIndexDataStatistis.add(indexDataStatisti);
        }

        //计算行业总分数
        Map tradeParam = new HashMap();
        tradeParam.put("statisType", "trade");
        List<IndexDataStatisti> indexDataStatistisTrade = indexDataStatistiDao.statisIndexDataByIndustryOrTradeId(tradeParam);
        for (IndexDataStatisti indexDataStatisti: indexDataStatistisTrade) {
            indexDataStatisti.setCreatedTime(new Date());
            indexDataStatisti.setIsDeleted(false);
            oneLevelIndexDataStatistis.add(indexDataStatisti);
        }

        indexDataStatistiDao.batchInsert(oneLevelIndexDataStatistis);

        String endTimeStr = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        Date endDate = DateUtil.parse(endTimeStr, "yyyy-MM-dd HH:mm:ss");
        logger.info("end recalculateIndexData................. endTime= " + endTimeStr);

        long diff = endDate.getTime() - beginDate.getTime();
        long days = diff / (1000 * 60 * 60 * 24);

        long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
        long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
        long seconds = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / (1000);
        logger.info("计算分数共耗时："+days+"天"+hours+"小时"+minutes+"分" + seconds +"秒");
    }

    /**
     * 根据企业编号、季度，统计总分数-用于热力图展示
     * @param industryIdStr
     * @param tradeId
     * @param enterpriseId
     * @param startTime
     * @param endTime
     * @return
     */
    private List<IndexDataStatisti> statistiEnterpriseAssembleIndexData(String industryIdStr, String tradeId, String enterpriseId, String startTime, String endTime) {
        String beginTime = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        logger.info("begin statistiEnterpriseAssembleIndexData................. beginTime="+ beginTime+" industryIdStr=" +
                industryIdStr + " tradeId=" + tradeId + " enterpriseId=" +enterpriseId + " startTime=" + startTime + " endTime="+ endTime);
        Long industryId = YoueStringUtils.strToLong(industryIdStr);
        List<IndexDefinition> list = Lists.newArrayList();
        List<IndexDefinition>indexDefinitionList = indexDefinitionService.getIndexDefinitionByIndustry(industryId);
        IndexDefinition.sortList(list, indexDefinitionList, IndexDefinition.getRootId(), true);

        List<IndexDataStatisti> oneLevelIndexDataStatistis = new ArrayList<IndexDataStatisti>();

        Map<String, Map> allMap = new HashMap<String, Map>();
        //获取一级指标
        for (IndexDefinition oneLevelIndex : list) {
            if (oneLevelIndex.getLevel() == Constant.ONE_LEVEL) {
                List<IndexDefinition> oneLevelIndexList = Lists.newArrayList();
                getChildNodes(list, oneLevelIndex.getId(), oneLevelIndexList);
                Map<String, Double> oneLevelIndexMap = new TreeMap<String, Double>();

                List<Map<String, Double>> twoLevelIndexDataList = new ArrayList<Map<String, Double>>();
                for (IndexDefinition twoLevelIndex : oneLevelIndexList) {
                    if (twoLevelIndex.getLevel() == Constant.TWO_LEVEL) {
                        List<IndexDefinition> twoLevelIndexList = Lists.newArrayList();
                        getChildNodes(list, twoLevelIndex.getId(), twoLevelIndexList);
                        Map<String, Double> threeLevelIndexMap =  new HashMap<String, Double>();

                        for (IndexDefinition threeIndex : twoLevelIndexList) {
                            if (threeIndex.getLevel() == Constant.THREE_LEVEL) {
                                Map param = new HashMap();
                                param.put("indexId", threeIndex.getId());
                                param.put("industryId", industryId);
                                param.put("enterpriseId", enterpriseId);
                                param.put("tradeId", tradeId);;
                                param.put("startTime", startTime);
                                param.put("endTime", endTime);
                                List dataList = indexDataDao.getDataEnterpriseByMap(param);
                                if (dataList.size() > 0) {
                                    Map map = (Map)dataList.get(0);
                                    Double rateDouble = threeIndex.getIndexRate().getRate().doubleValue() / 100;
                                    BigDecimal bigDecimalData = (BigDecimal) map.get("data");
                                    Double rate =  rateDouble * bigDecimalData.doubleValue() * 100;
                                    if (threeLevelIndexMap.get(map.get("quarter")) != null) {
                                        Double data = threeLevelIndexMap.get(map.get("quarter"));
                                        threeLevelIndexMap.put((String)map.get("quarter"), data + rate);
                                    } else {
                                        threeLevelIndexMap.put((String)map.get("quarter"), rate);
                                    }
                                }
                            }
                        }

                        Double rateDouble = twoLevelIndex.getIndexRate().getRate().doubleValue() / 100;
                        Iterator<String> iter = threeLevelIndexMap.keySet().iterator();
                        while (iter.hasNext()) {
                            String key = iter.next();
                            Double value = threeLevelIndexMap.get(key);
                            Map map = new HashMap();
                            map.put(key, value * rateDouble);
                            twoLevelIndexDataList.add(map);
                        }
                    }
                }

                Map<String, Double> twoLevelIndexDataMap = new HashMap<String, Double>();
                for (Map<String, Double> twoLevelMap:  twoLevelIndexDataList){
                    Iterator<String> iter = twoLevelMap.keySet().iterator();
                    while (iter.hasNext()) {
                        String key = iter.next();
                        Double value = twoLevelMap.get(key);
                        if (twoLevelIndexDataMap.get(key) != null) {
                            Double data = twoLevelIndexDataMap.get(key);
                            twoLevelIndexDataMap.put(key, value + data);
                        } else {
                            twoLevelIndexDataMap.put(key, value);
                        }
                    }
                }

                Double rateDouble = oneLevelIndex.getIndexRate().getRate().doubleValue() / 100;
                Iterator<String> iter = twoLevelIndexDataMap.keySet().iterator();
                while (iter.hasNext()) {
                    String key = iter.next();
                    Double value = twoLevelIndexDataMap.get(key);
                    oneLevelIndexMap.put(key, formatDouble2(value * rateDouble));

                    IndexDataStatisti indexDataStatisti = new IndexDataStatisti();
                    indexDataStatisti.setEnterpriseId(YoueStringUtils.strToLong(enterpriseId));
                    indexDataStatisti.setIndexId(oneLevelIndex.getId());
                    indexDataStatisti.setIndustryId(industryId);
                    indexDataStatisti.setTradeId(YoueStringUtils.strToLong(tradeId));
                    indexDataStatisti.setData(new BigDecimal(formatDouble2(value * rateDouble)));
                    indexDataStatisti.setCreatedTime(new Date());
                    indexDataStatisti.setIsDeleted(false);

                    String year = key.substring(0, 4);
                    String quarters = key.substring(4, key.length());
                    //将季度转化成开始时间、结束时间
                    jointDate(indexDataStatisti, quarters, year);
                    oneLevelIndexDataStatistis.add(indexDataStatisti);
                }

                allMap.put(oneLevelIndex.getName(), oneLevelIndexMap);
            }
        }
        logger.debug(allMap.toString());
        String endTimeStr = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        logger.info("end statistiEnterpriseAssembleIndexData................. endTime="+ endTimeStr+" industryIdStr=" +
                industryIdStr + " tradeId=" + tradeId + " enterpriseId=" +enterpriseId + " startTime=" + startTime + " endTime="+ endTime);
        return oneLevelIndexDataStatistis;
    }

    public static double formatDouble2(double d) {
        return (double)Math.round(d*100)/100;
    }

    private void getChildNodes(List<IndexDefinition> indexList, long parentId, List<IndexDefinition> returnIndexList) {

        for (int i=0; i<indexList.size(); i++){
            IndexDefinition index = indexList.get(i);
            if (index != null && index.getParent() != null
                    && String.valueOf(index.getParent().getId()).equals(String.valueOf(parentId))){
                returnIndexList.add(index);
                getChildNodes(indexList, index.getId(), returnIndexList);
            }
        }
    }

    /**
     * 拼接日期
     */
    private IndexDataStatisti jointDate(IndexDataStatisti indexDataStatisti, String quarters, String annual) {
        switch (quarters) { // 根据季度设置起止时间
            case "Q1":
                indexDataStatisti.setStartTime(annual + "0101000000");
                indexDataStatisti.setEndTime(annual + "0331000000");
                break;
            case "Q2":
                indexDataStatisti.setStartTime(annual + "0401000000");
                indexDataStatisti.setEndTime(annual + "0630000000");
                break;
            case "Q3":
                indexDataStatisti.setStartTime(annual + "0701000000");
                indexDataStatisti.setEndTime(annual + "0930000000");
                break;
            case "Q4":
                indexDataStatisti.setStartTime(annual + "1001000000");
                indexDataStatisti.setEndTime(annual + "1231000000");
                break;
            default:
                return null;
        }
        return indexDataStatisti;
    }

}
