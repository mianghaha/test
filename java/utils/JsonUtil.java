package utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;


public class JsonUtil

{
	
	private static ObjectMapper om = new ObjectMapper();
	
	static{
		om.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
		om.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		om.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		om.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	public static <T> T TransToObject(String json, Class<? extends T> cls) throws JsonProcessingException, IOException{
		ObjectReader or = om.reader(cls);
		T bean = null;
		bean = or.readValue(json);
		return bean;
	}
	
	public static String TransToJson (Object o) throws JsonGenerationException, JsonMappingException, IOException{
		ObjectWriter writer = om.writer();
		String result = null;
		result = writer.writeValueAsString(o);
		return result;
	}
	
		
}
