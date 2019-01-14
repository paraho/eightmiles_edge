package com.paige.service.apigateway.apiconfig;

import com.paige.service.apigateway.model.UserSessionRedis;
import com.paige.service.apigateway.repository.SessionRedisHashRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories(basePackageClasses = SessionRedisHashRepository.class)
public class RedisConfig {

//    @Autowired
//    RedisConnectionFactory factory;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName("172.20.92.133");
        redisStandaloneConfiguration.setPort(32223);
        redisStandaloneConfiguration.setPassword(RedisPassword.of("reDis12#"));

        LettuceClientConfiguration.LettuceClientConfigurationBuilder lettuceClientConfigurationBuilder = LettuceClientConfiguration.builder();
        LettuceConnectionFactory factory = new LettuceConnectionFactory(redisStandaloneConfiguration,
                lettuceClientConfigurationBuilder.build());
        return factory;
    }

    @Bean
    ReactiveRedisOperations<String, UserSessionRedis> redisOperations() {
        Jackson2JsonRedisSerializer<UserSessionRedis> serializer = new Jackson2JsonRedisSerializer<>(UserSessionRedis.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, UserSessionRedis> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, UserSessionRedis> context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(reactiveRedisConnectionFactory(), context);
    }

//    @Bean
//    RedisSerializationContext<String, String> redisSerializationContext() {}
//    RedisSerializationContext<String, String> serializationContext = RedisSerializationContext
//            .fromSerializer(new StringRedisSerializer());
//    return new ReactiveRedisTemplate<>(reactiveRedisConnectionFactory(), serializationContext);
//    }


    @Bean
    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
        return redisConnectionFactory();
    }

    @Bean
    public ReactiveRedisConnection reactiveRedisConnection(final ReactiveRedisConnectionFactory redisConnectionFactory) {
        return redisConnectionFactory.getReactiveConnection();
    }
}
