package e2j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	public static void fromObject(Map<String, Object> map, File json) {
		ObjectMapper mapper= new ObjectMapper();
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(new FileOutputStream(json), map);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Map<String, String> fromString(String json) {
		Map<String, String> result = null;
		ObjectMapper mapper= new ObjectMapper();
		TypeReference<Map<String, String>> tr = new TypeReference<Map<String, String>>(){};
		try {
			result = mapper.readValue(json, tr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
