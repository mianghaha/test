package redis;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

public class PipeLineTest {

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
			Response<Set<String>> res_keys = pipe.smembers("11234");
			pipe.sync();
			
			int index = 0;
			Set<String> keys = res_keys.get();
			for(String key : keys){
				index++;
				System.out.println(index + ". " + key);
			}
			
			
			Map<String, Object> result = new HashMap<String, Object>();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		} finally {
			jedisPool.returnResource(jedis);
		}

	}

}
