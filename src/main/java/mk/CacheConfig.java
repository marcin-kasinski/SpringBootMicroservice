package mk;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class CacheConfig {

	//redis-0.redis-hs.default.svc.cluster.local:6379,redis-1.redis-hs.default.svc.cluster.local:6379,redis-2.redis-hs.default.svc.cluster.local:6379,redis-3.redis-hs.default.svc.cluster.local:6379,redis-4.redis-hs.default.svc.cluster.local:6379,redis-5.redis-hs.default.svc.cluster.local:6379
	List<String> clusterNodes = Arrays.asList("redis-0.redis-hs.default.svc.cluster.local:6379", 
			"redis-1.redis-hs.default.svc.cluster.local:6379", 
			"redis-2.redis-hs.default.svc.cluster.local:6379",
			"redis-3.redis-hs.default.svc.cluster.local:6379", 
			"redis-4.redis-hs.default.svc.cluster.local:6379", 
			"redis-5.redis-hs.default.svc.cluster.local:6379");

	    @Bean
	    RedisConnectionFactory connectionFactory() {
	      return new JedisConnectionFactory(new RedisClusterConfiguration(clusterNodes));
	    }

	    @Bean
	    RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {

	      // just used StringRedisTemplate for simplicity here.
	    	    	
	      return new StringRedisTemplate(factory);
	    }
}
