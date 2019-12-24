package redis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import utils.JsonUtil;

public class test {

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
		
//		List<String> list = jedis.lrange("test1", 0, -1);
		
		List<Response<String>> strNewsListRes = new ArrayList<Response<String>>();
		Pipeline pipeline = jedis.pipelined();
		String redisKey = "test111";
		
		for(int i = 0; i < 5; i++){
			Response<String> strNewsRes = pipeline.lpop(redisKey);
			strNewsListRes.add(strNewsRes);
		}
		pipeline.sync();

		try {
//			System.out.println(JsonUtil.TransToJson(list));
			for(Response<String> strNewsRes : strNewsListRes){
				System.out.println("strNewsRes=" + strNewsRes);
				System.out.println("strNews=" + strNewsRes.get());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
