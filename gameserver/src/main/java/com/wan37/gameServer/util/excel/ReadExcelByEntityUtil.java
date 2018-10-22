package com.wan37.gameServer.util.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 将poi进行简单的封装，通过注解和反射，将excel中的集合和实体的set方法对应，形成执行集，再将执行集进行遍历，对bean进行封装，转化为
 * 主要支持任意实体，
 *  支持excel文件中字段的不规则排序
 * @author
 *
 * @param <T>
 */
public class ReadExcelByEntityUtil<T> {

	private Logger logger = LoggerFactory.getLogger(ReadExcelByEntityUtil.class);
	
	private Workbook wb;//每一个Excel文件都将被解析成一个WorkBook对象
	
	private Sheet sheet;//Excel的每一页都将被解析成一个Sheet对象；
	
	private Row row;//Excel中的每一行都是一个Row对象，每一个单元格都是一个Cell对象。
	
	private Map<Integer,T> map; //最终结果集
  	
	private Class<T> entity;  //泛型类
	
	private Field[] fields;   //泛型方法集
	
	private List<Class> TypeList; //转化类型
	
	private Map<String,String> mapByAno=new HashMap<String,String>();//初始化集：<注解,属性名>

	/**
	 * 构造工具类
	 */
	@SuppressWarnings("unchecked")
	public ReadExcelByEntityUtil(String filepath) {
	        if(filepath == null){
	        	logger.error("Excel文件名为空");
	            return;  
	        }  
	        
	        String lastName = filepath.substring(filepath.lastIndexOf("."));  
	        try {  
	            InputStream is = new FileInputStream(filepath);  
	            if(".xls".equals(lastName)){  
	                wb = new HSSFWorkbook(is);  
	            }else if(".xlsx".equals(lastName)){  
	                wb = new XSSFWorkbook(is);  
	            }else{  
	                wb = null;
	            }  
	        } catch (FileNotFoundException e) {  
	            logger.error("文件找不到FileNotFoundException", e);  
	        } catch (IOException e) {  
	            logger.error("IOException", e);  
	        }  
	        entity = (Class<T>)((ParameterizedType)this.getClass().getGenericSuperclass())  
	                .getActualTypeArguments()[0];
		try {
			@SuppressWarnings("unused")
			T t = entity.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		if(this.entity != null){
		    fields = entity.getDeclaredFields();//获取到属性字段
            TypeList = new ArrayList<Class>();
            for(Field f:fields){
                //设置强制访问
                f.setAccessible(true);
                EntityName annotation = f.getAnnotation(EntityName.class);
                if(!annotation.id()){
                    //对true的字段进行拦截
                    mapByAno.put(annotation.column(),f.getName());
                    TypeList.add(annotation.clazz());
                    logger.debug("annotation.column()"+annotation.column()+"  "+f.getName());
                }
            }
		}
	}
	
	
	public Map<Integer,T> getMap() throws Exception{
		setEntityMap();
		return map;
	}
	
	/**
	 * 
	 * 将excel数据内容填充到map
	 * @throws Exception 
	 */
	public void setEntityMap() throws Exception{
		this.map = new HashMap<Integer, T>();  
		T t= null;
		String methodName = null;
		List<String> InvokeList = setInvokeList();
        sheet = wb.getSheetAt(0);  
        //总行数  
        int rowNum = sheet.getLastRowNum();  
        row = sheet.getRow(0);  
        //得到每一行单元格个数，包括其中的空单元格，这里要求表格内容的每一行都是固定的个数
        int colNum = row.getLastCellNum();  //每行单元格总数
        
       
        for (int i = 1; i <= rowNum; i++) {   //从第二行开始，遍历每一行
            row = sheet.getRow(i);                        
            t = exchangeEntity(InvokeList, colNum);  
            map.put(i-1, t);  //将封装好的实体放入map
        } 
	}


	public T exchangeEntity(List<String> InvokeList, int colNum){
		T t=null;
		try {
			DecimalFormat df = new DecimalFormat("#");          // 对长数字段进行string的转化
			String methodName;
			int j = 0;  
			t = entity.newInstance();  //每次新建一个T
			while (j < colNum) {  
			    Object obj = getCellFormatValue(row.getCell(j));  //
			    Class cs = TypeList.get(j);
			    methodName=InvokeList.get(j);
			    Method method = t.getClass().getMethod(methodName, TypeList.get(j));
			    if(obj==null||obj.equals("")){
			    	try {
						method.invoke(t, (Object)null); //Object转化，很关键
					} catch (Exception e) {
						return t;
					} 
			    }else{
			    	Object cast;
			    	if(cs.getName().equals("java.lang.String")){
			    		//在这里拦截“excel中读取为double,date,int,boolean”，实际的实体函数形参却是String类型的
			    		if(obj.getClass().getName().equals("java.lang.Double")){
			    			cast = df.format(obj);
			            }else{
			            	cast = String.valueOf(obj);
			            }
			    			
			    	}else if(cs.getName().equals("java.lang.Integer")){//形参需要的类型
			    		if(obj.getClass().getName().equals("java.lang.Double")){
			    			String intNUm = df.format(obj);
			    			cast = Integer.valueOf(intNUm);
			            }else{
			            	 cast = cs.cast(obj); //可对类型进行扩展
			            }
			    	}else{
			    		 cast = cs.cast(obj);
			    	}                                  
			    	System.out.println(cast);
					method.invoke(t, cast);
			    }
			    j++;  
			}
		} catch (Exception e) {

			return t;//有异常返回一个空t
		}
		return t;
	}
	
	
	
	public Class judgeMethodType(String methodName) throws Exception{
		T t = entity.newInstance();
		Class[] paramTypes = null;
		Method[]  methods = t.getClass().getMethods();//全部方法
		 try {
			for (int  i = 0;  i< methods.length; i++) {
			     if(methodName.equals(methods[i].getName())){//和传入方法名匹配 
			         Class[] params = methods[i].getParameterTypes();
			            paramTypes = new Class[ params.length] ;
			            for (int j = 0; j < params.length; j++) {
								paramTypes[j] = Class.forName(params[j].getName());
			            }
			            break; 
			        }
			    }
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error(" 文件找不到FileNotFoundException", e);
		}
		 return paramTypes[0];
	}
	
	
	
	/**
	 * 将标题头和实体的属性set方法对应上,形成一个执行函数集list,之后调用这个函数时，每次遍历list，然后invoke即可
	 * @throws Exception 
	 *
	 */
	private List<String> setInvokeList() throws Exception{
		if(wb==null){  
            throw new Exception("Workbook对象为空！");  
        }  
		List<String> invokeList = new ArrayList<String>();
		
		List<String> readExcelTitle = readExcelTitle();
		
		StringBuffer sb = new StringBuffer("set");
		//System.out.println("setInvokeList的判断"+readExcelTitle.size()+"?==?"+mapByAno.size());
		if(readExcelTitle.size()!=mapByAno.size()){
			System.out.println("KD:excel表头跟注解数量不对应");
			return null;
		}else{
			for (String obj : readExcelTitle) {
				if(mapByAno.get(obj)==null){
					System.out.println("KD:excel表头跟注解名称不对应");
					return null;
				}
				String fieldname = mapByAno.get(obj);
				mapByAno.remove(obj, fieldname);			//每拿一个put掉一个
				sb = new StringBuffer("set");
				String method = sb.append(fieldname.substring(0, 1).toUpperCase())
						.append(fieldname.substring(1)).toString();
				invokeList.add(method);
			}
			if(!mapByAno.isEmpty()){
				System.out.println("KD:excel表头跟注解名称不对应");
				return null;
			}
			return invokeList;
		/* */
		}
		
		
	}
	
	
	 /**
	  * 得到标题头,标题头一定要是string类型，否则报错
	  *
	  */
	 @SuppressWarnings("deprecation")
	 public List<String> readExcelTitle() throws Exception{
		 if(wb == null){
			 throw new Exception(" workbook对象为空");
		 }
		 sheet = wb.getSheetAt(0);
		 row = sheet.getRow(0);
		 //标题总列数
		int colNum = row.getPhysicalNumberOfCells();  //去掉对空列的计数
		 
		 //System.out.println("colNum:"+colNum);
		 List<String> list = new ArrayList<String>();
		 for(int i = 0;i<colNum;i++){
			 if(row.getCell(i)== null||row.getCell(i).equals("")){
				 list.add(null);
				 logger.debug("row.getCell(i)" + row.getCell(i));
             }else {
				 list.add((String) getCellFormatValue(row.getCell(i)));
                 logger.debug("(String) getCellFormatValue(row.getCell(i))"
                         + (String) getCellFormatValue(row.getCell(i)));
			 }
		}
		 return list;
	 }
	 
	 @SuppressWarnings("deprecation")
	 private Object getCellFormatValue(Cell cell) {  
		 	DecimalFormat df = new DecimalFormat("#");          // 对长数字段进行string的转化
	        Object cellvalue = null;  
	        if (cell != null) {  
	            // 判断当前Cell的Type  
	            switch (cell.getCellType()) {  
	            case Cell.CELL_TYPE_NUMERIC:// 如果当前Cell的Type为NUMERIC  
	            case Cell.CELL_TYPE_FORMULA: {  
	                // 判断当前的cell是否为Date  
	                if (DateUtil.isCellDateFormatted(cell)) {
                        cellvalue = cell.getDateCellValue();
	                } else {// 如果是纯数字  
	                    // 取得当前Cell的数值  
	                    cellvalue = cell.getNumericCellValue();
	                    /*if(cellvalue.getClass().getName().equals("java.lang.Double")){
	                    	cellvalue = df.format(cellvalue);
	                    }如果在此处拦截double就会使得所有的double都变成string，而有的时候我并不需要转成string */
	                }  
	                break;  
	            }  
	            case Cell.CELL_TYPE_STRING:// 如果当前Cell的Type为STRING  
	                // 取得当前的Cell字符串  
	                cellvalue = cell.getRichStringCellValue().getString();  
	                break;  
	            default:// 默认的Cell值  
	                cellvalue = "";  
	            }  
	        } else {  
	            cellvalue = "";  
	        }  
	        return cellvalue;  
	    }  
	 
	
}
