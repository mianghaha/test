package cacheservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import utils.JsonUtil;

public class RedisCacheService extends CacheService{
	
	private enum Resource{
		Object("cacheservice:object"),
		List("cacheservice:list"),
		Set("cacheservice:set"),
		Map("cacheservice:map")
		;
		
		Resource(String sign){
			this.sign = sign;
		}
		
		String sign;
	}
	
	private static String getRedisKey(Resource resource, String redisKey, boolean isLinked) {
		if(isLinked) {
			return serviceSign + ":" + resource.sign + ":" + redisKey + ":" + linkedSign;
		}else {
			return serviceSign + ":" + resource.sign + ":" + redisKey;
		}
	}

	@Override
	public boolean cacheObject(String key, Object obj) throws Exception{
		String strObj = JsonUtil.TransToJson(obj);
		return RedisUtil.setString(getRedisKey(Resource.Object, key, false), strObj);
	}

	@Override
	public <T> T getCacheObject(String key, Class<? extends T> cls) throws Exception{
		String strObj = RedisUtil.getString(getRedisKey(Resource.Object, key, false));
		if(strObj == null || strObj.trim().equals("")) {
			return null;
		}
		return JsonUtil.TransToObject(strObj, cls);
	}

	@Override
	public boolean cacheList(String key, List<?> list, boolean coverOld) throws Exception{
		if(!checkKey(key)) {
			return false;
		}
		
		String redisKey = getRedisKey(Resource.List, key, false);
		Jedis jedis = null;
		try {
			if(list == null || list.isEmpty()) {
				if(coverOld) {
					RedisUtil.delete(redisKey);
				}
				return true;
			}
			
			jedis = RedisUtil.getJedis();
			Pipeline pipeline = jedis.pipelined();
			if(coverOld) {
				pipeline.del(redisKey);
			}
			for(Object obj : list) {
				String strObj = JsonUtil.TransToJson(obj);
				pipeline.rpush(redisKey, strObj);
			}
			pipeline.sync();
			return true;
		}finally {
			if(jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public boolean cacheListMember(String key, Object obj) throws Exception {
		return RedisUtil.rpush(getRedisKey(Resource.List, key, false), JsonUtil.TransToJson(obj));
	}
	
	@Override
	public <T> List<T> getAllList(String key, Class<? extends T> cls) throws Exception{
		return getPartialList(key, cls, 0, -1);
	}
	
	@Override
	public <T> T getListMember(String key, Class<? extends T> cls, int index) throws Exception{
		String strMember = RedisUtil.lindex(getRedisKey(Resource.List, key, false), index);
		if(strMember == null) {
			return null;
		}
		return JsonUtil.TransToObject(strMember, cls);
	}
	
	@Override
	public <T> List<T> getPartialList(String key, Class<? extends T> cls, int start, int end) throws Exception{
		List<String> strList = RedisUtil.lrange(getRedisKey(Resource.List, key, false), start, end);
		if(strList == null) {
			return null;
		}
		
		List<T> list = new ArrayList<T>();
		for(String strObj : strList) {
			T t = JsonUtil.TransToObject(strObj, cls);
			list.add(t);
		}
		return list;
	}
	
	@Override
	public <T> boolean delListMember(String key, T t) throws Exception{
		return RedisUtil.lrem(getRedisKey(Resource.List, key, false), 0, JsonUtil.TransToJson(t));
	}
	
	@Override
	public boolean delList(String key) throws Exception{
		return RedisUtil.delete(getRedisKey(Resource.List, key, false));
	}

	@Override
	public boolean cacheMap(String key, Map<String, ?> map, boolean coverOld, boolean isLinked) throws Exception{
		if(!checkKey(key)) {
			return false;
		}
		
		Jedis jedis = null;
		try {
			String redisKey = getRedisKey(Resource.Map, key, false);
			String linkedRedisKey = getRedisKey(Resource.Map, key, true);
			if(map == null || map.isEmpty()) {
				if(coverOld) {
					RedisUtil.delete(redisKey);
					RedisUtil.delete(linkedRedisKey);
				}
				return true;
			}
			
			jedis = RedisUtil.getJedis();
			Pipeline pipeline = jedis.pipelined();
			if(coverOld) {
				pipeline.del(redisKey);
				pipeline.del(linkedRedisKey);
			}
			for(Entry<String, ?> entry : map.entrySet()) {
				String strObj = JsonUtil.TransToJson(entry.getValue());
				pipeline.hset(redisKey, entry.getKey(), strObj);
				if(isLinked) {
					pipeline.rpush(linkedRedisKey, entry.getKey());
				}
			}
			pipeline.sync();
			return true;
		}finally {
			if(jedis != null) {
				jedis.close();
			}
		}
	}
	
	@Override
	public <T> boolean cacheMapMember(String key, Object field, T t, boolean isLinked) throws Exception{
		if(field == null) {
			return false;
		}
		Jedis jedis = null;
		try {
			jedis = RedisUtil.getJedis();
			Pipeline pipeline = jedis.pipelined();
			String redisKey = getRedisKey(Resource.Map, key, false);
			
			pipeline.hset(redisKey, field.toString(), JsonUtil.TransToJson(t));
			if(isLinked) {
				String linkedRedisKey = getRedisKey(Resource.Map, key, true);
				pipeline.rpush(linkedRedisKey, field.toString());
			}
			pipeline.sync();
			return true;
		}finally {
			if(jedis != null) {
				jedis.close();
			}
		}
	}

	@Override
	public <T> Map<String, T> getAllCacheMap(String key, Class<? extends T> cls, boolean isLinked) throws Exception{
		String redisKey = getRedisKey(Resource.Map, key, false);
		Map<String, String> strMap = RedisUtil.hgetAll(redisKey);
		if(strMap == null) {
			return null;
		}
		
		Map<String, T> map = null;
		List<String> linkedKeys = null;
		if(isLinked) {
			map = new LinkedHashMap<>();
			String redisKeyLinked = getRedisKey(Resource.Map, key, true);
			linkedKeys = RedisUtil.lrange(redisKeyLinked, 0, -1); 
		}else {
			map = new HashMap<>();
		}
 
		if(linkedKeys != null) {
			for(String linkedKey : linkedKeys){
				String strObj = strMap.get(linkedKey);
				if(strObj != null) {
					T t = JsonUtil.TransToObject(strObj, cls);
					map.put(linkedKey, t);
				}
			}
		}else {
			for(Entry<String, String> entry : strMap.entrySet()) {
				T t = JsonUtil.TransToObject(entry.getValue(), cls);
				map.put(entry.getKey(), t);
			}
		}

		return map;
	}

	@Override
	public <T> T getMapMember(String key, Object field, Class<? extends T> cls) throws Exception{
		String strObj = RedisUtil.hget(getRedisKey(Resource.Map, key, false), field.toString());
		if(strObj == null || strObj.trim().equals("")) {
			return null;
		}
		
		T t = JsonUtil.TransToObject(strObj, cls);
		return t;
	}
	
	@Override
	public boolean delMapMember(String key, Object field) throws Exception{
		RedisUtil.hdel(getRedisKey(Resource.Map, key, false), field.toString());
		RedisUtil.lrem(getRedisKey(Resource.Map, key, true), 1, field.toString());
		return true;
	}
	
	@Override
	public boolean delMap(String key) throws Exception{
		RedisUtil.delete(getRedisKey(Resource.Map, key, true));
		RedisUtil.delete(getRedisKey(Resource.Map, key, false));
		return true;
	}

	@Override
	public <T> boolean cacheSet(String key, Set<T> set, boolean coverOld) throws Exception{
		Jedis jedis = null;
		try {
			String redisKey = getRedisKey(Resource.Set, key, false);
			if(set == null || set.isEmpty()) {
				if(coverOld) {
					RedisUtil.delete(redisKey);
				}
				return true;
			}
			
			jedis = RedisUtil.getJedis();
			
			Pipeline pipeline = jedis.pipelined();
			if(coverOld) {
				pipeline.del(redisKey);
			}
			for(Object obj : set) {
				String strObj = JsonUtil.TransToJson(obj);
				pipeline.sadd(redisKey, strObj);
			}
			pipeline.sync();
			return true;
		}finally {
			if(jedis != null) {
				jedis.close();
			}
		}
	}
	
	@Override
	public <T> boolean cacheSetMember(String key, T t) throws Exception{
		if(t == null) {
			return false;
		}
		return RedisUtil.sadd(getRedisKey(Resource.Set, key, false), JsonUtil.TransToJson(t)) >= 0;
	}

	@Override
	public <T> Set<T> getCacheSet(String key, Class<? extends T> cls) throws Exception{
		Set<String> strSet = RedisUtil.smembers(getRedisKey(Resource.Set, key, false));
		if(strSet == null) {
			return null;
		}
		
		Set<T> set = new HashSet<T>();
		for(String strObj : strSet) {
			T t = JsonUtil.TransToObject(strObj, cls);
			set.add(t);
		}
		return set;
	}
	
	@Override
	public <T> T getSetRandMember(String key, Class<? extends T> cls) throws Exception{
		String str = RedisUtil.srandmember(getRedisKey(Resource.Set, key, false));
		if(str == null || str.trim().equals("")) {
			return null;
		}
		return JsonUtil.TransToObject(str, cls);
	}
	
	@Override
	public <T> T popSetRandMember(String key, Class<? extends T> cls) throws Exception{
		String str = RedisUtil.spop(getRedisKey(Resource.Set, key, false));
		if(str == null || str.trim().equals("")) {
			return null;
		}
		return JsonUtil.TransToObject(str, cls);
	}
	
	@Override
	public <T> boolean delSetMember(String key, T t) throws Exception{
		return RedisUtil.srem(getRedisKey(Resource.Set, key, false), JsonUtil.TransToJson(t)) >= 0;
	}
	
	@Override
	public boolean delSet(String key) throws Exception{
		return RedisUtil.delete(getRedisKey(Resource.Set, key, false));
	}
}
