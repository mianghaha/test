package cacheservice;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CacheListTest {
	
	private String key = "miang:test:cacheList";
	private List<TestObject> modelList = new ArrayList<TestObject>(){
		{
			TestObject test1 = new TestObject(1, 2, "3");
			this.add(test1);
			
			TestObject test2 = new TestObject(4, 5, "6");
			this.add(test2);
		}
	};
	
	private List<TestObject> extList = new ArrayList<TestObject>(){
		{
			TestObject test1 = new TestObject(7, 8, "9");
			this.add(test1);
		}
	};
	
	public void testListEquals(List<?> list1, List<?> list2) {
		for(int i = 0; i < list1.size(); i++) {
			if(!list1.get(i).equals(list2.get(i))) {
				Assert.fail();
			}
		}
	}
	
	@BeforeAll
	static void initCacheService() {
		RedisUtil.init(true, "10.236.254.201", 6379, "KeeL123s56..", 0, 5, 5, 5);
		CacheService.init(new RedisCacheService(), "mavenTest");
		
//		CacheService.init(new ConcurrentLocalCacheService(), "mavenTest");
	}
	
	
	@BeforeEach
	void delAllCache() throws Exception {
		CacheService.getInstance().delList(key);
	}
	
	@TestInterface
	/**
	 * 测试全部list缓存和取出
	 * @throws Exception
	 */
	void testCacheAllList() throws Exception {
		CacheService.getInstance().cacheList(key, modelList, true);
		List<TestObject> cacheList = CacheService.getInstance().getAllList(key, TestObject.class);
		testListEquals(modelList, cacheList);
	}
	
	@TestInterface
	/**
	 * 测试放入null
	 * @throws Exception
	 */
	void testCacheNull() throws Exception {
		CacheService.getInstance().cacheList(key, modelList, true);
		List<TestObject> cacheList = CacheService.getInstance().getAllList(key, TestObject.class);
		if(cacheList == null || cacheList.isEmpty()) {
			Assert.fail();
		}
		
		CacheService.getInstance().cacheList(key, null, true);
		cacheList = CacheService.getInstance().getAllList(key, TestObject.class);
		if(cacheList != null && !cacheList.isEmpty()) {
			Assert.fail();
		}
	}

}
