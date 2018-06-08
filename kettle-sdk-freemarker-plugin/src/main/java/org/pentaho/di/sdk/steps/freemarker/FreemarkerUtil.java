package org.pentaho.di.sdk.steps.freemarker;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreemarkerUtil {

	public static String generate(String templateStr, String originalJson, String tableJsonStr1, String tableJsonStr2, String tableJsonStr3) throws Exception {
		// init freemarker config and set freemarker template
	Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
	cfg.setDefaultEncoding("UTF-8");
	cfg.setWrapUncheckedExceptions(true);
	Template template = new Template("name", new StringReader(templateStr), cfg);
	// load json from file
	Map<String, List<Map<String, String>>> node = null;
	TypeReference<Map<String, List<Map<String, String>>>> tr = new TypeReference<Map<String, List<Map<String, String>>>>() {
	};
	node = new ObjectMapper().readValue(originalJson, tr);
	List<Map<String, String>> list = node.get("data");
	Map<String, Object> data = new HashMap<String, Object>();
	if (StringUtils.isNotEmpty(tableJsonStr1)) {
		Map<String, List<Map<String, String>>> tableData1 = new ObjectMapper().readValue(tableJsonStr1, tr);
		List<Map<String, String>> table1 = tableData1.get("data");
		data.put("table1", table1);
	}
	if (StringUtils.isNotEmpty(tableJsonStr2)) {
		Map<String, List<Map<String, String>>> tableData2 = new ObjectMapper().readValue(tableJsonStr2, tr);
		List<Map<String, String>> table2 = tableData2.get("data");
		data.put("table2", table2);
	}
	if (StringUtils.isNotEmpty(tableJsonStr3)) {
		Map<String, List<Map<String, String>>> tableData3 = new ObjectMapper().readValue(tableJsonStr3, tr);
		List<Map<String, String>> table3 = tableData3.get("data");
		data.put("table3", table3);
	}
	for (Map<String, String> m : list) {
		data.put(m.get("item_name"), m.get("item_value"));
	}
	// render freemarker template
	StringWriter out = new StringWriter(2048);
	template.process(data, out);
	return out.toString();
	}
}
