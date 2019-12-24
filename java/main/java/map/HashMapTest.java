package map;

import java.util.HashMap;
import java.util.Map;

public class HashMapTest {

	public static void main(String[] args) {
		Map<String, Integer> map = new HashMap<>();
		System.out.println(map.getOrDefault("1", 1));
	}
}
