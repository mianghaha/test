package json;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonProcessingException;

public class maptest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str_map = "{\"1\":\"3,4\",\"2\":\"5\"}";
		try {
			Map<Long, String> map = JsonUtil.TransToObject(str_map, HashMap.class);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
