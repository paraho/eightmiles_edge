package com.paige.service.apigateway;

import com.paige.service.apigateway.apiconfig.ApiServiceConfig;
import com.paige.service.apigateway.model.UserSessionRedis;
import com.paige.service.apigateway.repository.UserAuthRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApiServiceConfig.class, UserAuthRepository.class})
public class PaigeEdgeApplicationTests {

    @Autowired
    ApiServiceConfig apiServiceConfig;

    @Autowired
    UserAuthRepository sessionRedisHashRepository;

    @Test
    public void contextLoads() {

        System.out.println("service info : " + apiServiceConfig.getNews().getBaseurl());

    }

    @Test
    public void findById() {
        String accessToken = "A280C088-DB2B-4407-BE23-47F057C1BA64";

        Mono<UserSessionRedis> redisSession = sessionRedisHashRepository.findBySessionId(accessToken);

//        redisSession.flatMap()
//                .switchIfEmpty(System.out::println)
//                .log()
//                .subscribe();

/*
        System.out.println("--log :" + redisSession.getUserId());
        System.out.println("--log :" + redisSession.getAccessToken());
        System.out.println("--log :" + redisSession.getDeviceUuid());
        System.out.println("--log :" + redisSession.getUserLevel());
        System.out.println("--log :" + redisSession.getOs());
        System.out.println("--log :" + redisSession.getClientVersion());
        System.out.println("--log :" + redisSession.getTeam());

        System.out.println("--log :" + redisSession.getExpiredTime());*/


/*
        String userId = "3333";
        String accessToken = "11111";
        String deviceUUID = "111111";
        String userLevel = "USER";
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
                //.expiredTime(expritedTime)
                .build();


        this.posts
                .deleteAll()
                .thenMany(
                        Flux
                                .just("Post one", "Post two")
                                .flatMap(
                                        title -> {
                                            String id = UUID.randomUUID().toString();
                                            return this.posts.save(Post.builder().id(id).title(title).content("content of " + title).build());
                                        }
                                )
                )
                .log()
                .subscribe(
                        null,
                        null,
                        () -> log.info("done initialization...")
                );
*/


/*        this.authRepository
                .deleteAll()
                .thenMany(
                        Flux
                                .just("Post one", "Post two")
                                .flatMap(
                                        title -> {
                                            String id = UUID.randomUUID().toString();
                                            return this.authRepository.saveSession(userSessionRedis);
                                        }
                                )
                )
                .log()
                .subscribe(
                        null,
                        null,
                        () -> log.info("done initialization...")
                );*/

    }

}

