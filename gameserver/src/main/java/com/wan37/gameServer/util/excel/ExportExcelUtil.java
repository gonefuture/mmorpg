package com.wan37.gameServer.util.excel;


import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

;

/**
 * @author : 钱伟健 gonefuture@qq.com
 * @version : 2017/11/18 16:32.
 * 说明：
 */
public final class ExportExcelUtil<T> {

    private Logger logger = LoggerFactory.getLogger(ExportExcelUtil.class);

    /**
     *      生成XLS格式的excel对象
     * @param title     文件名字
     * @param headers   表格属性列名数组
     * @param dataSet 数据集
     * @return  EXCEL
     */
    public  Workbook exportXLS(String title,String[] headers, Collection<T> dataSet) {
        Workbook workbook = new HSSFWorkbook();
        return exportExcel(workbook,title,headers,dataSet);
    }

    /**
     *  生成XLS格式的excel对象
     * @param title 文件名字
     * @param headers   表格属性列名数组
     * @param dataSet   数据集
     * @return  EXCEl
     */
    public  Workbook exportXLSX(String title,String[] headers, Collection<T> dataSet) {
        //  声明一个工作簿和表格，并设置标题
        Workbook workbook = new HSSFWorkbook();
        return exportExcel(workbook,title,headers,dataSet);
    }





    //  通过实体类生成xls格式的workbook对象,
    /**
     * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据返回xls格式的EXCEL对象
     *
     * @param title
     *            表格标题名
     * @param headers
     *            表格属性列名数组
     * @param dataSet
     *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     */
     Workbook exportExcel(Workbook workbook,String title,String[] headers, Collection<T> dataSet) {

        Sheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(15);
        // 生成一个样式
        CellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        // 生成一个字体
        Font font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 生成并设置另一个样式
        CellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        style2.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(CellStyle.BORDER_THIN);
        style2.setBorderLeft(CellStyle.BORDER_THIN);
        style2.setBorderRight(CellStyle.BORDER_THIN);
        style2.setBorderTop(CellStyle.BORDER_THIN);
        style2.setAlignment(CellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        // 生成另一个字体
        Font font2 = workbook.createFont();
        font2.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style2.setFont(font2);


        // 产生表格标题行
        Row row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        int index = 0;
        for (T entity: dataSet){
            index++;
            row = sheet.createRow(index);

            //  利用反射，根据javaBean属性的先后顺序，动态调用getXxx()方法得到属性值
            Field[] fields = entity.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                //  创建cell单元格，此处可以设置单元格的样式
                Cell cell = row.createCell(i);
                Field field = fields[i];
                String fieldName = field.getName();
                //  如果是serialVersionUID属性,则继续循环
                if (fieldName.equals("serialVersionUID"))
                    continue;
                //  拼接出getXxx（）的名字
                String getMethodName = "getScene"
                        +fieldName.substring(0,1).toUpperCase()
                        +fieldName.substring(1);
                try {
                    Class clazz = entity.getClass();
                    Method getMethod = clazz.getMethod(getMethodName,new Class[] {});
                    Object value = getMethod.invoke(entity,new Object[] {});

                    //  判断值的类型后进行强制类型转换
                    String textValue = null;

                    // if (value instanceof Integer) {
                    // int intValue = (Integer) value;
                    // cell.setCellValue(intValue);
                    // } else if (value instanceof Float) {
                    // float fValue = (Float) value;
                    // textValue = new HSSFRichTextString(
                    // String.valueOf(fValue));
                    // cell.setCellValue(textValue);
                    // } else if (value instanceof Double) {
                    // double dValue = (Double) value;
                    // textValue = new HSSFRichTextString(
                    // String.valueOf(dValue));
                    // cell.setCellValue(textValue);
                    // } else if (value instanceof Long) {
                    // long longValue = (Long) value;
                    // cell.setCellValue(longValue);
                    // }
                    // 如果是日期类型，强转日期
                    if (value instanceof Date) {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat();
                        textValue = sdf.format(date);
                    } else {
                        //  其他数据类型当作字符串简单处理
                        if (value == null){     //  处理空的数据
                            textValue = " ";
                        } else {
                            textValue = value.toString();
                        }
                    }
                    //  利用正则表达式判断textValue是否全部由数字组成
                    if (textValue != null) {
                        Pattern pattern = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = pattern.matcher(textValue);
                        if (matcher.matches()) {
                            //  是数字当作double处理
                            cell.setCellValue(Double.valueOf(textValue));
                        } else {
                            //  让数据变成蓝色
                            RichTextString richTextString = new HSSFRichTextString(textValue);
                            Font font3 = workbook.createFont();
                            font.setColor(HSSFColor.BLACK.index);
                            richTextString.applyFont(font);
                            cell.setCellValue(richTextString);
                        }
                    }


                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
        }
        return workbook;
    }





}
