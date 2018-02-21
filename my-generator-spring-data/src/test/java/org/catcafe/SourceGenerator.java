package org.catcafe;

import static org.catcafe.util.FreeMarkerUtils.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

public class SourceGenerator {
	
	private String baseTemplatePath;
	
	private String basePackage;
	
	private String entityTemplateFile;
	
	private String entityPackage;
	
	private String repositoryTemplateFile;
	
	private String repositoryPackage;
	
	private Configuration config;
	
	public SourceGenerator(
		String baseTemplatePath,
		String basePackage,
		String entityTemplateFile,
		String entityPackage,
		String repositoryTemplateFile,
		String repositoryPackage) throws IOException 
	{
		this.baseTemplatePath = baseTemplatePath;
		this.basePackage = basePackage;
		this.entityTemplateFile = entityTemplateFile;
		this.entityPackage = entityPackage;
		this.repositoryTemplateFile = repositoryTemplateFile;
		this.repositoryPackage = repositoryPackage;
		this.config = new freemarker.template.Configuration(Configuration.getVersion());
        this.config.setDirectoryForTemplateLoading(new File(this.baseTemplatePath));
        this.config.setDefaultEncoding("utf-8");
	}

	public void generateEntity(String tableName, boolean overwrite) throws IOException, TemplateException {
		String templateFilePath = this.baseTemplatePath + this.entityTemplateFile;
		String destFilePath = (this.basePackage + "." + this.entityPackage).replace('.', '/');
		destFilePath = destFilePath + "/" + tableName + ".java";
		Map<String, Object> model = new HashMap<>();
		model.put("basePackage", basePackage);
		model.put("entityPackage", entityPackage);
		generateFileByFile(templateFilePath, destFilePath, this.config, model, overwrite);
	}
	
	public void generateRepository() {
		
	}
	
	public static void main(String[] args) throws IOException, TemplateException {
		SourceGenerator sourceGenerator = new SourceGenerator(
				"src/test/resources/flt/",
				"com.demo",
				"java/entity.flt",
				"entity",
				"java/repository.flt",
				"repository");
		sourceGenerator.generateEntity("user", true);
		//System.out.println("com.demo.entity".replace(".", "/"));
	}
}
