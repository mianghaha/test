package cacheservice;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Tuple;
import utils.JsonUtil;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedisUtil {
    private static Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    private static Jedis jedis = null;
    public static JedisPool jedisPool = null;
    public static boolean isOpen = false;
    public static final int STATUS_EXIST = 1;
    public static final int STATUS_NOT_EXIST = 0;
    public static final int STATUS_EXCEPTION = -1;
    public static final int DEFAULT_EXPIRE_TIME = 1000;
    public static final String DEFAULT_ERROR = "redis error";

    public static void init(boolean isOpen, String host, int port, String password, int timeout, int maxActive, int maxIdle, int maxWait) {
        JedisPoolConfig config = new JedisPoolConfig();
        // 最大连接数
        config.setMaxTotal(maxActive);
        // 最大空闲数
        config.setMaxIdle(maxIdle);
        // 超时时间
        config.setMaxWaitMillis(maxWait);
        // 从资源池借和归还连接时，先测试是否正常
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        RedisUtil.isOpen = isOpen;

        if (isOpen) {
            if (password == null || password.trim().equals("")) {
                jedisPool = new JedisPool(config, host, port);
            } else {
                if (timeout == 0)
                    timeout = DEFAULT_EXPIRE_TIME;
                jedisPool = new JedisPool(config, host, port, timeout, password);
            }
        }
    }

    public static boolean isConnect() {
        try {
            return jedisPool.getResource().isConnected();
        } catch (Exception e) {
            logger.error(DEFAULT_ERROR, e);
            return false;
        } finally {
        	if(jedis != null) {
            	jedis.close();
        	}
        }

    }

    public static int isExist(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (jedis.exists(key))
                return STATUS_EXIST;
            return STATUS_NOT_EXIST;
        } catch (Exception e) {
            logger.error(DEFAULT_ERROR, e);
            return STATUS_EXCEPTION;
        } finally {
        	if(jedis != null) {
            	jedis.close();
        	}
        }
    }

    public static Long incNUM(String key, int num) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.incrBy(key, num);
        } catch (Exception e) {
            logger.error(DEFAULT_ERROR, e);
            return -1L;
        } finally {
        	if(jedis != null) {
            	jedis.close();
        	}
        }
    }

    public static Long decNUM(String key, int num) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.decrBy(key, num);
        } catch (Exception e) {
            logger.error(DEFAULT_ERROR, e);
            return -1L;
        } finally {
        	if(jedis != null) {
            	jedis.close();
        	}
        }
    }

    public static boolean setObject(String key, int seconds, Object value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String json = JsonUtil.TransToJson(value);
            jedis.setex(key, seconds, json);
            return true;
        } catch (Exception e) {
            logger.error(DEFAULT_ERROR, e);
            return false;
        } finally {
        	if(jedis != null) {
            	jedis.close();
        	}
        }
    }

    public static boolean setString(String key, int seconds, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.setex(key, seconds, value);
            return true;
        } catch (Exception e) {
            logger.error(DEFAULT_ERROR, e);
            return false;
        } finally {
        	if(jedis != null) {
            	jedis.close();
        	}
        }
    }

    public static boolean setString(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
            return true;
        } catch (Exception e) {
            logger.error(DEFAULT_ERROR, e);
            return false;
        } finally {
        	if(jedis != null) {
            	jedis.close();
        	}
        }
    }

    public static String getString(String key) {
        Jedis jedis = null;
        String json = null;
        try {
            jedis = jedisPool.getResource();
            json = jedis.get(key);
            if (json == null || json.trim().equals("nil")) {
                return null;
            }
            return json;
        } catch (Exception e) {
            logger.error(DEFAULT_ERROR, e);
            return null;
        } finally {
        	if(jedis != null) {
            	jedis.close();
        	}
        }
    }

    public static Object getObject(String key, Class<?> cls) {
        Object result = null;
        try {
            String json = getString(key);
            if (json != null) {
                result = JsonUtil.TransToObject(json, cls);
            }
        } catch (Exception e) {
            logger.error(DEFAULT_ERROR, e);
        }
        return result;
    }

    public static boolean delete(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
            return true;
        } catch (Exception e) {
            logger.error(DEFAULT_ERROR, e);
            return false;
        } finally {
        	if(jedis != null) {
            	jedis.close();
        	}
        }
    }

    /**
     * 获得分布式锁
     * @param key
     * @return
     */
    public static boolean getDistributedLock(String key) {
        // 默认锁过期时间为60s
        int seconds = 60;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            long num = jedis.incr(key);
            // 此处设置过期时间非原子操作，但是不会影响逻辑
            jedis.expire(key, seconds);
            if (num == 1L) {
                return true;
            } else {
            	Long ttl = jedis.ttl(key);
            	//防止上次分布式锁设置过期时间失败
                if(ttl == null || ttl < 0) {
                	jedis.expire(key, seconds);
                }
                return false;
            }
        } catch (Exception e) {
            logger.error(DEFAULT_ERROR, e);
            return false;
        } finally {
        	if(jedis != null) {
            	jedis.close();
        	}
        }
    }

    public static boolean releaseDistributedLock(String key) {
        Jedis jedis = null;
        try {
            // 此处直接删除即可,因为只有得到lock的才能释放lock
            jedis = jedisPool.getResource();
            jedis.del(key);
            return true;
        } catch (Exception e) {
            logger.error(DEFAULT_ERROR, e);
            return false;
        } finally {
        	if(jedis != null) {
            	jedis.close();
        	}
        }
    }

    public static boolean lpush(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.lpush(key, value);
            return true;
        } catch (Exception e) {
            logger.error(DEFAULT_ERROR, e);
            return false;
        } finally {
        	if(jedis != null) {
            	jedis.close();
        	}
        }
    }
    
    public static boolean rpush(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.rpush(key, value);
            return true;
        } catch (Exception e) {
            logger.error(DEFAULT_ERROR, e);
            return false;
        } finally {
        	if(jedis != null) {
            	jedis.close();
        	}
        }
    }

    public static String lpop(String key) {
        Jedis jedis = null;
        String json = null;
        try {
            jedis = jedisPool.getResource();
            json = jedis.lpop(key);
        } catch (Exception e) {
            logger.error(DEFAULT_ERROR, e);
        } finally {
        	if(jedis != null) {
            	jedis.close();
        	}
        }
        if (json == null || json.trim().equals("nil")) {
            return null;
        }
        return json;
    }
    
    public static List<String> lrange(String key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.lrange(key, start, end);
        } catch (Exception e) {
            logger.error(DEFAULT_ERROR, e);
            return null;
        } finally {
        	if(jedis != null) {
            	jedis.close();
        	}
        }
    }

    public static String rpop(String key) {
        Jedis jedis = null;
        String json = null;
        try {
            jedis = jedisPool.getResource();
            json = jedis.rpop(key);
        } catch (Exception e) {
            logger.error(DEFAULT_ERROR, e);
        } finally {
        	if(jedis != null) {
            	jedis.close();
        	}
        }
        if (json == null || json.trim().equals("nil")) {
            return null;
        }
        return json;
    }

    public static String brpop(String key, int timeoutSeconds) {
        Jedis jedis = null;
        String json = null;
        try {
            jedis = jedisPool.getResource();
            List<String> jsonList = jedis.brpop(key, String.valueOf(timeoutSeconds)); // 阻塞1秒
            if (CollectionUtils.isNotEmpty(jsonList)) {
                json = jsonList.get(1);
            }
        } catch (Exception e) {
            logger.error(DEFAULT_ERROR, e);
        } finally {
        	if(jedis != null) {
            	jedis.close();
        	}
        }
        return json;
    }

    public static String ltrim(String key, int start, int end) {
        Jedis jedis = null;
        String json = null;
        try {
            jedis = jedisPool.getResource();
            json = jedis.ltrim(key, start, end);
        } catch (Exception e) {
            logger.error(DEFAULT_ERROR, e);
        } finally {
        	if(jedis != null) {
            	jedis.close();
        	}
        }
        if (json == null || json.trim().equals("nil")) {
            return null;
        }
        return json;
    }

    public static long llength(String key) {
        Jedis jedis = null;
        Long length = null;
        try {
            jedis = jedisPool.getResource();
            length = jedis.llen(key);
        } catch (Exception e) {
            logger.error(DEFAULT_ERROR, e);
        } finally {
        	if(jedis != null) {
            	jedis.close();
        	}
        }
        if (length == null) {
            return -1;
        }
        return length;
    }
    
    public static boolean lrem(String key, long count, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.lrem(key, count, value);
            return true;
        } catch (Exception e) {
            logger.error(DEFAULT_ERROR, e);
            return false;
        } finally {
        	if(jedis != null) {
            	jedis.close();
        	}
        }
    }
    
    public static String lindex(String key, long index) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.lindex(key, index);
        } catch (Exception e) {
            logger.error(DEFAULT_ERROR, e);
            return null;
        } finally {
        	if(jedis != null) {
            	jedis.close();
        	}
        }
    }

    public static Double zincrby(String key, Double score, String member) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zincrby(key, score, member);
        } catch (Exception e) {
            logger.error(DEFAULT_ERROR, e);
            return -1.0;
        } finally {
        	if(jedis != null) {
            	jedis.close();
        	}
        }
    }

    public static boolean setExpire(String key, int expireTime) {
        if (key == null || key.length() == 0) {
            return false;
        }
        // 默认过期时间为60s
        if (expireTime <= 0) {
            expireTime = DEFAULT_EXPIRE_TIME;
        }
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.expire(key, expireTime);
            return true;
        } catch (Exception e) {
            logger.error(DEFAULT_ERROR, e);
            return false;
        } finally {
        	if(jedis != null) {
            	jedis.close();
        	}
        }
    }

    public static Map<String, String> hgetAll(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hgetAll(key);
        } catch (Exception e) {
            logger.error(DEFAULT_ERROR, e);
            return null;
        } finally {
        	if(jedis != null) {
            	jedis.close();
        	}
        }
    }
    
    public static String hget(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hget(key, field);
        } catch (Exception e) {
            logger.error(DEFAULT_ERROR, e);
            return null;
        } finally {
        	if(jedis != null) {
            	jedis.close();
        	}
        }
    }

    public static Long hset(String key, String field, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hset(key, field, value);
        } catch (Exception e) {
            logger.error(DEFAULT_ERROR, e);
            return null;
        } finally {
        	if(jedis != null) {
            	jedis.close();
        	}
        }
    }

    public static Long hincrBy(String key, String field, long value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hincrBy(key, field, value);
        } catch (Exception e) {
            logger.error(DEFAULT_ERROR, e);
            return null;
        } finally {
        	if(jedis != null) {
            	jedis.close();
        	}
        }
    }

    public static boolean hdel(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.hdel(key, field);
            return true;
        } catch (Exception e) {
            logger.error(DEFAULT_ERROR, e);
            return false;
        } finally {
        	if(jedis != null) {
            	jedis.close();
        	}
        }
    }

    public static LinkedHashSet<Tuple> zrevrangeWithScores(String key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return (LinkedHashSet<Tuple>) jedis.zrevrangeWithScores(key, start, end);
        } catch (Exception e) {
            logger.error(DEFAULT_ERROR, e);
            return null;
        } finally {
        	if(jedis != null) {
            	jedis.close();
        	}
        }
    }

    public static LinkedHashSet<Tuple> zrevrangeByScoreWithScores(String key, double min, double max) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Set<Tuple> records = jedis.zrevrangeByScoreWithScores(key, max, min);
            if (CollectionUtils.isEmpty(records)) {
                return null;
            }
            return (LinkedHashSet<Tuple>) records;
        } catch (Exception e) {
            logger.error(DEFAULT_ERROR, e);
            return null;
        } finally {
        	if(jedis != null) {
            	jedis.close();
        	}
        }
    }

    public static long zrem(String key, String member) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrem(key, member);
        } catch (Exception e) {
            logger.error(DEFAULT_ERROR, e);
            return -1;
        } finally {
        	if(jedis != null) {
            	jedis.close();
        	}
        }
    }
    
    public static long sadd(String key, String member) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sadd(key, member);
        } catch (Exception e) {
            logger.error(DEFAULT_ERROR, e);
            return -1;
        } finally {
        	if(jedis != null) {
            	jedis.close();
        	}
        }
    }
    
    public static Set<String> smembers(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.smembers(key);
        } catch (Exception e) {
            logger.error(DEFAULT_ERROR, e);
            return null;
        } finally {
        	if(jedis != null) {
            	jedis.close();
        	}
        }
    }
    
    public static long srem(String key, String member) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.srem(key, member);
        } catch (Exception e) {
            logger.error(DEFAULT_ERROR, e);
            return -1;
        } finally {
        	if(jedis != null) {
            	jedis.close();
        	}
        }
    }
    
    public static String srandmember(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.srandmember(key);
        } catch (Exception e) {
            logger.error(DEFAULT_ERROR, e);
            return null;
        } finally {
        	if(jedis != null) {
            	jedis.close();
        	}
        }
    }
    
    public static String spop(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.spop(key);
        } catch (Exception e) {
            logger.error(DEFAULT_ERROR, e);
            return null;
        } finally {
        	if(jedis != null) {
            	jedis.close();
        	}
        }
    }

    public static Jedis getJedis() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis;
        } catch (Exception e) {
            logger.error(DEFAULT_ERROR, e);
            return null;
        }
    }

    public static void main(String[] args) {
        String host = "10.236.100.28";
        Integer port = 6379;
        Integer maxActive = 5;
        Integer maxIdle = 5;
        Integer maxWait = 1000;
        Integer isOpen = 1;

        JedisPoolConfig config = new JedisPoolConfig();
        // 最大连接数
        config.setMaxTotal(maxActive);
        // 最大空闲数
        config.setMaxIdle(maxIdle);
        // 超时时间
        config.setMaxWaitMillis(maxWait);

        if (isOpen.equals(1)) {
            jedisPool = new JedisPool(config, host, port);
        }

        String redisKey = "test";
        String content = RedisUtil.getString(redisKey);
        System.out.println(content);
    }
}
