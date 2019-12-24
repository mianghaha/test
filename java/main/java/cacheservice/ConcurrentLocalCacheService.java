package cacheservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentLocalCacheService extends CacheService{
	
	private enum Resource{
		List,Set,Map;
	}
	
	private static Map<Resource, Lock> resourceLockMap = new HashMap<Resource,Lock>();
	{
		for(Resource resource : Resource.values()) {
			resourceLockMap.put(resource, new ReentrantLock());
		}
	}
	
	public static void lockResource(Resource resource) {
		resourceLockMap.get(resource).lock();
	}
	
	public static void unlockResource(Resource resource) {
		resourceLockMap.get(resource).unlock();
	}
	
	private static Map<String, Object> objectCache = new ConcurrentHashMap<>();
	private static Map<String, Queue<?>> listCache = new ConcurrentHashMap<>();
	private static Map<String, Map<String, ?>> mapCache = new ConcurrentHashMap<>();
	private static Map<String, Map<?, String>> setCache = new ConcurrentHashMap<>();
	private final static String SET_PACK = ""; 
	
	private static Queue<?> getListOldCache(String key) {
		Queue<?> oldCache = listCache.get(key);
		if(oldCache == null) {
			lockResource(Resource.List);
			try {
				if(oldCache == null) {
					oldCache = getNewCacheList();
					pushCacheList(key, oldCache);
				}
			}finally {
				unlockResource(Resource.List);
			}
		}
		return oldCache;
	}
	
	private static Queue<?> getNewCacheList() {
		return new ConcurrentLinkedQueue<>();
	}
	
	private static void pushCacheList(String key, Queue<?> cache) {
		listCache.put(key, cache);
	}
	
	private static Map<String,?> getMapOldCache(String key, boolean isSorted) {
		//获取旧缓存
		Map<String, ?> oldCache = mapCache.get(key);
		if(oldCache == null) {
			lockResource(Resource.Map);
			try {
				if(oldCache == null) {
					oldCache = getNewCacheMap(isSorted);
					pushCacheMap(key, oldCache);
				}
			}finally {
				unlockResource(Resource.Map);
			}
		}
		return oldCache;
	}
	
	private static Map<String, ?> getNewCacheMap(boolean isLinked) {
		if(isLinked) {
			return Collections.synchronizedMap(new LinkedHashMap<String, Object>());
		}else {
			return new ConcurrentHashMap<>();
		}
	}
	
	private static void pushCacheMap(String key, Map<String,?> cache) {
		mapCache.put(key, cache);
	}
	
	private static Map<?, String> getSetOldCache(String key) {
		//获取旧缓存
		Map<?, String> oldCache = setCache.get(key);
		if(oldCache == null) {
			lockResource(Resource.Set);
			try {
				if(oldCache == null) {
					oldCache = getNewCacheSet();
					pushCacheSet(key, oldCache, null);
				}
			}finally {
				unlockResource(Resource.Set);
			}
		}
		return oldCache;
	}
	
	private static <T> void pushCacheSet(String key, Map<T, String> cache, Set<T> set) {
		if(set != null) {
			for(T t : set) {
				cache.put(t, SET_PACK);
			}
		}
		setCache.put(key, cache);
	}
	
	private static Map<?, String> getNewCacheSet() {
		return new ConcurrentHashMap<>();
	}

	@Override
	public boolean cacheObject(String key, Object obj) throws Exception{
		if(!checkKey(key)) {
			return false;
		}
		objectCache.put(key, obj);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCacheObject(String key, Class<? extends T> cls) throws Exception{
		if(!checkKey(key)) {
			return null;
		}
		return (T) objectCache.get(key);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean cacheList(String key, List<?> list, boolean coverOld) throws Exception{
		if(!checkKey(key)) {
			return false;
		}
		
		if(list == null || list.isEmpty()) {
			if(coverOld) {
				listCache.remove(key);
			}
			return true;
		}
		
		if(coverOld) {
			Queue cache = getNewCacheList();
			cache.addAll(list);
			pushCacheList(key, cache);
		}else {
			Queue cache = getListOldCache(key);
			cache.addAll(list);
		}
		return true;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean cacheListMember(String key, Object obj) throws Exception{
		if(obj == null || !checkKey(key)) {
			return false;
		}
		
		Queue cache = getListOldCache(key);
		cache.add(obj);
		return true;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T> List<T> getAllList(String key, Class<? extends T> cls) throws Exception{
		if(!checkKey(key)) {
			return null;
		}
		
		Queue queue = listCache.get(key);
		if(queue == null) {
			return null;
		}
		List<T> list = new ArrayList<>();
		list.addAll(queue);
		return list;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> T getListMember(String key, Class<? extends T> cls, int index) throws Exception{
		if(!checkKey(key)) {
			return null;
		}
		
		Queue queue = listCache.get(key);
		if(queue == null || queue.size() <= index) {
			return null;
		}
		Iterator<T> its = queue.iterator();
		for(int i = 0; i < index; i++) {
			its.next();
		}
		return its.next();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T> List<T> getPartialList(String key, Class<? extends T> cls, int start, int end) throws Exception{
		if(!checkKey(key) || start < 0 || end < 0 || start > end) {
			return null;
		}
		
		Queue queue = listCache.get(key);
		if(queue == null) {
			return null;
		}
		
		int max = queue.size() - 1;
		if(end > max) {
			end = max;
		}
		
		List<T> list = new ArrayList<>();
		Iterator<T> its = queue.iterator();
		for(int i = 0; i < start; i++) {
			its.next();
		}
		for(int i = start; i <= max; i++) {
			list.add(its.next());
		}
		return list;
	}

	@SuppressWarnings({"rawtypes"})
	@Override
	public <T> boolean delListMember(String key, T t) throws Exception{
		if(t == null || !checkKey(key)) {
			return false;
		}
		
		Queue queue = listCache.get(key);
		if(queue == null) {
			return true;
		}
		queue.remove(t);
		return true;
	}
	
	public boolean delList(String key) throws Exception{
		if(!checkKey(key)) {
			return false;
		}
		listCache.remove(key);
		return true;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean cacheMap(String key, Map<String, ?> map, boolean coverOld, boolean isLinked) throws Exception{
		if(!checkKey(key)) {
			return false;
		}
		
		if(map == null || map.isEmpty()) {
			if(coverOld) {
				mapCache.remove(key);
			}
			return true;
		}
		
		if(coverOld) {
			Map cache = getNewCacheMap(isLinked);
			cache.putAll(map);
			pushCacheMap(key, cache);
		}else {
			Map cache = getMapOldCache(key, isLinked);
			cache.putAll(map);
		}
		return true;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T> boolean cacheMapMember(String key, Object field, T t, boolean isLinked) throws Exception{
		if(!checkKey(key)) {
			return false;
		}
		
		Map cache = getMapOldCache(key, isLinked);
		cache.put(field, t);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Map<String, T> getAllCacheMap(String key, Class<? extends T> cls, boolean isLinked) throws Exception{
		if(!checkKey(key)) {
			return null;
		}
		
		Map<String, T> map = (Map<String, T>) mapCache.get(key);
		if(map == null) {
			return null;
		}
		if(isLinked) {
			return new LinkedHashMap<String, T>(map);
		}else {
			return new HashMap<String, T>(map);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getMapMember(String key, Object field, Class<? extends T> cls) throws Exception{
		if(!checkKey(key)) {
			return null;
		}
		
		Map<String, ?> map = mapCache.get(key);
		if(map == null) {
			return null;
		}
		Object o = map.get(field);
		if(o == null) {
			return null;
		}
		return (T) o;
	}
	
	public boolean delMapMember(String key, Object field) throws Exception{
		if(!checkKey(key)) {
			return false;
		}
		
		Map<String, ?> map = mapCache.get(key);
		if(map == null) {
			return true;
		}
		map.remove(field);
		return true;
	}
	
	public boolean delMap(String key) throws Exception{
		if(!checkKey(key)) {
			return false;
		}
		mapCache.remove(key);
		return true;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean cacheSet(String key, Set set, boolean coverOld) throws Exception{
		if(!checkKey(key)) {
			return false;
		}
		
		if(set == null || set.isEmpty()) {
			if(coverOld) {
				setCache.remove(key);
			}
			return true;
		}
		
		Map<?, String> oldcache = null;
		if(coverOld) {
			oldcache = getNewCacheSet();
		}else {
			oldcache = getSetOldCache(key);
		}
		pushCacheSet(key, oldcache, set);
		return true;
	}
	
	@SuppressWarnings({"unchecked"})
	@Override
	public <T> boolean cacheSetMember(String key, T t) throws Exception{
		if(!checkKey(key)) {
			return false;
		}
		
		Map<T, String> oldcache = (Map<T, String>) getSetOldCache(key);
		oldcache.put(t, SET_PACK);
		return true;
	}

	@SuppressWarnings({ "unchecked"})
	@Override
	public <T> Set<T> getCacheSet(String key, Class<? extends T> cls) throws Exception{
		if(!checkKey(key)) {
			return null;
		}
		
		Map<T,?> map = (Map<T, ?>) setCache.get(key);
		if(map == null) {
			return null;
		}
		return new HashSet<>(map.keySet());
	}
	
	@SuppressWarnings({ "unchecked"})
	@Override
	public <T> T getSetRandMember(String key, Class<? extends T> cls) throws Exception{
		if(!checkKey(key)) {
			return null;
		}
		
		Map<T,?> map = (Map<T, ?>) setCache.get(key);
		if(map == null) {
			return null;
		}
		
		int index = new Random().nextInt(map.size() - 1);
		
		Iterator<T> its = map.keySet().iterator();
		int i = 0;
		while(i < index && its.hasNext()) {
			i++;
			its.next();
		}
		return its.next();
	}
	
	@SuppressWarnings({ "unchecked"})
	@Override
	public <T> T popSetRandMember(String key, Class<? extends T> cls) throws Exception{
		if(!checkKey(key)) {
			return null;
		}
		
		Map<T,?> map = (Map<T, ?>) setCache.get(key);
		if(map == null) {
			return null;
		}
		
		int index = new Random().nextInt(map.size() - 1);
		
		Iterator<T> its = map.keySet().iterator();
		int i = 0;
		while(i < index && its.hasNext()) {
			i++;
			its.next();
		}
		
		T t = its.next();
		its.remove();
		return t;
	}
	
	public <T> boolean delSetMember(String key, T t) throws Exception{
		if(!checkKey(key)) {
			return false;
		}
		
		@SuppressWarnings("unchecked")
		Map<T,?> set = (Map<T, ?>) setCache.get(key);
		if(set == null) {
			return true;
		}
		set.remove(t);
		return true;
	}
	
	public boolean delSet(String key) throws Exception {
		if(!checkKey(key)) {
			return false;
		}
		
		setCache.remove(key);
		return true;
	}
	
	
}
