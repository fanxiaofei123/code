package com.youedata.cd.industries.web.admin.data;

import com.youedata.cd.index.pojo.IndexData;
import com.youedata.cd.index.pojo.IndexDefinition;
import com.youedata.cd.industries.pojo.enterprise.EnterpriseBase;
import com.youedata.cd.industries.service.enterprise.EnterpriseBaseService;
import com.youedata.cd.industries.service.index.IndexDataService;
import com.youedata.cd.industries.service.index.IndexDefinitionService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 使用POI读取Excel表中的数据，并根据公式计算封装成数据库存入对象,并存入数据库
 */
@Service("countExcelData")
public class CountExcelData {
    @Autowired
    private EnterpriseBaseService enterpriseBaseService;
    @Autowired
    private IndexDefinitionService indexDefinitionService;
    @Autowired
    private IndexDataService indexDataService;

    private String globalDateFormat = "yyyy-MM-dd HH:mm:ss"; // 统一日期格式
    private final int startGetValueColumn = 4; // 开始获取数据的列
    private List<String> positiveNormalizedNameList = new ArrayList<String>(); // 正向归一字段名集合
    private List<String> negativeNormalizedNameList = new ArrayList<String>(); // 负向归一字段名集合
    private Map<Long, String> noNeedNormalizedMap = new HashMap<Long, String>(); // 不需要归一化的数据
    private Map<Long, Map<String, BigDecimal>> totalMaxAndMinMap; // 指标总的最大值和最小值
    private Map<Long, Map<String, BigDecimal>> databaseMmaxAndMinMap; // 数据库的最大值和最小值
    private Map<Long, BigDecimal> MaxOrMinChangeMap; // 最大或最小值变化的列指标定义ID集合

    // 计算前初始化的数据
    public void countBeforeInit() {
        totalMaxAndMinMap = new HashMap<Long, Map<String, BigDecimal>>();
        databaseMmaxAndMinMap = new HashMap<Long, Map<String, BigDecimal>>();
        MaxOrMinChangeMap = new HashMap<Long, BigDecimal>();

        // 正向归一字段名集合
        positiveNormalizedNameList.add("实体环境面积");
        positiveNormalizedNameList.add("实体环境价格");
        positiveNormalizedNameList.add("注册资本");
        positiveNormalizedNameList.add("融资规模");
        positiveNormalizedNameList.add("园区优惠政策");
        positiveNormalizedNameList.add("税收减免");
        positiveNormalizedNameList.add("管理层学历");
        positiveNormalizedNameList.add("管理层年龄结构");
        positiveNormalizedNameList.add("员工学历");
        positiveNormalizedNameList.add("员工工作年限");
        positiveNormalizedNameList.add("专利持有数");
        positiveNormalizedNameList.add("正在研发项目数");
        positiveNormalizedNameList.add("研发投入");
        positiveNormalizedNameList.add("科研人员数占比");
        positiveNormalizedNameList.add("科研人员工作年限");
        positiveNormalizedNameList.add("去年流动比率");
        positiveNormalizedNameList.add("前年流动比率");
        positiveNormalizedNameList.add("上前年流动比率");
        positiveNormalizedNameList.add("去年资产收益率");
        positiveNormalizedNameList.add("前年资产收益率");
        positiveNormalizedNameList.add("上前年资产收益率");
        positiveNormalizedNameList.add("去年主营业务利润率");
        positiveNormalizedNameList.add("前年主营业务利润率");
        positiveNormalizedNameList.add("上前年主营业务利润率");
        positiveNormalizedNameList.add("去年主营业务增长率");
        positiveNormalizedNameList.add("前年主营业务增长率");
        positiveNormalizedNameList.add("上前年主营业务增长率");
        positiveNormalizedNameList.add("去年利润增长率");
        positiveNormalizedNameList.add("前年利润增长率");
        positiveNormalizedNameList.add("上前年利润增长率");
        positiveNormalizedNameList.add("今年资产收益率预测");
        positiveNormalizedNameList.add("明年资产收益率预测");
        positiveNormalizedNameList.add("后年资产收益率预测");
        positiveNormalizedNameList.add("今年主营业务利润率预测");
        positiveNormalizedNameList.add("明年主营业务利润率预测");
        positiveNormalizedNameList.add("后年主营业务利润率预测");
        positiveNormalizedNameList.add("今年主营业务增长率预测");
        positiveNormalizedNameList.add("明年主营业务增长率预测");
        positiveNormalizedNameList.add("后年主营业务增长率预测");
        positiveNormalizedNameList.add("今年利润增长率预测");
        positiveNormalizedNameList.add("明年利润增长率预测");
        positiveNormalizedNameList.add("后年利润增长率预测");
        positiveNormalizedNameList.add("招聘人数");
        // 负向归一字段名集合
        negativeNormalizedNameList.add("园区内同行业企业占比");
        negativeNormalizedNameList.add("去年资产负债率");
        negativeNormalizedNameList.add("前年资产负债率");
        negativeNormalizedNameList.add("上前年资产负债率");

        // 不需要归一化的字段名
        List<String> noNeedNormalizedNameList = new ArrayList<String>();
        noNeedNormalizedNameList.add("实体环境位置");
        noNeedNormalizedNameList.add("实体环境等级");
        noNeedNormalizedNameList.add("注册时间");
        noNeedNormalizedNameList.add("行业规模");
        noNeedNormalizedNameList.add("融资渠道");
        noNeedNormalizedNameList.add("技术来源渠道");
        noNeedNormalizedNameList.add("技术水平");
        noNeedNormalizedNameList.add("科研合作");
        for (int i = 0; i < noNeedNormalizedNameList.size(); i++) {
            List<IndexDefinition> indexDef = getIndexDefinitionByName(noNeedNormalizedNameList.get(i));// 查询指标定义
            if (indexDef != null && indexDef.size() > 0) {
                noNeedNormalizedMap.put(indexDef.get(0).getId(), indexDef.get(0).getName());
            }
        }
    }

