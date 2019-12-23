package cacheservice;

import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class CacheService {

	private static CacheService cache;
	protected static String serviceSign;
	protected static String linkedSign = "linked";
	
	public static void init(CacheService service, String serviceSign) {
		CacheService.cache = service;
		CacheService.serviceSign = serviceSign;
	}
	
	public static CacheService getInstance() {
		return cache;
	}

	public abstract boolean cacheObject(String key, Object obj) throws Exception;
	public abstract <T> T getCacheObject(String key, Class<? extends T> cls) throws Exception;
	
	public abstract boolean cacheList(String key, List<?> list, boolean coverOld) throws Exception;
	public abstract boolean cacheListMember(String key, Object obj) throws Exception;
	public abstract <T> List<T> getAllList(String key, Class<? extends T> cls) throws Exception;
	public abstract <T> T getListMember(String key, Class<? extends T> cls, int index) throws Exception;
	public abstract <T> List<T> getPartialList(String key, Class<? extends T> cls, int start, int end) throws Exception;
	public abstract <T> boolean delListMember(String key, T t) throws Exception;
	public abstract boolean delList(String key) throws Exception;
	
	public abstract boolean cacheMap(String key, Map<String, ?> map, boolean coverOld, boolean isLinked) throws Exception;
	public abstract <T> boolean cacheMapMember(String key, Object field, T t, boolean isLinked) throws Exception;
	public abstract <T> Map<String, T> getAllCacheMap(String key, Class<? extends T> cls, boolean isLinked) throws Exception;
	public abstract <T> T getMapMember(String key, Object field, Class<? extends T> cls) throws Exception;
	public abstract boolean delMapMember(String key, Object field) throws Exception;
	public abstract boolean delMap(String key) throws Exception;
	
	public abstract <T> boolean cacheSet(String key, Set<T> set, boolean coverOld) throws Exception;
	public abstract <T> boolean cacheSetMember(String key, T t) throws Exception;
	public abstract <T> Set<T> getCacheSet(String key, Class<? extends T> cls) throws Exception;
	public abstract <T> T getSetRandMember(String key, Class<? extends T> cls) throws Exception;
	public abstract <T> T popSetRandMember(String key, Class<? extends T> cls) throws Exception;
	public abstract <T> boolean delSetMember(String key, T t) throws Exception;
	public abstract boolean delSet(String key) throws Exception;
	
	boolean checkKey(String key) {
		return (key != null && !key.trim().equals(""));
	}
}
