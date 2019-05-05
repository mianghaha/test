package concurrent;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class skipmap {

	public static void main(String[] args){
		ConcurrentSkipListMap<Integer,Integer> a = new ConcurrentSkipListMap<Integer,Integer>();
		a.put(1,1000);
		a.put(30,1001);
		a.put(2,1002);
		a.put(100,1003);
		for(Map.Entry<Integer,Integer> entry : a.entrySet()){
			System.out.println(entry.getKey() + "====" + entry.getValue());
		}
	}
}
