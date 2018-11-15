package mk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisConnectionUtils;

public class RedisCacheErrorHandler implements CacheErrorHandler {

	private static Logger log = LoggerFactory.getLogger(RedisCacheErrorHandler.class);

    @Autowired
    JedisConnectionFactory redisConnectionFactory;
    @Autowired
    RedisCacheManager cacheManager;
    
    @Override
    public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
        log.info("Unable to get from cache " + cache.getName() + " : " + exception.getMessage());
        //redisConnectionFactory.
        //cacheManager.

		//redisConnectionFactory.getConnection().

    }

    @Override
    public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
        log.info("Unable to put into cache " + cache.getName() + " : " + exception.getMessage());

    }

    @Override
    public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
        log.info("Unable to evict from cache " + cache.getName() + " : " + exception.getMessage());

    }

    @Override
    public void handleCacheClearError(RuntimeException exception, Cache cache) {
        log.info("Unable to clean cache " + cache.getName() + " : " + exception.getMessage());

    }

}
