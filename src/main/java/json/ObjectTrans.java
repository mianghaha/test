package json;

import java.io.IOException;

import org.codehaus.jackson.JsonProcessingException;

public class ObjectTrans {


	public static void main(String[] args){
		String str_object = "{\"a\":\"123\",\"b\":\"444\",\"c\":\"555\"}";
		try {
			A a = JsonUtil.TransToObject(str_object, A.class);
			System.out.println(a);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
