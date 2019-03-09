package com.eightmileslab.service.apigateway.repository;

import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class TestRedisRepository {

    private final ReactiveRedisOperations<String, Post> template;

    public TestRedisRepository(ReactiveRedisOperations<String, Post> template) {
        this.template = template;
    }

    public Flux<Post> findAll() {
        return template.<String, Post>opsForHash().values("posts");
    }

    public Mono<Post> findById(String id) {
        return template.<String, Post>opsForHash().get("posts", id);
    }

    public Mono<Post> save(Post post) {
        if (post.getId() != null) {
            String id = UUID.randomUUID().toString();
            post.setId(id);
        }
        return template.<String, Post>opsForHash().put("posts", post.getId(), post)
                .log()
                .map(p -> post);

    }

    public Mono<Void> deleteById(String id) {
        return template.<String, Post>opsForHash().remove("posts", id)
                .flatMap(p -> Mono.<Void>empty());
    }

    public Mono<Boolean> deleteAll() {
        return template.<String, Post>opsForHash().delete("posts");
    }

}
