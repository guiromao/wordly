package co.wordly.configuration;

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

import java.time.Duration;

@Configuration
public class RedisConfiguration {

    public static final String CACHE_SEARCH_JOBS = "cacheSearchJobs";
    public static final Long CACHE_TTL_IN_MINUTES = 20L;

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(CACHE_TTL_IN_MINUTES))
                .disableCachingNullValues()
                .serializeValuesWith(SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCustomizer() {
        return builder -> {
            builder.withCacheConfiguration(CACHE_SEARCH_JOBS,
                    RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(CACHE_TTL_IN_MINUTES)));
        };
    }

}
