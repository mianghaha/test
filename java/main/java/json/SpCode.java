package json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import utils.JsonUtil;

public class SpCode {
	public static void main(String args[]){
		
		A a = new A();
		a.setA("\\(");
		a.setB("\"");
		
		try {
			String srtA = JsonUtil.TransToJson(a);
			System.out.println(srtA);
			A b = JsonUtil.TransToObject(srtA, A.class);
			System.out.println(a.getA().equals(b.getA()));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
