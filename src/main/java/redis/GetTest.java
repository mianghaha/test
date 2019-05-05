package redis;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class GetTest {

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
		Jedis jedis = jedisPool.getResource();
		
//		String str = jedis.get("ssp:moment:307105710116831232");
		long len = jedis.hlen("111111111");
		System.out.println(len);
		jedisPool.returnResource(jedis);

		try {

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
