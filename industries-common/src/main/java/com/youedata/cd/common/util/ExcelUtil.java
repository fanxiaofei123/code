package com.youedata.cd.common.util;

import com.youedata.cd.common.exception.ExcelParserFailException;
import com.youedata.cd.industries.dto.excel.ExcelVOAttribute;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * ExcelUtil工具类实现功能:
 * 导出时传入list<T>,即可实现导出为一个excel,其中每个对象Ｔ为Excel中的一条记录.
 * 导入时读取excel,得到的结果是一个list<T>.T是自己定义的对象.
 * 需要导出的实体对象只需简单配置注解就能实现灵活导出,通过注解您可以方便实现下面功能:
 * 1.实体属性配置了注解就能导出到excel中,每个属性都对应一列.
 * 2.列名称可以通过注解配置.
 * 3.导出到哪一列可以通过注解配置.
 * 4.鼠标移动到该列时提示信息可以通过注解配置.
 * 5.用注解设置只能下拉选择不能随意填写功能.
 * 6.用注解设置是否只导出标题而不导出内容,这在导出内容作为模板以供用户填写时比较实用.
 */
public class ExcelUtil<T> {
    Class<T> clazz;

    public ExcelUtil(Class<T> clazz) {
        this.clazz = clazz;
    }


    //03或者07版本的进行统一得到Workbook
    public Workbook initWorkbook(String filePath) {
        Workbook workbook = null;
        try {
            if (filePath.endsWith("xlsx")) {
                workbook = new XSSFWorkbook(new FileInputStream(filePath));
            } else {
                workbook = new HSSFWorkbook(new FileInputStream(filePath));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }


    public String getCellValu(Cell cell, SimpleDateFormat sdf) {
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    if (sdf == null) {
                        sdf = new SimpleDateFormat("yyyy-MM-dd");
                    }
                    return sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
                }
                cell.setCellType(1);//如果不是时间类型，就统一把数据当做文本类型获取
                return cell.getStringCellValue();
            case HSSFCell.CELL_TYPE_STRING:
                System.out.println(cell.getStringCellValue());
                return cell.getStringCellValue();
            case HSSFCell.CELL_TYPE_FORMULA:
                return cell.getCellFormula();
            case HSSFCell.CELL_TYPE_BLANK:
                return "";
            case HSSFCell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue() + "";
            case HSSFCell.CELL_TYPE_ERROR:
                return cell.getErrorCellValue() + "";
        }
        return "";
    }