    /**
     * 读取 Excel 2003 版本
     */
    public Map<String, Object> read2003Excel(String file) {
        countBeforeInit();
        Map<String, Object> returnMap = new HashMap<String, Object>();
        int saveSuccessObjectNumber = 0; // 保存成功的数据库对象数量
        int saveSuccessEnterpriseNumber = 0; // 保存成功的企业数量
        try {
            HSSFWorkbook hwb = new HSSFWorkbook(new FileInputStream(file)); // 创建Excel工作薄
            HSSFSheet sheet = hwb.getSheetAt(0); // 获取第一张工作表
            HSSFRow firstRow = sheet.getRow(1); // 第一行（列名，0列是备注）
            HSSFRow row = sheet.getRow(2); // 工作表的数据

            IndexData indexData = new IndexData(); // 指数对象
            String annual = getCellValue(row.getCell(2));
            String quarter = getCellValue(row.getCell(3));
            indexData = jointDate(indexData, Integer.parseInt(quarter.substring(0, quarter.indexOf("."))), annual.substring(0, annual.indexOf("."))); // 设置时间
            if (indexData == null) {
                returnMap.put("saveSuccessObjectNumber", saveSuccessObjectNumber);
                returnMap.put("saveSuccessEnterpriseNumber", saveSuccessEnterpriseNumber);
                returnMap.put("data", getCellValue(row.getCell(0)));
                returnMap.put("message", "季度值“" + quarter + "”输入错误，请核对“" + getCellValue(row.getCell(0)) + "”的季度值！");
                return returnMap;
            }
            getMaxAndMinMap(indexData);// 获取数据库中最大最小值(一张表只获取一次)

            Map<String, String> setMaxAndMinMessage = get2003TableColumnMaxAndMin(sheet); // 获取表中的最大最小值
            if ("error".equals(setMaxAndMinMessage.equals("state"))) {
                returnMap.put("saveSuccessObjectNumber", 0);
                returnMap.put("saveSuccessEnterpriseNumber", 0);
                returnMap.put("data", "");
                returnMap.put("message", setMaxAndMinMessage.get("message"));
                return returnMap;
            }

            int firstLineCellNumber = firstRow.getPhysicalNumberOfCells(); // 获取第一行单元格数量(有多少列)
            for (int j = 2; j < sheet.getPhysicalNumberOfRows(); j++) { // 遍历该表所有的数据行
                row = sheet.getRow(j); // 获取一行数据
                String enterpriseName = getCellValue(row.getCell(0));
                String registerNumber = getCellValue(row.getCell(1));
                EnterpriseBase enterpriseBase = enterpriseBaseService.selectByRegisterNumber(registerNumber);
                if (enterpriseBase == null) {
                    returnMap.put("saveSuccessObjectNumber", saveSuccessObjectNumber);
                    returnMap.put("saveSuccessEnterpriseNumber", saveSuccessEnterpriseNumber);
                    returnMap.put("data", enterpriseName);
                    returnMap.put("message", "企业“" + enterpriseName + "”不存在，请上传企业信息或检查“注册号”是否填写正确！");
                    return returnMap;
                }
                indexData.setEnterpriseId(enterpriseBase.getId()); // 设置企业id
                indexData.setIndustryId(enterpriseBase.getIndustryId()); // 设置产业id
                indexData.setTradeId(enterpriseBase.getTradeId()); // 设置行业id
                for (int i = startGetValueColumn; i < firstLineCellNumber; i++) {
                    String fifthColumnHeaderValue = getCellValue(firstRow.getCell(i)).trim();
                    String cellValue = getCellValue(row.getCell(i)); // Excel表中原始值
                    BigDecimal rawData;

                    List<IndexDefinition> fifthIndexDefinition = getIndexDefinitionByName(fifthColumnHeaderValue);// 查询指标定义
                    // 原始值归一化计算
                    if (fifthIndexDefinition != null && fifthIndexDefinition.size() > 0) {
                        indexData.setIndexId(fifthIndexDefinition.get(0).getId()); // 设置指标定义

                        if ("null".equals(cellValue)) { // 如果表格中没有填写数据就查询数据库上一季度的数据进行填充
                            // 封装查询条件
                            IndexData newIndexData = new IndexData();
                            newIndexData.setIndexId(indexData.getIndexId());
                            newIndexData.setEnterpriseId(enterpriseBase.getId());
                            newIndexData.setIndustryId(enterpriseBase.getIndustryId());
                            newIndexData.setTradeId(enterpriseBase.getTradeId());

                            List<IndexData> indexDataList = indexDataService.getByStartTimeDesc(newIndexData);
                            if (indexDataList != null && indexDataList.size() > 0) {
                                rawData = indexDataList.get(0).getRawData();
                            } else {
                                rawData = new BigDecimal(0); // 历史季度和表格中都没有就设置为0
                            }
                        } else {
                            rawData = new BigDecimal(cellValue);
                        }
//                        indexData.setRawData(rawData); // 设置原始值
                        BigDecimal data;
                        if (positiveNormalizedNameList.contains(fifthColumnHeaderValue)) {
                            data = normalized(rawData, fifthColumnHeaderValue, "positive");
                        } else if (negativeNormalizedNameList.contains(fifthColumnHeaderValue)) {
                            data = normalized(rawData, fifthColumnHeaderValue, "negative");
                        } else {
                            data = rawData;
                        }
                        long isSuccessful;
                        // 查询数据需要剔除掉id、data、rawData、createdTime、updateTime等条件
                        indexData.setId(null);
//                        BigDecimal data = indexData.getData();
                        indexData.setData(null);
                        indexData.setRawData(null);
                        indexData.setCreatedTime(null);
                        indexData.setUpdateTime(null);
                        List<IndexData> indexDataList = indexDataService.getByMap(indexData);
                        if (indexDataList == null || indexDataList.size() == 0) {  // 保存数据
                            indexData.setData(data);
                            indexData.setRawData(rawData);
                            isSuccessful = indexDataService.save(indexData);
                            if (isSuccessful > 0) {
                                saveSuccessObjectNumber++;
                            }
                        } else {
                            indexData.setId(indexDataList.get(0).getId());
                            indexData.setData(data);
                            indexData.setRawData(rawData);
                            indexData.setCreatedTime((indexDataList.get(0).getCreatedTime()));
                            indexData.setUpdateTime(new Date());
                            isSuccessful = indexDataService.update(indexData);
                            if (isSuccessful > 0) {
                                saveSuccessObjectNumber++;
                            }
                        }
                        if (isSuccessful == 0) {
                            returnMap.put("saveSuccessObjectNumber", saveSuccessObjectNumber);
                            returnMap.put("saveSuccessEnterpriseNumber", saveSuccessEnterpriseNumber);
                            returnMap.put("data", enterpriseName);
                            returnMap.put("message", "此列“" + fifthColumnHeaderValue + "”数据保存失败，请检查模板格式或下载新模板，并按照“填写注意事项”进行修改，再上传！");
                            return returnMap;
                        }
                    } else {
                        returnMap.put("saveSuccessObjectNumber", saveSuccessObjectNumber);
                        returnMap.put("saveSuccessEnterpriseNumber", saveSuccessEnterpriseNumber);
                        returnMap.put("data", enterpriseName);
                        returnMap.put("message", "此列“" + fifthColumnHeaderValue + "”不在定义表中，请添加指标定义或模板列名错误，下载模板，并按照“填写注意事项”进行修改。列名禁止修改！");
                        return returnMap;
                    }
                }
                saveSuccessEnterpriseNumber++;
            }
            // 如果导入的数据表中有最大或最小值（会影响某列归一结果的时候），执行此代码修改某季度某列的全部值
            if (databaseMmaxAndMinMap != null && databaseMmaxAndMinMap.size() > 0 && MaxOrMinChangeMap != null && MaxOrMinChangeMap.size() > 0) {
                Iterator<Map.Entry<Long, BigDecimal>> iterator = MaxOrMinChangeMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Long id = iterator.next().getKey();
                    Map<String, BigDecimal> maxAndMin = totalMaxAndMinMap.get(id);
                    IndexData indexDataGetMaxAndMin = new IndexData();
                    indexDataGetMaxAndMin.setStartTime(indexData.getStartTime());
                    indexDataGetMaxAndMin.setEndTime(indexData.getEndTime());
                    indexDataGetMaxAndMin.setIndexId(id);
                    List<IndexData> indexDataList = indexDataService.getByMap(indexDataGetMaxAndMin); // 获取同一个季度同一个指标的所有值进行重新计算
                    for (int i = 0; i < indexDataList.size(); i++) {
                        IndexData inData = indexDataList.get(i);
                        BigDecimal rawData = inData.getRawData();

                        IndexDefinition indexDefinition = new IndexDefinition();
                        indexDefinition.setId(id);
                        IndexDefinition newIndexDefinition = indexDefinitionService.get(indexDefinition);
                        String name = newIndexDefinition.getName().trim();

                        BigDecimal max = maxAndMin.get("max");
                        BigDecimal min = maxAndMin.get("min");
                        BigDecimal numerator;
                        BigDecimal denominator;
                        inData.setUpdateTime(new Date());
                        if (positiveNormalizedNameList.contains(name)) { // 正向归一
                            numerator = rawData.subtract(min);
                            denominator = max.subtract(min);
                            inData.setData(numerator.divide(denominator, 2, RoundingMode.HALF_UP));
                            indexDataService.update(inData);
                        } else if (negativeNormalizedNameList.contains(name)) { // 负向归一
                            numerator = max.subtract(rawData);
                            denominator = max.subtract(min);
                            inData.setData(numerator.divide(denominator, 2, RoundingMode.HALF_UP));
                            indexDataService.update(inData);
                        } else {
                            inData.setData(rawData);
                            indexDataService.update(inData);
                        }
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        returnMap.put("saveSuccessObjectNumber", saveSuccessObjectNumber);
        returnMap.put("saveSuccessEnterpriseNumber", saveSuccessEnterpriseNumber);
        return returnMap;
    }

    /**
     * 读取 Excel 2007 以上版本
     */
    public Map<String, Object> read2007Excel(String file) {
        countBeforeInit();
        Map<String, Object> returnMap = new HashMap<String, Object>();
        int saveSuccessObjectNumber = 0; // 保存成功的数据库对象数量
        int saveSuccessEnterpriseNumber = 0; // 保存成功的企业数量
        try {
            XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));// 创建Excel工作薄
            XSSFSheet sheet = xwb.getSheetAt(0);// 获取第一张工作表
            XSSFRow firstRow = sheet.getRow(1);// 第一行（列名，0列是备注）
            XSSFRow row = sheet.getRow(2); // 工作表的数据

            IndexData indexData = new IndexData(); // 指数对象
            String annual = getCellValue(row.getCell(2));
            String quarter = getCellValue(row.getCell(3));
            indexData = jointDate(indexData, Integer.parseInt(quarter.substring(0, quarter.indexOf("."))), annual.substring(0, annual.indexOf("."))); // 设置时间
            if (indexData == null) {
                returnMap.put("saveSuccessObjectNumber", saveSuccessObjectNumber);
                returnMap.put("saveSuccessEnterpriseNumber", saveSuccessEnterpriseNumber);
                returnMap.put("data", getCellValue(row.getCell(0)));
                returnMap.put("message", "季度值“" + quarter + "”输入错误，请核对“" + getCellValue(row.getCell(0)) + "”的季度值！");
                return returnMap;
            }
            getMaxAndMinMap(indexData);// 获取数据库中最大最小值(一张表只获取一次)

            Map<String, String> setMaxAndMinMessage = get2007TableColumnMaxAndMin(sheet); // 获取表中的最大最小值
            if ("error".equals(setMaxAndMinMessage.equals("state"))) {
                returnMap.put("saveSuccessObjectNumber", 0);
                returnMap.put("saveSuccessEnterpriseNumber", 0);
                returnMap.put("data", "");
                returnMap.put("message", setMaxAndMinMessage.get("message"));
                return returnMap;
            }

            int firstLineCellNumber = firstRow.getPhysicalNumberOfCells(); // 获取第一行单元格数量(有多少列)
            for (int j = 2; j < sheet.getPhysicalNumberOfRows(); j++) { // 遍历该表所有的数据行
                row = sheet.getRow(j); // 获取一行数据
                String enterpriseName = getCellValue(row.getCell(0));
                String registerNumber = getCellValue(row.getCell(1));
                EnterpriseBase enterpriseBase = enterpriseBaseService.selectByRegisterNumber(registerNumber);
                if (enterpriseBase == null) {
                    returnMap.put("saveSuccessObjectNumber", saveSuccessObjectNumber);
                    returnMap.put("saveSuccessEnterpriseNumber", saveSuccessEnterpriseNumber);
                    returnMap.put("data", enterpriseName);
                    returnMap.put("message", "企业“" + enterpriseName + "”不存在，请上传企业信息或检查“注册号”是否填写正确！");
                    return returnMap;
                }
                indexData.setEnterpriseId(enterpriseBase.getId()); // 设置企业id
                indexData.setIndustryId(enterpriseBase.getIndustryId()); // 设置产业id
                indexData.setTradeId(enterpriseBase.getTradeId()); // 设置行业id
                for (int i = startGetValueColumn; i < firstLineCellNumber; i++) {
                    String fifthColumnHeaderValue = getCellValue(firstRow.getCell(i)).trim();
                    String cellValue = getCellValue(row.getCell(i)); // Excel表中原始值
                    BigDecimal rawData;

                    List<IndexDefinition> fifthIndexDefinition = getIndexDefinitionByName(fifthColumnHeaderValue);// 查询指标定义
                    // 原始值归一化计算
                    if (fifthIndexDefinition != null && fifthIndexDefinition.size() > 0) {
                        indexData.setIndexId(fifthIndexDefinition.get(0).getId()); // 设置指标定义

                        if ("null".equals(cellValue)) { // 如果表格中没有填写数据就查询数据库上一季度的数据进行填充
                            // 封装查询条件
                            IndexData newIndexData = new IndexData();
                            newIndexData.setIndexId(indexData.getIndexId());
                            newIndexData.setEnterpriseId(enterpriseBase.getId());
                            newIndexData.setIndustryId(enterpriseBase.getIndustryId());
                            newIndexData.setTradeId(enterpriseBase.getTradeId());

                            List<IndexData> indexDataList = indexDataService.getByStartTimeDesc(newIndexData);
                            if (indexDataList != null && indexDataList.size() > 0) {
                                rawData = indexDataList.get(0).getRawData();
                            } else {
                                rawData = new BigDecimal(0); // 历史季度和表格中都没有就设置为0
                            }
                        } else {
                            rawData = new BigDecimal(cellValue);
                        }
                        BigDecimal data;
                        if (positiveNormalizedNameList.contains(fifthColumnHeaderValue)) {
                            data = normalized(rawData, fifthColumnHeaderValue, "positive");
                        } else if (negativeNormalizedNameList.contains(fifthColumnHeaderValue)) {
                            data = normalized(rawData, fifthColumnHeaderValue, "negative");
                        } else {
                            data = rawData;
                        }
                        long isSuccessful;
                        // 查询数据需要剔除掉id、data、rawData、createdTime、updateTime等条件
                        indexData.setId(null);
                        indexData.setData(null);
                        indexData.setRawData(null);
                        indexData.setCreatedTime(null);
                        indexData.setUpdateTime(null);
                        List<IndexData> indexDataList = indexDataService.getByMap(indexData);
                        if (indexDataList == null || indexDataList.size() == 0) {  // 保存数据
                            indexData.setData(data);
                            indexData.setRawData(rawData);
                            isSuccessful = indexDataService.save(indexData);
                            if (isSuccessful > 0) {
                                saveSuccessObjectNumber++;
                            }
                        } else {
                            indexData.setId(indexDataList.get(0).getId());
                            indexData.setData(data);
                            indexData.setRawData(rawData);
                            indexData.setCreatedTime((indexDataList.get(0).getCreatedTime()));
                            indexData.setUpdateTime(new Date());
                            isSuccessful = indexDataService.update(indexData);
                            if (isSuccessful > 0) {
                                saveSuccessObjectNumber++;
                            }
                        }
                        if (isSuccessful == 0) {
                            returnMap.put("saveSuccessObjectNumber", saveSuccessObjectNumber);
                            returnMap.put("saveSuccessEnterpriseNumber", saveSuccessEnterpriseNumber);
                            returnMap.put("data", enterpriseName);
                            returnMap.put("message", "此列“" + fifthColumnHeaderValue + "”数据保存失败，请检查模板格式或下载新模板，并按照“填写注意事项”进行修改，再上传！");
                            return returnMap;
                        }
                    } else {
                        returnMap.put("saveSuccessObjectNumber", saveSuccessObjectNumber);
                        returnMap.put("saveSuccessEnterpriseNumber", saveSuccessEnterpriseNumber);
                        returnMap.put("data", enterpriseName);
                        returnMap.put("message", "此列“" + fifthColumnHeaderValue + "”不在定义表中，请添加指标定义或模板列名错误，下载模板，并按照“填写注意事项”进行修改。列名禁止修改！");
                        return returnMap;
                    }
                }
                saveSuccessEnterpriseNumber++;
            }
            // 如果导入的数据表中有最大或最小值（会影响某列归一结果的时候），执行此代码修改某季度某列的全部值
            if (databaseMmaxAndMinMap != null && databaseMmaxAndMinMap.size() > 0 && MaxOrMinChangeMap != null && MaxOrMinChangeMap.size() > 0) {
                Iterator<Map.Entry<Long, BigDecimal>> iterator = MaxOrMinChangeMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Long id = iterator.next().getKey();
                    Map<String, BigDecimal> maxAndMin = totalMaxAndMinMap.get(id);
                    IndexData indexDataGetMaxAndMin = new IndexData();
                    indexDataGetMaxAndMin.setStartTime(indexData.getStartTime());
                    indexDataGetMaxAndMin.setEndTime(indexData.getEndTime());
                    indexDataGetMaxAndMin.setIndexId(id);
                    List<IndexData> indexDataList = indexDataService.getByMap(indexDataGetMaxAndMin); // 获取同一个季度同一个指标的所有值进行重新计算
                    for (int i = 0; i < indexDataList.size(); i++) {
                        IndexData inData = indexDataList.get(i);
                        BigDecimal rawData = inData.getRawData();

                        IndexDefinition indexDefinition = new IndexDefinition();
                        indexDefinition.setId(id);
                        IndexDefinition newIndexDefinition = indexDefinitionService.get(indexDefinition);
                        String name = newIndexDefinition.getName().trim();

                        BigDecimal max = maxAndMin.get("max");
                        BigDecimal min = maxAndMin.get("min");
                        BigDecimal numerator;
                        BigDecimal denominator;
                        inData.setUpdateTime(new Date());
                        if (positiveNormalizedNameList.contains(name)) { // 正向归一
                            numerator = rawData.subtract(min);
                            denominator = max.subtract(min);
                            inData.setData(numerator.divide(denominator, 2, RoundingMode.HALF_UP));
                            indexDataService.update(inData);
                        } else if (negativeNormalizedNameList.contains(name)) { // 负向归一
                            numerator = max.subtract(rawData);
                            denominator = max.subtract(min);
                            inData.setData(numerator.divide(denominator, 2, RoundingMode.HALF_UP));
                            indexDataService.update(inData);
                        } else {
                            inData.setData(rawData);
                            indexDataService.update(inData);
                        }
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        returnMap.put("saveSuccessObjectNumber", saveSuccessObjectNumber);
        returnMap.put("saveSuccessEnterpriseNumber", saveSuccessEnterpriseNumber);
        return returnMap;
    }

    /**
     * 判断每一个单元格内数据格式并统一转成String返回
     */
    private String getCellValue(Cell cell) {
        String value = "null";
        if (cell != null) {
            // 简单的查检列类型
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_STRING: // 字符串
                    if (!"".equals(cell.getRichStringCellValue().getString().trim())) { // 如果是空字符串返回“null”，以便区分。
                        value = cell.getRichStringCellValue().getString().trim();
                    }
                    break;
                case HSSFCell.CELL_TYPE_NUMERIC: // 数字
                    // 判断当前Cell的类型为Date 判断条件不支持如：2016年6月28日，这样带中文的日期可以将中文用/代替
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat(globalDateFormat);
                        value = sdf.format(date);
                    } else { // 如果是纯数值
                        if (Math.round(cell.getNumericCellValue()) > 2147483647) {// 大于int就转成long
                            value = String.valueOf(Math.round(cell.getNumericCellValue()));// 获取当前cell的值，并转成Long,再转成String类型
                        } else {
                            double numericCellValue = cell.getNumericCellValue();
                            value = String.valueOf(numericCellValue);
                        }
                    }
                    break;
//                case HSSFCell.CELL_TYPE_BLANK: // 空型值
//                    value = "";
//                    break;
//                case HSSFCell.CELL_TYPE_FORMULA: // 公式类型
//                    value = String.valueOf(cell.getCellFormula());
//                    break;
//                case HSSFCell.CELL_TYPE_BOOLEAN: // boolean型值
//                    value = String.valueOf(cell.getBooleanCellValue());
//                    break;
//                case HSSFCell.CELL_TYPE_ERROR: // 错误
//                    value = String.valueOf(cell.getErrorCellValue());
//                    break;
                default:
                    return value;
            }
        }
        return value;
    }

    /**
     * 判断Excel2003版文件中每一个单元格内数据格式
     */
//    private String get2003CellValue(Cell cell) {
//        String value = null;
//        if (cell != null) { // 如果单元格为空，直接返回null
//            // 简单的查检列类型
//            switch (cell.getCellType()) {
//                case HSSFCell.CELL_TYPE_STRING: // 字符串
//                    value = cell.getRichStringCellValue().getString();
//                    break;
//                case HSSFCell.CELL_TYPE_NUMERIC: // 数字
//                    // 判断当前Cell的类型为Date 判断条件不支持如：2016年6月28日，这样带中文的日期可以将中文用/代替
//                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
//                        Date date = cell.getDateCellValue();
//                        SimpleDateFormat sdf = new SimpleDateFormat(globalDateFormat);
//                        value = sdf.format(date);
//                    }else { // 如果是纯数值
//                        value = String.valueOf(Math.round(cell.getNumericCellValue()));// 获取当前cell的值，并转成Long,再转成String类型
//                    }
//                    break;
//                case HSSFCell.CELL_TYPE_BLANK: // 空型值
//                    value = "";
//                    break;
//                case HSSFCell.CELL_TYPE_FORMULA: // 公式类型
//                    value = String.valueOf(cell.getCellFormula());
//                    break;
//                case HSSFCell.CELL_TYPE_BOOLEAN: // boolean型值
//                    value = String.valueOf(cell.getBooleanCellValue());
//                    break;
//                case HSSFCell.CELL_TYPE_ERROR: // 错误
//                    value = String.valueOf(cell.getErrorCellValue());
//                    break;
//                default:
//                    break;
//            }
//        }
//        return value;
//    }


    /**
     * 判断Excel2007版文件中每一个单元格内数据格式
     */

//    private String get2007CellValue(XSSFCell cell) {
//        String value = null;
//        if (cell != null) { // 如果单元格为空，直接返回null
//            // 简单的查检列类型
//            switch (cell.getCellType()) {
//                case HSSFCell.CELL_TYPE_STRING: // 字符串
//                    value = cell.getRichStringCellValue().getString();
//                    break;
//                case HSSFCell.CELL_TYPE_NUMERIC: // 数字
//                    // 判断当前Cell的类型为Date 判断条件不支持如：2016年6月28日，这样带中文的日期可以将中文用/代替
//                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
//                        Date date = cell.getDateCellValue();
//                        SimpleDateFormat sdf = new SimpleDateFormat(globalDateFormat);
//                        value = sdf.format(date);
//                    }else { // 如果是纯数值
//                        value = String.valueOf(Math.round(cell.getNumericCellValue()));// 获取当前cell的值，并转成Long,再转成String类型
//                    }
//                    break;
//                case HSSFCell.CELL_TYPE_BLANK: // 空型值
//                    value = "";
//                    break;
//                case HSSFCell.CELL_TYPE_FORMULA: // 公式类型
//                    value = String.valueOf(cell.getCellFormula());
//                    break;
//                case HSSFCell.CELL_TYPE_BOOLEAN: // boolean型值
//                    value = String.valueOf(cell.getBooleanCellValue());
//                    break;
//                case HSSFCell.CELL_TYPE_ERROR: // 错误
//                    value = String.valueOf(cell.getErrorCellValue());
//                    break;
//                default:
//                    break;
//            }
//        }
//        return value;
//    }

    /**
     * 通过名称获取指标定义
     */
    private List<IndexDefinition> getIndexDefinitionByName(String name) {
        IndexDefinition indexDefinition = new IndexDefinition();
        indexDefinition.setName(name);
        indexDefinitionService.selectByMap(indexDefinition);
        return indexDefinitionService.selectByMap(indexDefinition);
    }

    /**
     * 归一化计算
     * @param originalValue 原始值
     * @param indexName     指标名称
     * @param indexName     positive negative(正向、反向)
     * @return 归一化后的最终数据
     */
    private BigDecimal normalized(BigDecimal originalValue, String indexName, String positive) {
        List<IndexDefinition> indexDefinitionByName = getIndexDefinitionByName(indexName);// 查询指标定义
        if (indexDefinitionByName != null && indexDefinitionByName.size() > 0) {
            IndexDefinition indexDef = indexDefinitionByName.get(0);

            BigDecimal max = totalMaxAndMinMap.get(indexDef.getId()).get("max");
            BigDecimal min = totalMaxAndMinMap.get(indexDef.getId()).get("min");
//            BigDecimal currentValue = new BigDecimal(originalValue);
            BigDecimal numerator;
            if ("positive".equals(positive)) { // 正向还是反向
                numerator = originalValue.subtract(min);
            } else {
                numerator = max.subtract(originalValue);
            }
            BigDecimal denominator = max.subtract(min);
            // -1:小于,0:等于,1:大于
            if (numerator.compareTo(new BigDecimal(0)) == 0) { // 解决除数为0的情况
                return numerator;
            }
            return numerator.divide(denominator, 2, RoundingMode.HALF_UP);
        }
        return null;
    }

    /**
     * 获取excel（2003）每列中最大值和最小值，并修改最终集合的最大最小值
     *
     * @param sheet 工作表
     */
    private Map<String, String> get2003TableColumnMaxAndMin(HSSFSheet sheet) {
        Map<String, String> returnMap = new HashMap<String, String>(); // 返回的集合
        HSSFRow firstRow = sheet.getRow(1); // 第一行
        int firstLineCellNumber = firstRow.getPhysicalNumberOfCells(); // 获取第一行单元格数量(有多少列)
        for (int j = 2; j < sheet.getPhysicalNumberOfRows(); j++) { // 遍历该表所有的行
            HSSFRow row = sheet.getRow(j); // 获取一行数据
            for (int i = startGetValueColumn; i < firstLineCellNumber; i++) { // 获取每一列
                String fifthColumnHeaderValue = getCellValue(firstRow.getCell(i)); // 获取第一行的名称
                boolean isNormalized = true;
                Iterator<Map.Entry<Long, String>> iterator = noNeedNormalizedMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    if (fifthColumnHeaderValue.equals(iterator.next().getValue())) { // 字段是否需要归一化（相等表示不需要）
                        isNormalized = false;
                    }
                }
                if (isNormalized) {
                    List<IndexDefinition> indexDefinitionByName = getIndexDefinitionByName(fifthColumnHeaderValue);// 查询指标定义
                    if (indexDefinitionByName != null && indexDefinitionByName.size() > 0) {
                        IndexDefinition fifthIndexDefinition = indexDefinitionByName.get(0);
                        Long indexDefinitionId = fifthIndexDefinition.getId();
                        BigDecimal cellValue;

                        String cellVal = getCellValue(row.getCell(i));
                        if ("null".equals(cellVal)) { // 如果表格中没有填写数据就查询数据库上一季度的数据进行填充
                            // 封装查询条件
                            String registerNumber = getCellValue(row.getCell(1));
                            EnterpriseBase enterpriseBase = enterpriseBaseService.selectByRegisterNumber(registerNumber); // 获取企业
                            IndexData indexData = new IndexData();
                            indexData.setIndexId(indexDefinitionId);
                            indexData.setEnterpriseId(enterpriseBase.getId());
                            indexData.setIndustryId(enterpriseBase.getIndustryId());
                            indexData.setTradeId(enterpriseBase.getTradeId());

                            List<IndexData> indexDataList = indexDataService.getByStartTimeDesc(indexData);
                            if (indexDataList != null && indexDataList.size() > 0) {
                                cellValue = indexDataList.get(0).getRawData();
                            } else {
                                cellValue = new BigDecimal(0); // 历史季度和表格中都没有就设置为0
                            }
                        } else {
                            cellValue = new BigDecimal(cellVal);
                        }

                        // 如果数据库没有本季度的数据
                        if (totalMaxAndMinMap == null || totalMaxAndMinMap.size() == 0) {
                            HashMap<String, BigDecimal> tempMaxMap = new HashMap<String, BigDecimal>();
                            tempMaxMap.put("max", cellValue);
                            tempMaxMap.put("min", cellValue);
                            totalMaxAndMinMap.put(indexDefinitionId, tempMaxMap);
                        } else {
                            // 如果本季度没有此指标的数据
                            Map<String, BigDecimal> totalMap = totalMaxAndMinMap.get(indexDefinitionId);
                            if (totalMap == null || totalMap.size() == 0) {
                                HashMap<String, BigDecimal> tempMaxMap = new HashMap<String, BigDecimal>();
                                tempMaxMap.put("max", cellValue);
                                tempMaxMap.put("min", cellValue);
                                totalMaxAndMinMap.put(indexDefinitionId, tempMaxMap);
                            } else {
                                // 如果有数据并且大于或小于最大最小值
                                BigDecimal totalMax = totalMap.get("max");
                                BigDecimal totalMin = totalMap.get("min");
                                if (cellValue.compareTo(totalMax) == 1) {
                                    totalMap.put("max", cellValue);
                                    if (databaseMmaxAndMinMap != null && databaseMmaxAndMinMap.size() > 0 && databaseMmaxAndMinMap.get(indexDefinitionId) != null
                                            && databaseMmaxAndMinMap.get(indexDefinitionId).size() > 0) {
                                        MaxOrMinChangeMap.put(indexDefinitionId, cellValue); // 添加变动后影响归化的指标定义id
                                    }
                                } else if (totalMin.compareTo(cellValue) == 1) {
                                    totalMap.put("min", cellValue);
                                    if (databaseMmaxAndMinMap != null && databaseMmaxAndMinMap.size() > 0 && databaseMmaxAndMinMap.get(indexDefinitionId) != null
                                            && databaseMmaxAndMinMap.get(indexDefinitionId).size() > 0) {
                                        MaxOrMinChangeMap.put(indexDefinitionId, cellValue); // 添加变动后影响归化的指标定义id
                                    }
                                }
                                totalMaxAndMinMap.put(indexDefinitionId, totalMap);
                            }
                        }
                    } else {
                        returnMap.put("state", "error");
                        returnMap.put("message", "指标名称“" + fifthColumnHeaderValue + "”在指标定义中不存在！");
                        return returnMap;
                    }
                }
            }
        }
        returnMap.put("state", "success");
        returnMap.put("message", "成功！");
        return returnMap;
    }

    /**
     * 获取excel（2007以上）每列中最大值和最小值，并修改最终集合的最大最小值
     *
     * @param sheet 工作表
     */
    private Map<String, String> get2007TableColumnMaxAndMin(XSSFSheet sheet) {
        Map<String, String> returnMap = new HashMap<String, String>(); // 返回的集合
        XSSFRow firstRow = sheet.getRow(1); // 第一行
        int firstLineCellNumber = firstRow.getPhysicalNumberOfCells(); // 获取第一行单元格数量(有多少列)
        for (int j = 2; j < sheet.getPhysicalNumberOfRows(); j++) { // 遍历该表所有的行
            XSSFRow row = sheet.getRow(j); // 获取一行数据
            for (int i = startGetValueColumn; i < firstLineCellNumber; i++) { // 获取每一列
                String fifthColumnHeaderValue = getCellValue(firstRow.getCell(i)); // 获取第一行的名称
                boolean isNormalized = true;
                Iterator<Map.Entry<Long, String>> iterator = noNeedNormalizedMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    if (fifthColumnHeaderValue.equals(iterator.next().getValue())) { // 字段是否需要归一化（相等表示不需要）
                        isNormalized = false;
                    }
                }
                if (isNormalized) {
                    List<IndexDefinition> indexDefinitionByName = getIndexDefinitionByName(fifthColumnHeaderValue);// 查询指标定义
                    if (indexDefinitionByName != null && indexDefinitionByName.size() > 0) {
                        IndexDefinition fifthIndexDefinition = indexDefinitionByName.get(0);
                        Long indexDefinitionId = fifthIndexDefinition.getId();
                        BigDecimal cellValue;

                        String cellVal = getCellValue(row.getCell(i));
                        if ("null".equals(cellVal)) { // 如果表格中没有填写数据就查询数据库上一季度的数据进行填充
                            // 封装查询条件
                            String registerNumber = getCellValue(row.getCell(1));
                            EnterpriseBase enterpriseBase = enterpriseBaseService.selectByRegisterNumber(registerNumber); // 获取企业
                            IndexData indexData = new IndexData();
                            indexData.setIndexId(indexDefinitionId);
                            indexData.setEnterpriseId(enterpriseBase.getId());
                            indexData.setIndustryId(enterpriseBase.getIndustryId());
                            indexData.setTradeId(enterpriseBase.getTradeId());

                            List<IndexData> indexDataList = indexDataService.getByStartTimeDesc(indexData);
                            if (indexDataList != null && indexDataList.size() > 0) {
                                cellValue = indexDataList.get(0).getRawData();
                            } else {
                                cellValue = new BigDecimal(0); // 历史季度和表格中都没有就设置为0
                            }
                        } else {
                            cellValue = new BigDecimal(cellVal);
                        }

                        // 如果数据库没有本季度的数据
                        if (totalMaxAndMinMap == null || totalMaxAndMinMap.size() == 0) {
                            HashMap<String, BigDecimal> tempMaxMap = new HashMap<String, BigDecimal>();
                            tempMaxMap.put("max", cellValue);
                            tempMaxMap.put("min", cellValue);
                            totalMaxAndMinMap.put(indexDefinitionId, tempMaxMap);
                        } else {
                            // 如果本季度没有此指标的数据
                            Map<String, BigDecimal> totalMap = totalMaxAndMinMap.get(indexDefinitionId);
                            if (totalMap == null || totalMap.size() == 0) {
                                HashMap<String, BigDecimal> tempMaxMap = new HashMap<String, BigDecimal>();
                                tempMaxMap.put("max", cellValue);
                                tempMaxMap.put("min", cellValue);
                                totalMaxAndMinMap.put(indexDefinitionId, tempMaxMap);
                            } else {
                                // 如果有数据并且大于或小于最大最小值
                                BigDecimal totalMax = totalMap.get("max");
                                BigDecimal totalMin = totalMap.get("min");
                                if (cellValue.compareTo(totalMax) == 1) {
                                    totalMap.put("max", cellValue);
                                    if (databaseMmaxAndMinMap != null && databaseMmaxAndMinMap.size() > 0 && databaseMmaxAndMinMap.get(indexDefinitionId) != null
                                            && databaseMmaxAndMinMap.get(indexDefinitionId).size() > 0) {
                                        MaxOrMinChangeMap.put(indexDefinitionId, cellValue); // 添加变动后影响归化的指标定义id
                                    }
                                } else if (totalMin.compareTo(cellValue) == 1) {
                                    totalMap.put("min", cellValue);
                                    if (databaseMmaxAndMinMap != null && databaseMmaxAndMinMap.size() > 0 && databaseMmaxAndMinMap.get(indexDefinitionId) != null
                                            && databaseMmaxAndMinMap.get(indexDefinitionId).size() > 0) {
                                        MaxOrMinChangeMap.put(indexDefinitionId, cellValue); // 添加变动后影响归化的指标定义id
                                    }
                                }
                                totalMaxAndMinMap.put(indexDefinitionId, totalMap);
                            }
                        }
                    } else {
                        returnMap.put("state", "error");
                        returnMap.put("message", "指标名称“" + fifthColumnHeaderValue + "”在指标定义中不存在！");
                        return returnMap;
                    }
                }
            }
        }
        returnMap.put("state", "success");
        returnMap.put("message", "成功！");
        return returnMap;
    }

