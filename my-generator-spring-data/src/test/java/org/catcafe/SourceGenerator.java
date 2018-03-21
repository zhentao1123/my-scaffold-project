package org.catcafe;

import static org.catcafe.util.FreeMarkerUtils.generateFileByFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.catcafe.util.DBInfoUtil;
import org.catcafe.util.TableInfo;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class SourceGenerator {
	
	private String basePackage;
	private Configuration config;
	
	public SourceGenerator(Configuration config, String basePackage) throws IOException 
	{
		this.config = config;
		this.basePackage = basePackage;
	}

	public void generateEntity(String entityTemplateFileName, String entityPackage, TableInfo tableInfo, boolean overwrite) throws IOException, TemplateException {
		String tableName = tableInfo.getName();
		String className = dbName2JavaClassName(tableName);
		String destFolderPath = (this.basePackage + "." + entityPackage).replace('.', '/');
		String destFilePath = destFolderPath + "/" + className + ".java"; 
		
		String baseEntityPackage = "base";
		String baseEntityName = "BaseEntity";
		String baseEntityImport = baseEntityPackage + "." + baseEntityName + ";";
		
		/*
		Map<String, Object> mapModel = new HashMap<>();
		mapModel.put("basePackage", basePackage);
		mapModel.put("entityPackage", entityPackage);
		mapModel.put("baseEntityImport", baseEntityImport);
		mapModel.put("tableName", tableName);
		mapModel.put("className", className);
		mapModel.put("baseEntityName", baseEntityName);
		*/
		
		EntityModel entityModel = new EntityModel();
		entityModel.setBasePackage(baseEntityPackage);
		entityModel.setEntityPackage(baseEntityPackage);
		entityModel.setBaseEntityImport(baseEntityImport);
		entityModel.setTableName(tableName);
		entityModel.setClassName(className);
		entityModel.setBaseEntityName(baseEntityName);
		entityModel.setTableInfo(tableInfo);
		
		generateFileByFile(entityTemplateFileName, destFilePath, this.config, entityModel, overwrite);
	}
	
	public void generateRepository() {
		
	}
	
	public static class EntityModel{
		private String basePackage;
		private String entityPackage;
		private String baseEntityImport;
		private String tableName;
		private String className;
		private String baseEntityName;
		private TableInfo tableInfo;
		
		public String getBasePackage() {
			return basePackage;
		}
		public void setBasePackage(String basePackage) {
			this.basePackage = basePackage;
		}
		public String getEntityPackage() {
			return entityPackage;
		}
		public void setEntityPackage(String entityPackage) {
			this.entityPackage = entityPackage;
		}
		public String getBaseEntityImport() {
			return baseEntityImport;
		}
		public void setBaseEntityImport(String baseEntityImport) {
			this.baseEntityImport = baseEntityImport;
		}
		public String getTableName() {
			return tableName;
		}
		public void setTableName(String tableName) {
			this.tableName = tableName;
		}
		public String getClassName() {
			return className;
		}
		public void setClassName(String className) {
			this.className = className;
		}
		public String getBaseEntityName() {
			return baseEntityName;
		}
		public void setBaseEntityName(String baseEntityName) {
			this.baseEntityName = baseEntityName;
		}
		public TableInfo getTableInfo() {
			return tableInfo;
		}
		public void setTableInfo(TableInfo tableInfo) {
			this.tableInfo = tableInfo;
		}
	}
	
	public static class EntityAttribute{
		private String typeClassName;
		private String name;
		public String getTypeClassName() {
			return typeClassName;
		}
		public void setTypeClassName(String typeClassName) {
			this.typeClassName = typeClassName;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	
	/**
	 * 数据库命名变为Java类名
	 * 将aa_bb的格式变为AaBb
	 * @param dbName
	 * @return
	 */
	private String dbName2JavaClassName(String dbName) {
		String[] strArr = dbName.split("_");
		StringBuilder sb = new StringBuilder();
		for(String str : strArr) {
			sb.append(Character.toUpperCase(str.charAt(0))).append(str.substring(1));
		}
		return sb.toString();
	}
	
	/**
	 * 数据库命名变为Java属性名
	 * 将aa_bb的格式变为aaBb
	 * @param dbName
	 * @return
	 */
	private String dbName2JavaAttributeName(String dbName) {
		String name = dbName2JavaClassName(dbName);
		return new StringBuilder().append(Character.toLowerCase(name.charAt(0))).append(name.substring(1)).toString();
	}
	
	public static void main(String[] args) throws IOException, TemplateException 
	{
		Configuration cfg = new Configuration(Configuration.getVersion());
		//cfg.setDirectoryForTemplateLoading(new File("/test/resources/flt/java"));
		cfg.setTemplateLoader(new ClassTemplateLoader(SourceGenerator.class, "/flt/java"));
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setLogTemplateExceptions(false);
		cfg.setWrapUncheckedExceptions(true);
		
		DBInfoUtil dbInfoUnit = new DBInfoUtil(
				"jdbc:mysql://rm-uf69svh1l840s9kd5zo.mysql.rds.aliyuncs.com/wxmall?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=round&useSSL=false",
				"muzusoftroot", "39fIdkwls230oP", "wxmall", "com.mysql.jdbc.Driver");
		TableInfo memberTableInfo = null;
		try {
			memberTableInfo = dbInfoUnit.getTableInfo("memb_member");
			memberTableInfo.process();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		SourceGenerator sourceGenerator = new SourceGenerator(cfg, "com.demo");
		
		sourceGenerator.generateEntity("entity.flt", "entity", memberTableInfo, true);
		//System.out.println("com.demo.entity".replace(".", "/"));
	}
}
