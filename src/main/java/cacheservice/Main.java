package cacheservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import utils.JsonUtil;

public class Main {

	public static void main(String[] args) throws Exception {
//		RedisUtil.init(true, "10.236.254.201", 6379, "KeeL123s56..", 0, 5, 5, 5);
//		CacheService.init(new RedisCacheService(), "mavenTest");
		
		CacheService.init(new ConcurrentLocalCacheService(), "mavenTest");
		
//		testCacheObject();
		
//		testCacheList();

//		testCacheSet();
		
		testCacheMap();
		testCacheMap2();
		testLinkedCacheMap();

		;
	}
	
	public static void initCacheService() {
		RedisUtil.init(true, "10.236.254.201", 6379, "KeeL123s56..", 0, 5, 5, 5);
		CacheService.init(new RedisCacheService(), "mavenTest");
		
//		CacheService.init(new ConcurrentLocalCacheService(), "mavenTest");
	}
	
	public static void testCacheObject() throws Exception {
		TestObject test = new TestObject(1, 2, "3");
		String key = "miang:test:cacheObject";
		CacheService.getInstance().cacheObject(key, test);
		System.out.println("cacheObject:original.obj=" + test);
		
		test = CacheService.getInstance().getCacheObject(key, TestObject.class);
		System.out.println("getCacheObject:new.obj=" + test);
	}
	
	public static void testCacheList() throws Exception {
		String key = "miang:test:cacheList";
		TestObject test1 = new TestObject(1, 2, "3");
		TestObject test2 = new TestObject(4, 5, "6");
		
		List<TestObject> list1 = new ArrayList<TestObject>();
		list1.add(test1);
		list1.add(test2);
		
		List<TestObject> list2 = new ArrayList<TestObject>();
		list2.add(test1);
		
		//cache整个列表和查询整个列表
		System.out.println("cacheList:original.list1=" + JsonUtil.TransToJson(list1));
		CacheService.getInstance().cacheList(key, list1, true);
		System.out.println("cacheList:cachelist.list1=" + JsonUtil.TransToJson(CacheService.getInstance().getAllList(key, TestObject.class)));
		
		//cache空队列
		CacheService.getInstance().cacheList(key, null, true);
		System.out.println("cacheList:null=" + JsonUtil.TransToJson(CacheService.getInstance().getAllList(key, TestObject.class)));
		
		//不覆盖cache
		CacheService.getInstance().cacheList(key, list1, true);
		CacheService.getInstance().cacheList(key, list2, false);
		System.out.println("cacheList:original.list1+list2=" + JsonUtil.TransToJson(CacheService.getInstance().getAllList(key, TestObject.class)));
		
		//覆盖cache
		CacheService.getInstance().cacheList(key, list2, true);
		System.out.println("cacheList:original.list2=" + JsonUtil.TransToJson(CacheService.getInstance().getAllList(key, TestObject.class)));
		
		//cache单个对象
		TestObject test3 = new TestObject(7, 8, "9");
		CacheService.getInstance().cacheListMember(key, test3);
		System.out.println("getAllList:add obj cache.list=" + JsonUtil.TransToJson(CacheService.getInstance().getAllList(key, TestObject.class)));
		
		//获取单个对象
		test3 = CacheService.getInstance().getListMember(key, TestObject.class, 2);
		System.out.println("getListMember:obj=" + JsonUtil.TransToJson(test3));
		
		//获取部分队列
		System.out.println("getPartialList:obj=" + JsonUtil.TransToJson(CacheService.getInstance().getPartialList(key, TestObject.class, 1, 2)));
		
		//删除单个对象
		System.out.println("delListMember.old:list=" + JsonUtil.TransToJson(CacheService.getInstance().getAllList(key, TestObject.class)));
		CacheService.getInstance().delListMember(key, test1);
		System.out.println("delListMember.new:list=" + JsonUtil.TransToJson(CacheService.getInstance().getAllList(key, TestObject.class)));
		
		//删除整个列表
		CacheService.getInstance().delList(key);
		System.out.println("delList:list=" + JsonUtil.TransToJson(CacheService.getInstance().getAllList(key, TestObject.class)));
	}
	
