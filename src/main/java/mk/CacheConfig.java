package mk;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class CacheConfig extends CachingConfigurerSupport implements CachingConfigurer {

	@Value("${redis.cluster.nodes}")
	String clusterNodesProp;

	List<String> clusterNodes = new ArrayList<String>(Arrays.asList(clusterNodesProp.split(",")));
	/*
	//redis-0.redis-hs.default.svc.cluster.local:6379,redis-1.redis-hs.default.svc.cluster.local:6379,redis-2.redis-hs.default.svc.cluster.local:6379,redis-3.redis-hs.default.svc.cluster.local:6379,redis-4.redis-hs.default.svc.cluster.local:6379,redis-5.redis-hs.default.svc.cluster.local:6379
	List<String> clusterNodes = Arrays.asList("redis-0.redis-hs.default.svc.cluster.local:6379", 
			"redis-1.redis-hs.default.svc.cluster.local:6379", 
			"redis-2.redis-hs.default.svc.cluster.local:6379",
			"redis-3.redis-hs.default.svc.cluster.local:6379", 
			"redis-4.redis-hs.default.svc.cluster.local:6379", 
			"redis-5.redis-hs.default.svc.cluster.local:6379");
*/
	    @Bean
	    RedisConnectionFactory redisConnectionFactory() {
	    	
	    	 JedisPoolConfig poolConfig = new JedisPoolConfig();
             poolConfig.setMaxTotal(10);
             poolConfig.setMaxIdle(5);
             poolConfig.setMinIdle(1);
             poolConfig.setTestOnCreate(true);
             poolConfig.setTestOnBorrow(true);
             poolConfig.setTestOnReturn(true);
             poolConfig.setTestWhileIdle(true);
             poolConfig.setMaxWaitMillis(10*1000);
             poolConfig.setEvictorShutdownTimeoutMillis(10*1000);
             poolConfig.setMinEvictableIdleTimeMillis(10*1000);
             poolConfig.setTimeBetweenEvictionRunsMillis(10*1000);
             poolConfig.setNumTestsPerEvictionRun(-1);
//             poolConfig.set
	    	
	      return new JedisConnectionFactory(new RedisClusterConfiguration(clusterNodes), poolConfig);
	    }

	    @Bean
	    RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {

	      // just used StringRedisTemplate for simplicity here.
	    	    	
	      return new StringRedisTemplate(factory);
	    }
	    
	    @Bean
	    public RedisCacheConfiguration cacheConfiguration() {
	 	RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
	 	  .entryTtl(Duration.ofSeconds(600))
	 	  .disableCachingNullValues();	
	 	return cacheConfig;
	    }	    
	    
	    @Bean
	    public RedisCacheManager cacheManager() {
	 	RedisCacheManager rcm = RedisCacheManager.builder(redisConnectionFactory())
//	 	  .cacheDefaults(cacheConfiguration())
	 	  .transactionAware()
	 	  .build();
	 	return rcm;
	    }  
	    
	    @Override
	    public CacheErrorHandler errorHandler() {
	        return new RedisCacheErrorHandler();
	    }
	    
}
