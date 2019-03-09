package com.eightmileslab.service.apigateway.apiconfig;

import com.eightmileslab.service.apigateway.repository.Post;
import com.eightmileslab.service.apigateway.model.UserSessionRedis;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.resource.ClientResources;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.*;


@SpringBootConfiguration
public class RedisConfig {

    //private @Value("${spring.redis.host}") String redisHost;
    //private @Value("${spring.redis.port}") int redisPort;
    //private @Value("${spring.redis.password}") String redisPassword;

    @Value("${spring.redis.master}")
    private String REDIS_MASTER;

    @Value("${spring.redis.pwd}")
    private String REDIS_PW;

    @Value("${spring.redis.nodes}")
    private String NODES;


    // TODO : Redis Sentinel 구성으로 변경되어야 함.
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();

        redisSentinelConfiguration.setMaster(REDIS_MASTER);

        String[] arr = NODES.split(",");
        for (int i = 0; i < arr.length; i++) {
            redisSentinelConfiguration.sentinel(arr[0].split(":")[0], Integer.parseInt(arr[0].split(":")[1]));
        }
        redisSentinelConfiguration.setPassword(RedisPassword.of(REDIS_PW));

        LettuceClientConfiguration.LettuceClientConfigurationBuilder lettuceClientConfigurationBuilder = LettuceClientConfiguration.builder();
        LettuceConnectionFactory factory = new LettuceConnectionFactory(redisSentinelConfiguration,
                lettuceClientConfigurationBuilder.build());



        return factory;
    }

    @Bean
    public ReactiveRedisTemplate<String, String> reactiveObjectRedisTemplate(
            ReactiveRedisConnectionFactory reactiveRedisConnectionFactory,
            ResourceLoader resourceLoader) {
        RedisSerializer<String> serializer = new StringRedisSerializer();
        RedisSerializationContext<String , String> serializationContext = RedisSerializationContext
                .<String, String>newSerializationContext()
                .key(serializer)
                .value(serializer)
                .hashKey(serializer)
                .hashValue(serializer)
                .build();

        return new ReactiveRedisTemplate<>(reactiveRedisConnectionFactory,
                serializationContext);
    }

    @Bean
    public ReactiveRedisTemplate<String, Object> createReactiveRedisTemplate(ReactiveRedisConnectionFactory reactiveRedisConnectionFactory) {
        RedisSerializer<String> keySerializer = new StringRedisSerializer();
        StringRedisSerializer valueSerializer = new StringRedisSerializer();
        RedisSerializationContext<String, Object> serializationContext = RedisSerializationContext
                .<String, Object>newSerializationContext(valueSerializer)
                .key(keySerializer).hashKey(keySerializer).build();

        return new ReactiveRedisTemplate<>(
                reactiveRedisConnectionFactory, serializationContext);
    }

    @Bean
    public ReactiveRedisTemplate<String, Post> reactiveJsonPostRedisTemplate(
            ReactiveRedisConnectionFactory connectionFactory) {

        RedisSerializationContext<String, Post> serializationContext = RedisSerializationContext
                .<String, Post>newSerializationContext(new StringRedisSerializer())
                .hashKey(new StringRedisSerializer())
                .hashValue(new Jackson2JsonRedisSerializer<>(Post.class))
                .build();


        return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
    }

    @Bean
    public ReactiveRedisTemplate<String, UserSessionRedis> reactiveJsonSessionRedisTemplate(
            ReactiveRedisConnectionFactory connectionFactory) {

        RedisSerializationContext<String, UserSessionRedis> serializationContext = RedisSerializationContext
                .<String, UserSessionRedis>newSerializationContext(new StringRedisSerializer())
                .hashKey(new StringRedisSerializer())
                .hashValue(new Jackson2JsonRedisSerializer<>(UserSessionRedis.class))
                .build();


        return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
    }

    /**
     * Configures a {@link ReactiveRedisTemplate} with {@link String} keys and values.
     */
    @Bean
    public ReactiveRedisTemplate<String, String> reactiveRedisTemplate(ReactiveRedisConnectionFactory connectionFactory) {
        return new ReactiveRedisTemplate<>(connectionFactory, RedisSerializationContext.string());
    }

}