	public static void testCacheMap() throws Exception {
		String key = "miang:test:cacheMap";
		TestObject test1 = new TestObject(1, 2, "3");
		TestObject test2 = new TestObject(4, 5, "6");
		Map<String, TestObject> map1 = new HashMap<>();
		map1.put("test1", test1);
		map1.put("test2", test2);
		
		TestObject test4 = new TestObject(2, 1, "3");
		Map<String, TestObject> map2 = new HashMap<>();
		map2.put(key, test4);
		
		System.out.println("cacheMap:original.map=" + JsonUtil.TransToJson(map1));
		CacheService.getInstance().cacheMap(key, map1, true, false);
		System.out.println("cacheMap:cache.map1=" + JsonUtil.TransToJson(CacheService.getInstance().getAllCacheMap(key, TestObject.class, false)));
		CacheService.getInstance().cacheMap(key, map2, true, false);
		System.out.println("cacheMap:cache.map2=" + JsonUtil.TransToJson(CacheService.getInstance().getAllCacheMap(key, TestObject.class, false)));
		CacheService.getInstance().cacheMap(key, map1, false, false);
		System.out.println("cacheMap:cache.map1+map2=" + JsonUtil.TransToJson(CacheService.getInstance().getAllCacheMap(key, TestObject.class, false)));
		
		TestObject test3 = new TestObject(7, 8, "9");
		CacheService.getInstance().cacheMapMember(key, "test3", test3, false);
		Map<String, TestObject> tmp = CacheService.getInstance().getAllCacheMap(key, TestObject.class, false);
		System.out.println("cacheMap:cache.object.map=" + JsonUtil.TransToJson(tmp));
		
		test4 = CacheService.getInstance().getMapMember(key, "test3", TestObject.class);
		System.out.println("getMapMember:cache.test3=" + JsonUtil.TransToJson(test4));
		
		CacheService.getInstance().delMapMember(key, "test1");
		System.out.println("delMapMember:delete.test1.map=" + JsonUtil.TransToJson(CacheService.getInstance().getAllCacheMap(key, TestObject.class, false)));
	
		CacheService.getInstance().delMap(key);
		System.out.println("delMap:delete.map=" + JsonUtil.TransToJson(CacheService.getInstance().getAllCacheMap(key, TestObject.class, false)));
	}
	
	public static void testCacheSet() throws Exception {
		String key = "miang:test:cacheSet";
		TestObject test1 = new TestObject(1, 2, "3");
		TestObject test2 = new TestObject(4, 5, "6");
		Set<TestObject> set1 = new HashSet<>();
		set1.add(test1);
		set1.add(test2);
		
		TestObject test4 = new TestObject(2, 1, "3");
		Set<TestObject> set2 = new HashSet<>();
		set2.add(test4);
		
		//缓存和查询所有set
		System.out.println("cacheSet:original.set=" + JsonUtil.TransToJson(set1));
		CacheService.getInstance().cacheSet(key, set1, true);
		System.out.println("getCacheSet:cache.set1=" + JsonUtil.TransToJson(CacheService.getInstance().getCacheSet(key, TestObject.class)));
		
		//覆盖缓存
		CacheService.getInstance().cacheSet(key, set2, true);
		System.out.println("getCacheSet:cache.set2=" + JsonUtil.TransToJson(CacheService.getInstance().getCacheSet(key, TestObject.class)));
		
		//非覆盖缓存
		CacheService.getInstance().cacheSet(key, set1, false);
		System.out.println("getCacheSet:cache.set1+set2=" + JsonUtil.TransToJson(CacheService.getInstance().getCacheSet(key, TestObject.class)));
		
		//缓存单个对象
		TestObject test3 = new TestObject(7, 8, "9");
		CacheService.getInstance().cacheSetMember(key, test3);
		System.out.println("cacheSet:cache.set=" + JsonUtil.TransToJson(CacheService.getInstance().getCacheSet(key, TestObject.class)));
		
		//随机换取一个对象
		TestObject tmp1 = CacheService.getInstance().getSetRandMember(key, TestObject.class);
		TestObject tmp2 = CacheService.getInstance().getSetRandMember(key, TestObject.class);
		TestObject tmp3 = CacheService.getInstance().getSetRandMember(key, TestObject.class);
		System.out.println("getSetRandMember:cache.tmp1=" + JsonUtil.TransToJson(tmp1) + ",tmp2=" + tmp2 + ",tmp3=" + tmp3 + ",allset=" + JsonUtil.TransToJson(CacheService.getInstance().getCacheSet(key, TestObject.class)));
		
		//随机取出一个对象
		tmp1 = CacheService.getInstance().popSetRandMember(key, TestObject.class);
		System.out.println("popSetRandMember:cache.pop=" + JsonUtil.TransToJson(tmp1) + ",allset=" + JsonUtil.TransToJson(CacheService.getInstance().getCacheSet(key, TestObject.class)));
		
		//删除一个对象
		CacheService.getInstance().delSetMember(key, test3);
		System.out.println("delSetMember:obj=" + JsonUtil.TransToJson(test3) + ",cache.set=" + JsonUtil.TransToJson(CacheService.getInstance().getCacheSet(key, TestObject.class)));
		
		//删除整个set
		CacheService.getInstance().delSet(key);
		System.out.println("delSet:cache.set=" + JsonUtil.TransToJson(CacheService.getInstance().getCacheSet(key, TestObject.class)));
		
		//对象为null和emty覆盖式缓存
		CacheService.getInstance().cacheSet(key, set1, true);
		CacheService.getInstance().cacheSet(key, null, true);
		System.out.println("getCacheSet:cache.null=" + JsonUtil.TransToJson(CacheService.getInstance().getCacheSet(key, TestObject.class)));
		CacheService.getInstance().cacheSet(key, set1, true);
		CacheService.getInstance().cacheSet(key, new HashSet<>(), true);
		System.out.println("getCacheSet:cache.empty=" + JsonUtil.TransToJson(CacheService.getInstance().getCacheSet(key, TestObject.class)));
	}
	
