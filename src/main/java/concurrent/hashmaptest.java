package concurrent;

import java.util.concurrent.ConcurrentHashMap;

public class hashmaptest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		final ConcurrentHashMap<Integer, Integer> ll = new ConcurrentHashMap<>();
		ll.put(1, 1);
		ll.put(2, 2);
		
		for(Integer gameid : ll.keySet()){
			System.out.println(gameid);
		}
		
	}

}