    /**
     * 获取数据库指标定义的最大最小值
     */
    private void getMaxAndMinMap(IndexData indexData) {
        List<IndexData> maxList = indexDataService.getMaxByMap(indexData); // 获取最大值
        List<IndexData> minList = indexDataService.getMinByMap(indexData); // 获取最小值
        maxList.addAll(minList);// 合并两个list
        if (maxList != null && maxList.size() > 0) {
            for (int i = 0; i < maxList.size(); i++) {
                IndexData indexDa = maxList.get(i);
                String name = noNeedNormalizedMap.get(indexDa.getIndexId());
                if (name == null) { // 不在归一化集合内才求最大最小值
                    Long imdexId = indexDa.getIndexId();
                    BigDecimal rawData = indexDa.getRawData();
                    Map<String, BigDecimal> stringBigDecimalMap = databaseMmaxAndMinMap.get(imdexId);
                    if (stringBigDecimalMap == null || stringBigDecimalMap.size() == 0) {
                        Map<String, BigDecimal> maxOrMin = new HashMap<String, BigDecimal>();
                        maxOrMin.put("max", rawData);
                        databaseMmaxAndMinMap.put(imdexId, maxOrMin);
                        totalMaxAndMinMap.put(imdexId, maxOrMin);
                    } else {
                        BigDecimal max = stringBigDecimalMap.get("max");
                        // -1:小于,0:等于,1:大于
                        if (max.compareTo(rawData) == 1) {
                            stringBigDecimalMap.put("min", rawData);
                            databaseMmaxAndMinMap.put(imdexId, stringBigDecimalMap);
                            totalMaxAndMinMap.put(imdexId, stringBigDecimalMap);
                        } else {
                            stringBigDecimalMap.put("max", rawData);
                            stringBigDecimalMap.put("min", max);
                            databaseMmaxAndMinMap.put(imdexId, stringBigDecimalMap);
                            totalMaxAndMinMap.put(imdexId, stringBigDecimalMap);
                        }
                    }
                }
            }
        }
    }

    /**
     * 拼接日期
     */
    private IndexData jointDate(IndexData indexData, int quarterInt, String annual) {
        switch (quarterInt) { // 根据季度设置起止时间
            case 1:
                indexData.setStartTime(annual + "0101000000");
                indexData.setEndTime(annual + "0331000000");
                break;
            case 2:
                indexData.setStartTime(annual + "0401000000");
                indexData.setEndTime(annual + "0630000000");
                break;
            case 3:
                indexData.setStartTime(annual + "0701000000");
                indexData.setEndTime(annual + "0930000000");
                break;
            case 4:
                indexData.setStartTime(annual + "1001000000");
                indexData.setEndTime(annual + "1231000000");
                break;
            default:
                return null;
        }
        return indexData;
    }

}