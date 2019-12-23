package genetic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import utils.JsonUtil;

public class Main {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
//		BaseClass imp = new Imp();
//		Map a = imp.getCacheObject("aaa");
//		System.out.println(a.toString());
		
//		Set<String> set1 = new HashSet<>();
//		set1.add("1");
//		Set set = set1;
//		Set<Integer> set2 = (Set<Integer>)set;
//		System.out.println(JsonUtil.TransToJson(set2));
		
		
		Map<Object, String> map = new HashMap<Object, String>();
		map.put(new Integer(1), "1");
		map.put(new Integer(1), "2");
		map.put("1", "1");
		System.out.println(JsonUtil.TransToJson(map));
		
	}

}