	public static void testCacheMap2() throws Exception {
		String key = "miang:test:cacheMap";
		Integer test1 = new Integer(1);
		Integer test2 = new Integer(2);
		Map<String, Integer> map1 = new HashMap<>();
		map1.put("test1", test1);
		map1.put("test2", test2);
		
		Integer test4 = new Integer(4);
		Map<String, Integer> map2 = new HashMap<>();
		map2.put(key, test4);
		
		System.out.println("cacheMap:original.map=" + JsonUtil.TransToJson(map1));
		CacheService.getInstance().cacheMap(key, map1, true, false);
		System.out.println("cacheMap:cache.map1=" + JsonUtil.TransToJson(CacheService.getInstance().getAllCacheMap(key, Integer.class, false)));
		CacheService.getInstance().cacheMap(key, map2, true, false);
		System.out.println("cacheMap:cache.map2=" + JsonUtil.TransToJson(CacheService.getInstance().getAllCacheMap(key, Integer.class, false)));
		CacheService.getInstance().cacheMap(key, map1, false, false);
		System.out.println("cacheMap:cache.map1+map2=" + JsonUtil.TransToJson(CacheService.getInstance().getAllCacheMap(key, Integer.class, false)));
		
		Integer test3 = new Integer(3);
		CacheService.getInstance().cacheMapMember(key, "test3", test3, false);
		Map<String, Integer> tmp = CacheService.getInstance().getAllCacheMap(key, Integer.class, false);
		System.out.println("cacheMap:cache.object.map=" + JsonUtil.TransToJson(tmp));
		
		test4 = CacheService.getInstance().getMapMember(key, "test3", Integer.class);
		System.out.println("getMapMember:cache.test3=" + JsonUtil.TransToJson(test4));
		
		CacheService.getInstance().delMapMember(key, "test1");
		System.out.println("delMapMember:delete.test1.map=" + JsonUtil.TransToJson(CacheService.getInstance().getAllCacheMap(key, Integer.class, false)));
	
		CacheService.getInstance().delMap(key);
		System.out.println("delMap:delete.map=" + JsonUtil.TransToJson(CacheService.getInstance().getAllCacheMap(key, Integer.class, false)));
		
		//缓存null和空map
		CacheService.getInstance().cacheMap(key, map1, true, false);
		CacheService.getInstance().cacheMap(key, null, true, false);
		System.out.println("cacheMap:cache.null=" + JsonUtil.TransToJson(CacheService.getInstance().getAllCacheMap(key, Integer.class, false)));
		CacheService.getInstance().cacheMap(key, map1, true, false);
		CacheService.getInstance().cacheMap(key, new HashMap<>(), true, false);
		System.out.println("cacheMap:cache.empty=" + JsonUtil.TransToJson(CacheService.getInstance().getAllCacheMap(key, Integer.class, false)));
	}
	
	public static void testLinkedCacheMap() throws Exception {
		String key = "miang:test:cacheMap";
		Integer test1 = new Integer(1);
		Integer test2 = new Integer(2);
		Integer test3 = new Integer(3);
		Integer test4 = new Integer(4);
		Integer test5 = new Integer(5);
		Integer test6 = new Integer(6);
		Integer test7 = new Integer(7);
		Integer test8 = new Integer(8);
		Integer test9 = new Integer(9);
		Integer test10 = new Integer(10);
		
		Map<String, Integer> map1 = new LinkedHashMap<>();
		map1.put("test1", test1);
		map1.put("test2", test2);
		map1.put("test3", test3);
		map1.put("test4", test4);
		map1.put("test5", test5);
		map1.put("test6", test6);
		map1.put("test7", test7);
		map1.put("test8", test8);
		map1.put("test9", test9);
		map1.put("test10", test10);
		
		System.out.println("cacheMap:original.map=" + JsonUtil.TransToJson(map1));
		CacheService.getInstance().cacheMap(key, map1, true, true);
		System.out.println("cacheMap:linked=" + JsonUtil.TransToJson(CacheService.getInstance().getAllCacheMap(key, Integer.class, true)));
		System.out.println("cacheMap:not linked=" + JsonUtil.TransToJson(CacheService.getInstance().getAllCacheMap(key, Integer.class, false)));
		
		Integer test100 = new Integer(100);
		CacheService.getInstance().cacheMapMember(key, "test100", test100, true);
		System.out.println("cacheMap:cache.object.map=" + JsonUtil.TransToJson(CacheService.getInstance().getAllCacheMap(key, Integer.class, true)));
		System.out.println("getMapMember:cache.test100=" + JsonUtil.TransToJson(CacheService.getInstance().getMapMember(key, "test100", Integer.class)));
		
		CacheService.getInstance().delMapMember(key, "test1");
		System.out.println("delMapMember:delete.test1.map=" + JsonUtil.TransToJson(CacheService.getInstance().getAllCacheMap(key, Integer.class, true)));
	
		CacheService.getInstance().delMap(key);
		System.out.println("delMap:delete.map=" + JsonUtil.TransToJson(CacheService.getInstance().getAllCacheMap(key, Integer.class, true)));
	}
}
