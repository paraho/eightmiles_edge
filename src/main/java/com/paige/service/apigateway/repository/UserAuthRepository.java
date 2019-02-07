package com.paige.service.apigateway.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paige.service.apigateway.exceptions.SessionException;
import com.paige.service.apigateway.model.UserSessionRedis;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;

@Component
public class UserAuthRepository {

    private static final String ERROR_GETTING_DATA = "[redis eror] : getting user session";
    private final ReactiveRedisTemplate<String, Object> reactiveRedisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String SESSION_KEY_PREFIX = "session:";
    private static final Long SESSION_EXPIRE_TIME = Long.valueOf(30*24*60*60);

    public UserAuthRepository(ReactiveRedisTemplate<String, Object> reactiveRedisTemplate) {
        this.reactiveRedisTemplate = reactiveRedisTemplate;
    }

    @Deprecated
    public Flux<UserSessionRedis> findAll() {
        return reactiveRedisTemplate.<String, UserSessionRedis>opsForHash().values("session");
    }

    public Mono<UserSessionRedis> findBySessionId(String sessionId) {
        return reactiveRedisTemplate.<String, Object>opsForHash().entries(SESSION_KEY_PREFIX + sessionId)
                .collectMap( entry -> entry.getKey(),
                        entry -> entry.getValue())
                .flatMap(map -> {
                    if (map.size() == 0) return null;
                    UserSessionRedis session = new UserSessionRedis();
                    session.setAccessToken(map.get("accessToken").toString());
                    session.setUserId(map.get("userId").toString());
                    session.setClientVersion(map.get("clientVer").toString());
                    session.setTeam(map.get("team").toString());
                    session.setOs(map.get("clientOS").toString());
                    session.setUserLevel(map.get("userLevel").toString());
                    session.setExpiredTime(map.get("expiredTime").toString());
                    return Mono.just(session);
                })
                .doOnSuccess(userSessionRedis -> this.setSessionTime(SESSION_KEY_PREFIX + userSessionRedis.getAccessToken()))
                .onErrorResume(throwable -> Mono.error(new SessionException(SessionException.Reason.ACCESS_DENIED, "session not found")));

    }

    public Mono<Void> setSessionTime(String sessionId) {

        return reactiveRedisTemplate.expire(sessionId, Duration.ofSeconds(SESSION_EXPIRE_TIME))
                .flatMap(aBoolean -> Mono.empty());
    }

    @Deprecated
    public Mono<String> findByField(String hashKey, String hashField) {

        return reactiveRedisTemplate.<String, String>opsForHash().get(hashKey, hashField);
    }

    @Deprecated
    public Mono<UserSessionRedis> saveSession(UserSessionRedis userSessionRedis) {

        //TODO : 테스트를 위한 용도 제거되어야 할 코드!
        if (userSessionRedis.getAccessToken() != null) {
            String id = UUID.randomUUID().toString();
            userSessionRedis.setAccessToken(id);
        }

        Map<String, Object> map = objectMapper.convertValue(userSessionRedis, Map.class);
        return reactiveRedisTemplate.<String, Object>opsForHash().putAll("session:test", map)
                .log()
                .map(p -> userSessionRedis);

    }

    @Deprecated
    public Mono<Void> deleteById(String id) {
        return reactiveRedisTemplate.<String, UserSessionRedis>opsForHash().remove("session:test", id)
                .flatMap(p -> Mono.<Void>empty());
    }

    @Deprecated
    public Mono<Boolean> deleteAll() {
        return reactiveRedisTemplate.<String, UserSessionRedis>opsForHash().delete("session:test");
    }

}
