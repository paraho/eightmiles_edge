package com.paige.service.apigateway.apiconfig;

import com.paige.service.apigateway.model.UserSessionRedis;
import com.paige.service.apigateway.repository.Post;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.*;

@Configuration
public class RedisConfig {

//    @Autowired
//    RedisConnectionFactory factory;

//    @Bean
//    public LettuceConnectionFactory redisConnectionFactory() {
//        return new LettuceConnectionFactory();
//    }

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
/*
        return new ReactiveRedisTemplate<>(reactiveRedisConnectionFactory,
                serializationContext);
*/

        return new ReactiveRedisTemplate<>(
                reactiveRedisConnectionFactory, serializationContext);
    }



/*    @Bean
    public ReactiveRedisTemplate<String, Post> reactiveJsonPostRedisTemplate(
            ReactiveRedisConnectionFactory connectionFactory) {

        RedisSerializationContext<String, Post> serializationContext = RedisSerializationContext
                .<String, Post>newSerializationContext(new StringRedisSerializer())
                .hashKey(new StringRedisSerializer())
                .hashValue(new Jackson2JsonRedisSerializer<>(Post.class))
                .build();


        return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
    }*/

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

    /**
     * Configures a {@link ReactiveRedisTemplate} with {@link String} keys and a typed
     * {@link Jackson2JsonRedisSerializer}.
     */
//    @Bean
//    public ReactiveRedisTemplate<String, UserSessionRedis> reactiveJsonPersonRedisTemplate(
//            ReactiveRedisConnectionFactory connectionFactory) {
//
//        Jackson2JsonRedisSerializer<UserSessionRedis> serializer = new Jackson2JsonRedisSerializer<>(UserSessionRedis.class);
//        RedisSerializationContext.RedisSerializationContextBuilder<String, UserSessionRedis> builder = RedisSerializationContext
//                .newSerializationContext(new StringRedisSerializer());
//
//        RedisSerializationContext<String, UserSessionRedis> serializationContext = builder.value(serializer).build();
//
//        return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
//    }

//    /**
//     * Configures a {@link ReactiveRedisTemplate} with {@link String} keys and {@link GenericJackson2JsonRedisSerializer}.
//     */
//    @Bean
//    public ReactiveRedisTemplate<String, Object> reactiveJsonObjectRedisTemplate(
//            ReactiveRedisConnectionFactory connectionFactory) {
//
//        RedisSerializationContext.RedisSerializationContextBuilder<String, Object> builder = RedisSerializationContext
//                .newSerializationContext(new StringRedisSerializer());
//
//        RedisSerializationContext<String, Object> serializationContext = builder
//                .value(new GenericJackson2JsonRedisSerializer("_type")).build();
//
//        return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
//    }

//    /**
//     * Clear database before shut down.
//     */
//    public @PreDestroy
//    void flushTestDb() {
//        factory.getConnection().flushDb();
//    }
}
