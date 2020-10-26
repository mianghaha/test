package map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapTest {

	public static void main(String[] args) throws InterruptedException {
		final ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
		for(int i = 0; i < 100; i++) {
			map.put("test" + i, "test" + i);
		}
		Iterator<Entry<String, String>> iterators = map.entrySet().iterator();
		Thread thread = new Thread() {
			public void run() {
				Iterator<Entry<String, String>> iterators2 = map.entrySet().iterator();
				while(iterators2.hasNext()) {
					Entry<String, String> entry2 = iterators2.next();
					iterators2.remove();
					System.out.println(entry2.getKey() + " removed");
					
				}
			}
		};
		thread.start();
		//Thread.currentThread().sleep(1);
		while(iterators.hasNext()) {
			Entry<String, String> entry = iterators.next();
			String key = entry.getKey();
			System.out.println(key);
		}
	}
	
	public static void remove(Map<String, String> map) {
		map.remove("test1");
	}
}
