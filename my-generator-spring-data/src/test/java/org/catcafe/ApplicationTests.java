package org.catcafe;

import java.io.IOException;
import java.sql.SQLException;

import org.catcafe.util.DBInfoUtil;
import org.catcafe.util.DBInfoUtil.TableInfo;
import org.catcafe.util.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import freemarker.template.TemplateException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	Logger logger = LoggerFactory.getLogger(ApplicationTests.class);
	
	@Autowired
	DBInfoUtil dbInfoUtil;
	
	@Test
	public void testDBInfoUtil() {
		try {
			TableInfo tableInfo = dbInfoUtil.getTableInfo("user");
			logger.debug(JsonUtil.obj2json(tableInfo));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSourceGenerator() throws IOException, TemplateException {
		SourceGenerator sourceGenerator = new SourceGenerator(
				"src/test/resources/flt/java/",
				"com.demo",
				"entity.flt",
				"entity",
				"repository.flt",
				"repository");
		sourceGenerator.generateEntity("user", true);
	}

	public static void main(String[] args) {
		
	}
}
