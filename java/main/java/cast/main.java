package cast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import utils.JsonUtil;

public class main {

	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		// TODO Auto-generated method stub
//		A a = new A(1,2);
//		B b = (B)a;
		
		B b = new B(1, 2);
		A a = (A)b;
		System.out.println(JsonUtil.TransToJson(a));
	}

}
