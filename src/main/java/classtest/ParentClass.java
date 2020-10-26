package classtest;

import java.util.HashMap;
import java.util.Map;

public class ParentClass implements Interface{

	public int i = 2;
	public static Map<String, String> j = new HashMap<String, String>();
	
	static {
		j.put("ParentClass", "ParentClass");
	}
	
	public void test() {
		
	}
	
	public static void test2() {
		System.out.println("ParentClass test2");
	}
}
