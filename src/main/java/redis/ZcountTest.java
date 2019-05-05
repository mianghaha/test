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

public class ZcountTest {

	public static void main(String[] args){
		String host = "10.236.100.28";
		Integer port = 6379;
		int maxactive = 5;
		Integer maxidle = 5;
		Integer maxwait = 1000;
		Integer timeout = 3000;
//		String password = Config.getInstance().getRedisByKey("password");
		
		JedisPoolConfig config = new JedisPoolConfig();
		// 最大连接数
		config.setMaxTotal(maxactive);
		// 最大空闲数
		config.setMaxIdle(maxidle);
		// 超时时间
		config.setMaxWaitMillis(maxwait);
		JedisPool jedisPool = new JedisPool(config, host, port);
		Jedis jedis = null;
		jedis = jedisPool.getResource();
		
		long count = jedis.zcount("test10", 0, -1);
		
		
		
		try {
			System.out.println(JsonUtil.TransToJson(count));
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
