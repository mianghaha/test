package redis;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

public class PipeLineTest2 {

	public static void main(String[] args) {
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
		try {
			jedis = jedisPool.getResource();
			Pipeline pipe = jedis.pipelined();
			Map<String, String> keyMap = pipe.hgetAll("pipemap").get();
			for(String key : keyMap.keySet()){
				String key1 = "pipetest:" + key;
				String value1 = pipe.get(key).get();
				System.out.println("key1=" + key1 + ",value1=" + value1);
			}
			pipe.sync();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		} finally {
			jedisPool.returnResource(jedis);
		}

	}

}