    public void packageObject(Field field, String value, T entity) {
        Class<?> fieldType = field.getType();
        try {
            if ((Integer.TYPE == fieldType)
                    || (Integer.class == fieldType)) {
                field.set(entity, Integer.parseInt(value));
            } else if (String.class == fieldType) {
                field.set(entity, String.valueOf(value));
            } else if ((Long.TYPE == fieldType)
                    || (Long.class == fieldType)) {
                field.set(entity, Long.valueOf(value));
            } else if ((Float.TYPE == fieldType)
                    || (Float.class == fieldType)) {
                field.set(entity, Float.valueOf(value));
            } else if ((Short.TYPE == fieldType)
                    || (Short.class == fieldType)) {
                field.set(entity, Short.valueOf(value));
            } else if ((Double.TYPE == fieldType)
                    || (Double.class == fieldType)) {
                field.set(entity, Double.valueOf(value));
            } else if (Character.TYPE == fieldType) {
                if ((value != null) && (value.length() > 0)) {
                    field.set(entity, Character
                            .valueOf(value.charAt(0)));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void getfieldMap(Field[] allFields, Map<Integer, Field> fieldsMap) {
        for (Field field : allFields) {
            // 将有注解的field存放到map中.
            if (field.isAnnotationPresent(ExcelVOAttribute.class)) {
                ExcelVOAttribute attr = field
                        .getAnnotation(ExcelVOAttribute.class);
                int col = getExcelCol(attr.column());// 获得列号
                // System.out.println(col + "====" + field.getName());
                field.setAccessible(true);// 设置类的私有字段属性可访问.
                fieldsMap.put(col, field);
            }
        }
    }

    public List<T> importExcel(String sheetName, String filePath, SimpleDateFormat sdf, int length) throws ExcelParserFailException {
        List<T> list = new ArrayList<T>();

        Workbook book = initWorkbook(filePath);
        try {
            Sheet sheet = null;
            if (sheetName != null && !sheetName.trim().equals("")) {
                sheet = book.getSheet(sheetName);// 如果指定sheet名,则取指定sheet中的内容.
            }
            if (sheet == null) {
                sheet = book.getSheetAt(0);// 如果传入的sheet名不存在则默认指向第1个sheet.
            }

//            int rows=filterRows(sheet,length);
            int rows = sheet.getLastRowNum()+1;// 得到数据的行数
            if (rows > 0) {// 有数据时才处理
                Field[] allFields = clazz.getDeclaredFields();// 得到类的所有field.
                Map<Integer, Field> fieldsMap = new HashMap<Integer, Field>();// 定义一个map用于存放列的序号和field.
                getfieldMap(allFields, fieldsMap);
                int count = 1;
                for (int i = 0; i < rows; i++) {
                    boolean flag = true;
                    Row row = sheet.getRow(i);// 得到一行中的所有单元格对象.
                    for (int j = 0; j < length; j++) {
                        Cell cell = row.getCell(j);
                        if(cell == null|| cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
                            continue;
                        }
                        String value = getCellValu(cell, sdf).trim();
                        if (value.equals("")) {
                            continue;
                        }
                        flag = false;
                    }
                        if(flag) {
                            count++;
                        }
                }

                rows -= count;

                for (int i = 0; i <= rows; i++) {// 从第1行开始取数据,默认第一行是表头.
                    Row row = sheet.getRow(i);// 得到一行中的所有单元格对象.
                    if (row == null)
                        continue;
                    T entity = null;
                    boolean blankRow = true;
                    for (int j = 0; j < length; j++) {
                        Cell cell = row.getCell(j);
                        entity = (entity == null ? clazz.newInstance() : entity);// 如果不存在实例则新建.
                        // System.out.println(cells[j].getContents());
//                        int cellType=cell.getCellType();
//                        cell.setCellType(1);
//                        Object v = cell.getStringCellValue();
                        if(cell == null) {
                            continue;
                        }
                        String value = getCellValu(cell, sdf).trim();
                        Field field = fieldsMap.get(j);// 从map中得到对应列的field.
                        if ("".equals(value)) {
                            continue;
                        }
                        if (field == null) {
                            continue;
                        }
                        blankRow = false;
                        // 取得类型,并根据对象类型设置值.
                        Class<?> fieldType = field.getType();
                        packageObject(field, value, entity);
                    }


//                    int j = 0;
//                    while (iterator.hasNext()) {
//
//                    }
                    if (blankRow) {//空行结束 应该报异常？
                        break;
                    }
                    if (entity != null) {
                        list.add(entity);
                    }
                }
            }

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 对
     * @param sheet
     * @param length
     * @return
     */
    public Integer filterRows(Sheet sheet,int length){
        CellReference cellReference = new CellReference("A"+length);
        boolean flag = false;
        System.out.println("总行数："+(sheet.getLastRowNum()+1));
        for (int i = cellReference.getRow(); i <= sheet.getLastRowNum();) {
            Row r = sheet.getRow(i);
            if(r == null){
                // 如果是空行（即没有任何数据、格式），直接把它以下的数据往上移动
                sheet.shiftRows(i+1, sheet.getLastRowNum(),-1);
                continue;
            }
            flag = false;
            for(Cell c:r){
                if(c.getCellType() != Cell.CELL_TYPE_BLANK){
                    flag = true;
                    break;
                }
            }
            if(flag){
                i++;
                continue;
            }
            else{//如果是空白行（即可能没有数据，但是有一定格式）
                if(i == sheet.getLastRowNum())//如果到了最后一行，直接将那一行remove掉
                    sheet.removeRow(r);
                else//如果还没到最后一行，则数据往上移一行
                    sheet.shiftRows(i+1, sheet.getLastRowNum(),-1);
            }
        }
        return sheet.getLastRowNum() + 1;
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @param sheetName 工作表的名称
     * @param sheetSize 每个sheet中数据的行数,此数值必须小于65536
     * @param output    java输出流
     */
    public boolean exportExcel(List<T> list, String sheetName, int sheetSize,
                               OutputStream output) {
        Field[] allFields = clazz.getDeclaredFields();// 得到所有定义字段
        List<Field> fields = new ArrayList<Field>();
        // 得到所有field并存放到一个list中.
        for (Field field : allFields) {
            if (field.isAnnotationPresent(ExcelVOAttribute.class)) {
                fields.add(field);
            }
        }

        HSSFWorkbook workbook = new HSSFWorkbook();// 产生工作薄对象

        // excel2003中每个sheet中最多有65536行,为避免产生错误所以加这个逻辑.
        if (sheetSize > 65536 || sheetSize < 1) {
            sheetSize = 65536;
        }
        double sheetNo = Math.ceil(list.size() / sheetSize);// 取出一共有多少个sheet.
        for (int index = 0; index <= sheetNo; index++) {
            HSSFSheet sheet = workbook.createSheet();// 产生工作表对象
            workbook.setSheetName(index, sheetName + index);// 设置工作表的名称.
            HSSFRow row;
            HSSFCell cell;// 产生单元格

            row = sheet.createRow(0);// 产生一行
            // 写入各个字段的列头名称
            for (int i = 0; i < fields.size(); i++) {
                Field field = fields.get(i);
                ExcelVOAttribute attr = field
                        .getAnnotation(ExcelVOAttribute.class);
                int col = getExcelCol(attr.column());// 获得列号
                cell = row.createCell(col);// 创建列
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);// 设置列中写入内容为String类型
                cell.setCellValue(attr.name());// 写入列名

                // 如果设置了提示信息则鼠标放上去提示.
                if (!attr.prompt().trim().equals("")) {
                    setHSSFPrompt(sheet, "", attr.prompt(), 1, 100, col, col);// 这里默认设了2-101列提示.
                }
                // 如果设置了combo属性则本列只能选择不能输入
                if (attr.combo().length > 0) {
                    setHSSFValidation(sheet, attr.combo(), 1, 100, col, col);// 这里默认设了2-101列只能选择不能输入.
                }
            }

            int startNo = index * sheetSize;
            int endNo = Math.min(startNo + sheetSize, list.size());
            // 写入各条记录,每条记录对应excel表中的一行
            for (int i = startNo; i < endNo; i++) {
                row = sheet.createRow(i + 1 - startNo);
                T vo = (T) list.get(i); // 得到导出对象.
                for (int j = 0; j < fields.size(); j++) {
                    Field field = fields.get(j);// 获得field.
                    field.setAccessible(true);// 设置实体类私有属性可访问
                    ExcelVOAttribute attr = field
                            .getAnnotation(ExcelVOAttribute.class);
                    try {
                        // 根据ExcelVOAttribute中设置情况决定是否导出,有些情况需要保持为空,希望用户填写这一列.
                        if (attr.isExport()) {
                            cell = row.createCell(getExcelCol(attr.column()));// 创建cell
                            cell.setCellType(HSSFCell.ENCODING_UTF_16);
                            cell.setCellValue(field.get(vo) == null ? ""
                                    : String.valueOf(field.get(vo)));// 如果数据存在就填入,不存在填入空格.
                        }
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        try {
            output.flush();
            workbook.write(output);
            output.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Output is closed ");
            return false;
        }

    }

    public boolean exportExcel07(List<T> list, String sheetName, int sheetSize,
                                 OutputStream output) {
        Field[] allFields = clazz.getDeclaredFields();// 得到所有定义字段
        List<Field> fields = new ArrayList<Field>();
        // 得到所有field并存放到一个list中.
        for (Field field : allFields) {
            if (field.isAnnotationPresent(ExcelVOAttribute.class)) {
                fields.add(field);
            }
        }
        XSSFWorkbook workbook = new XSSFWorkbook();

        // excel2003中每个sheet中最多有65536行,为避免产生错误所以加这个逻辑.
        if (sheetSize > 65536 || sheetSize < 1) {
            sheetSize = 65536;
        }
        double sheetNo = Math.ceil(list.size() / sheetSize);// 取出一共有多少个sheet.
        for (int index = 0; index <= sheetNo; index++) {
            XSSFSheet sheet = workbook.createSheet();// 创建一个工作薄对象
            workbook.setSheetName(index, sheetName + index);// 设置工作表的名称.
            XSSFRow row;
            XSSFCell cell;// 产生单元格

            row = sheet.createRow(0);// 产生一行
            // 写入各个字段的列头名称
            for (int i = 0; i < fields.size(); i++) {
                Field field = fields.get(i);
                ExcelVOAttribute attr = field
                        .getAnnotation(ExcelVOAttribute.class);
                int col = getExcelCol(attr.column());// 获得列号
                cell = row.createCell(col);// 创建列
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);// 设置列中写入内容为String类型
                cell.setCellValue(attr.name());// 写入列名

                // 如果设置了提示信息则鼠标放上去提示.
//                if (!attr.prompt().trim().equals("")) {
//                    setHSSFPrompt(sheet, "", attr.prompt(), 1, 100, col, col);// 这里默认设了2-101列提示.
//                }
                // 如果设置了combo属性则本列只能选择不能输入
//                if (attr.combo().length > 0) {
//                    setHSSFValidation(sheet, attr.combo(), 1, 100, col, col);// 这里默认设了2-101列只能选择不能输入.
//                }
            }

            int startNo = index * sheetSize;
            int endNo = Math.min(startNo + sheetSize, list.size());
            // 写入各条记录,每条记录对应excel表中的一行
            for (int i = startNo; i < endNo; i++) {
                row = sheet.createRow(i + 1 - startNo);
                T vo = (T) list.get(i); // 得到导出对象.
                for (int j = 0; j < fields.size(); j++) {
                    Field field = fields.get(j);// 获得field.
                    field.setAccessible(true);// 设置实体类私有属性可访问
                    ExcelVOAttribute attr = field
                            .getAnnotation(ExcelVOAttribute.class);
                    try {
                        // 根据ExcelVOAttribute中设置情况决定是否导出,有些情况需要保持为空,希望用户填写这一列.
                        if (attr.isExport()) {
                            cell = row.createCell(getExcelCol(attr.column()));// 创建cell
                            cell.setCellType(HSSFCell.ENCODING_UTF_16);
                            cell.setCellValue(field.get(vo) == null ? ""
                                    : String.valueOf(field.get(vo)));// 如果数据存在就填入,不存在填入空格.
                        }
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        try {
            output.flush();
            workbook.write(output);
            output.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Output is closed ");
            return false;
        }

    }

    /**
     * 将EXCEL中A,B,C,D,E列映射成0,1,2,3
     *
     * @param col
     */
    public static int getExcelCol(String col) {
        col = col.toUpperCase();
        // 从-1开始计算,字母重1开始运算。这种总数下来算数正好相同。
        int count = -1;
        char[] cs = col.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            count += (cs[i] - 64) * Math.pow(26, cs.length - 1 - i);
        }
        return count;
    }

    /**
     * 设置单元格上提示
     *
     * @param sheet         要设置的sheet.
     * @param promptTitle   标题
     * @param promptContent 内容
     * @param firstRow      开始行
     * @param endRow        结束行
     * @param firstCol      开始列
     * @param endCol        结束列
     * @return 设置好的sheet.
     */
    public static HSSFSheet setHSSFPrompt(HSSFSheet sheet, String promptTitle,
                                          String promptContent, int firstRow, int endRow, int firstCol,
                                          int endCol) {
        // 构造constraint对象
        DVConstraint constraint = DVConstraint
                .createCustomFormulaConstraint("DD1");
        // 四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow,
                endRow, firstCol, endCol);
        // 数据有效性对象
        HSSFDataValidation data_validation_view = new HSSFDataValidation(
                regions, constraint);
        data_validation_view.createPromptBox(promptTitle, promptContent);
        sheet.addValidationData(data_validation_view);
        return sheet;
    }

    /**
     * 设置某些列的值只能输入预制的数据,显示下拉框.
     *
     * @param sheet    要设置的sheet.
     * @param textlist 下拉框显示的内容
     * @param firstRow 开始行
     * @param endRow   结束行
     * @param firstCol 开始列
     * @param endCol   结束列
     * @return 设置好的sheet.
     */
    public static HSSFSheet setHSSFValidation(HSSFSheet sheet,
                                              String[] textlist, int firstRow, int endRow, int firstCol,
                                              int endCol) {
        // 加载下拉列表内容
        DVConstraint constraint = DVConstraint
                .createExplicitListConstraint(textlist);
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow,
                endRow, firstCol, endCol);
        // 数据有效性对象
        HSSFDataValidation data_validation_list = new HSSFDataValidation(
                regions, constraint);
        sheet.addValidationData(data_validation_list);
        return sheet;
    }
}