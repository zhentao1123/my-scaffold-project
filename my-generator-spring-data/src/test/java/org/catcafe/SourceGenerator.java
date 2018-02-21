package org.catcafe.generator;

import static org.catcafe.util.FreeMarkerUtils.*;
import java.util.HashMap;
import java.util.Map;

public class SourceGenerator {

	public void generateEntity() {
		Map<String, Object> model = new HashMap<>();
		generateFileByFile(templateFilePath, destFilePath, configuration, model);
	}
	
	public void generateRepository() {
		
	}
}
