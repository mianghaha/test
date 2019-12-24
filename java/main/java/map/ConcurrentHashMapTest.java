package map;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapTest {

	public static void main(String[] args) throws InterruptedException {
		ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
		HashMap<String, String> map2 = new HashMap<>();
		for(;;) {
			System.out.println("ttt");
			Thread.currentThread().sleep(1000);
		}
	}
}
