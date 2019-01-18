package com.paige.service.apigateway.paigeservices;

import com.paige.service.apigateway.model.UserSessionRedis;
import com.paige.service.apigateway.repository.UserAuthRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
@ComponentScan("com.paige.service.*")
public class AuthServiceImpl {

    private final UserAuthRepository template;

//    @Autowired
//    private SessionRedisHashRepository sessionRedisHashRepository;


    public AuthServiceImpl(UserAuthRepository template) {
        this.template = template;
    }

    public Mono<UserSessionRedis> getUserSession(String sessionId) {

        Mono<UserSessionRedis> userSessionRedis
                = template.findBySessionId(sessionId);

        return userSessionRedis;
    }

    public UserSessionRedis testSession() {

        String userId = "3333";
        String accessToken = UUID.randomUUID().toString().toUpperCase();
        String deviceUUID = "aaaaaaa3333";
        String userLevel = "GUEST";
        String os = "ios";
        String clientVersion = "1.0";
        String team = "NC";

        Long expritedTime = 20L;

        UserSessionRedis userSessionRedis = UserSessionRedis.builder()
                .userId(userId)
                .accessToken(accessToken)
                .deviceUuid(deviceUUID)
                .userLevel(userLevel)
                .os(os)
                .clientVersion(clientVersion)
                .team(team)
                .expiredTime(expritedTime.toString())
                .build();
        return userSessionRedis;
    }


}
