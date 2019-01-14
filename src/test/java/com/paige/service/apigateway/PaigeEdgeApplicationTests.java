package com.paige.service.apigateway;

import com.paige.service.apigateway.apiconfig.ApiServiceConfig;
import com.paige.service.apigateway.model.UserSessionRedis;
import com.paige.service.apigateway.repository.SessionRedisHashRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApiServiceConfig.class, SessionRedisHashRepository.class})
public class PaigeEdgeApplicationTests {

    @Autowired
    ApiServiceConfig apiServiceConfig;

    @Autowired
    private SessionRedisHashRepository sessionRedisHashRepository;

    @Test
    public void contextLoads() {

        System.out.println("service info : " + apiServiceConfig.getNews().getBaseurl());

    }

    @Test
    public void findById() {
        String accessToken = "A280C088-DB2B-4407-BE23-47F057C1BA64";

        UserSessionRedis redisSession = sessionRedisHashRepository.findById(accessToken).block();
        System.out.println("--log :" + redisSession.getUserId());
        System.out.println("--log :" + redisSession.getAccessToken());
        System.out.println("--log :" + redisSession.getDeviceUuid());
        System.out.println("--log :" + redisSession.getUserLevel());
        System.out.println("--log :" + redisSession.getOs());
        System.out.println("--log :" + redisSession.getClientVersion());
        System.out.println("--log :" + redisSession.getTeam());

        System.out.println("--log :" + redisSession.getExpiredTime());
    }

}

