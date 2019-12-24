package map;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import utils.JsonUtil;

public class SortedMap {

	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		// TODO Auto-generated method stub
		LinkedHashMap<Integer, Integer> map1 = new LinkedHashMap();
		TreeMap<Integer, Integer> map2 = new TreeMap();
		Map<Integer, Integer> map3 = new HashMap();
		map3.put(1, 1);
		
		for(int i = 0; i < 200 ; i ++){
			map1.put(i, i);
			map2.put(i, i);
			map3.put(i, i);
		}
		
		System.out.println("map1=" + JsonUtil.TransToJson(map1));
		System.out.println("map2=" + JsonUtil.TransToJson(map2));
		System.out.println("map3=" + JsonUtil.TransToJson(map3));
	}

}
