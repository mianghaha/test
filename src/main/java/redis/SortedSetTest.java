package redis;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import utils.JsonUtil;

public class SortedSetTest {

	public static void main(String[] args){
		String host = "10.236.254.201";
		Integer port = 6379;
		int maxactive = 5;
		Integer maxidle = 5;
		Integer maxwait = 1000;
		Integer timeout = 3000;
		String password = "KeeL123s56..";
		
		JedisPoolConfig config = new JedisPoolConfig();
		// 最大连接数
		config.setMaxTotal(maxactive);
		// 最大空闲数
		config.setMaxIdle(maxidle);
		// 超时时间
		config.setMaxWaitMillis(maxwait);
		JedisPool jedisPool = new JedisPool(config, host, port, timeout, password);
		Jedis jedis = null;
		jedis = jedisPool.getResource();
		
		String key = "test1";
		long id = 354269990473433088L;
		
		jedis.del(key);
		jedis.zadd(key, id, "11111");
		
		System.out.println(jedis.zrangeByScore(key, id, id));
		jedisPool.close();
	}
}
