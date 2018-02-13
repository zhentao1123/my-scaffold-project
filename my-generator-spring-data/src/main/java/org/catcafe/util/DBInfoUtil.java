package org.catcafe.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 数据库工具类
 * @author zhangzhengtao
 *
 */
public class DBInfoUtil {
	
	private String driver;
	private String url;
	private String username;
	private String password;
	private String db;
	
	private Connection conn = null;
	
	public DBInfoUtil(String url, String userName, String password, String db, String driver){
		this.url = url;
		this.username = userName;
		this.password = password;
		this.db = db;
		this.driver = driver;
		
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private Connection openConn() throws SQLException{
		if(null==conn){
			conn = DriverManager.getConnection(url,username,password);
		}
		return conn;
	}
	
	private void closeConn() throws SQLException{
		if(null!=conn && !conn.isClosed()){
			conn.close();
			conn=null;
		}
	}
	
	public List<String> readTableNames() throws SQLException{
		List<String> tableNames = new ArrayList<String>();
		Connection conn = openConn();
		
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("show table status from " + db);

		while (rs.next()) {
			String tableName = rs.getString("Name");
			tableNames.add(tableName);
//		    System.out.println(tableName);
		}
		
		rs.close();
		stmt.close();
		closeConn();
		return tableNames;
	}
	
	public TableInfo getTableInfo(String tableName) throws SQLException{
		
		TableInfo tableInfo = new TableInfo();
		tableInfo.setName(tableName);
		List<ColumnInfo> tableFieldList = new ArrayList<ColumnInfo>();
		
		Connection conn = openConn();
		Statement stmt = conn.createStatement();
		
		ResultSet rs = stmt.executeQuery("select * from `" + tableName + "` where 1=2");
		ResultSetMetaData rsmd = rs.getMetaData();
		ResultSet rs1 = stmt.executeQuery("show full columns from " + tableName);
		
		int i=0;
		while (rs1.next()) {
			i++;
			ColumnInfo columnInfo = new ColumnInfo();
			
			columnInfo.setIndex(i);
			columnInfo.setField(rs1.getString("Field"));
			String type = rs1.getString("Type");
			System.out.println(type);
			if(type.contains("(")){
				columnInfo.setType(type.substring(0, type.indexOf("(")));
				columnInfo.setLength(type.substring(type.indexOf("(")+1, type.indexOf(")")));
			}
			columnInfo.setComment(rs1.getString("Comment"));
			columnInfo.setJavaClass(rsmd.getColumnClassName(i));
			columnInfo.setPk("PRI".equals(rs1.getString("Key")));
			columnInfo.setrequire(!"YES".equals(rs1.getString("Null")));
			
			tableFieldList.add(columnInfo);
			
			if("PRI".equals(rs1.getString("Key"))){
				tableInfo.setPk(columnInfo);
			}
		}
		tableInfo.setColumns(tableFieldList);
		
		rs.close();
		rs1.close();
		closeConn();
		stmt.close();
		return tableInfo;
	}
	
	public static class TableInfo{
		//表名
		private String name;
		//主键信息
		private ColumnInfo pk;
		//表字段信息
		private List<ColumnInfo> columnInfos;
		//表字段对应Java类型import引用类全路径（去重）
		private List<String> javaClasses = new ArrayList<String>();
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public ColumnInfo getPk() {
			return pk;
		}
		public void setPk(ColumnInfo pk) {
			this.pk = pk;
		}
		public List<ColumnInfo> getColumns() {
			return columnInfos;
		}
		public void setColumns(List<ColumnInfo> columnInfos) {
			//处理
			buildImportClasses(columnInfos);
			this.columnInfos = columnInfos;
		}
		public List<String> getJavaClasses() {
			return javaClasses;
		}
		public void setJavaClasses(List<String> javaClasses) {
			this.javaClasses = javaClasses;
		}
		
		/**
		 * 构造import所需列表（去重）
		 * @param columnInfos
		 */
		private void buildImportClasses(List<ColumnInfo> columnInfos){
			for(ColumnInfo field : columnInfos){
				String javaClass = field.getJavaClass();
				if(!this.javaClasses.contains(javaClass) && !javaClass.equals("")){
					this.javaClasses.add(javaClass);
				}
			}
		}
	}
	
	public static class ColumnInfo{
		//表字段索引
		private int index;
		//表字段名称
		private String field;
		//表字段类型
		private String type;
		//注释
		private String comment;
		//是否主键
		private boolean pk;
		//是否必须
		private boolean require;
		//字段长度
		private String length;
		//对应java类全名
		private String javaClass;
		//对应java类名
		private String javaClassName;
		//表字段名称，首字母大写
		private String fieldUpperCase;
		//字段值
		private Object value;
		
		public int getIndex() {
			return index;
		}
		public void setIndex(int index) {
			this.index = index;
		}
		public String getField() {
			return field;
		}
		public void setField(String field) {
			/**
			 * 处理java关键字
			 */
			field = processJavaKeyWord(field);
			this.fieldUpperCase = toUpperCaseFirstOne(field);
			this.field = field;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			//importClass需要处理成类中对应的文本
//			String type_ = typeProcess(type);
//			this.javaClass = javaClassProcess(type_);
//			this.type = type_;
			this.type = type;
		}
		public String getFieldUpperCase() {
			return fieldUpperCase;
		}
		public void setFieldUpperCase(String fieldUpperCase) {
			this.fieldUpperCase = fieldUpperCase;
		}
		public String getComment() {
			return comment;
		}
		public void setComment(String comment) {
			/**
			 * 处理特殊字符
			 */
			comment = processSpecialChar(comment);
			this.comment = comment;
		}
		public boolean isPk() {
			return pk;
		}
		public void setPk(boolean pK) {
			pk = pK;
		}
		public boolean isrequire() {
			return require;
		}
		public void setrequire(boolean require) {
			this.require = require;
		}
		public String getLength() {
			return length;
		}
		public void setLength(String length) {
			this.length = length;
		}
		public String getJavaClass() {
			return javaClass;
		}
		public void setJavaClass(String javaClass) {
			this.javaClass = javaClass;
			this.javaClassName = javaClass.substring(javaClass.lastIndexOf(".")+1);
		}
		public String getJavaClassName() {
			return javaClassName;
		}
		public void setJavaClassName(String javaClassName) {
			this.javaClassName = javaClassName;
		}
		public Object getValue() {
			return value;
		}
		public void setValue(Object value) {
			this.value = value;
		}
		//首字母转小写
		protected String toLowerCaseFirstOne(String s){
	        return Character.isLowerCase(s.charAt(0)) ? 
	        		s : (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
	    }
	    
	    //首字母转大写
		protected String toUpperCaseFirstOne(String s){
	        return Character.isUpperCase(s.charAt(0)) ? 
	        		s : (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
	    }
		
//		private String typeProcess(String type){
//		if(type==null)return "";
//		String result = type;
	//	
//		for(String[] info : types){
//			String supportType = info[0];
//			String showType = info[1];
//			String javaClass = info[2];
//			if(type.startsWith(supportType)) {
//				result = showType;
//				break;
//			}
//		}
//		return result;
	//}

	//private String javaClassProcess(String type){
//		if(type==null)return "";
//		String result = type;
	//	
//		for(String[] info : types){
//			String supportType = info[0];
//			String showType = info[1];
//			String javaClass = info[2];
//			if(type.startsWith(showType)) {
//				result = javaClass;
//				break;
//			}
//		}
//		return result;
	//}
		
//		private String[][] types = {
//					{"char",		"String",	""},
//					{"varchar",		"String",	""},
//					{"int",			"Integer",	""},
//					{"timestamp",	"Date",		"java.util.Date"},
//					{"date",		"Date",		"java.util.Date"},
//					{"time",		"Date",		"java.util.Date"},
//					{"text",		"String",	""},
//				};
		
		private static String processSpecialChar(String orig){
			String[] specialChars = new String[]{"\""};
			if(StringUtils.isBlank(orig)){
				return orig;
			}else{
				for(String specialChar : specialChars){
					if(orig.contains(specialChar)){
						return orig.replace(specialChar, "\\" + specialChar);
					}
				}
				return orig;
			}
		}
		
		private static String processJavaKeyWord(String orig){
			String[] javaKeyWords = new String[]{"short","boolean","long","double","int","char","byte"};
			if(StringUtils.isBlank(orig)){
				return orig;
			}else{
				for(String keyword : javaKeyWords){
					if(orig.equals(keyword)){
						return orig+"_";
					}
				}
				return orig;
			}
		}
	}
	
}
