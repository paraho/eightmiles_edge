package com.paige.service.apigateway.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;

@Data
@RedisHash("session")
public class UserSessionRedis implements Serializable {

    @Id
    private String accessToken;

    @TimeToLive
    Long expiredTime;

    private String userId;
    private String deviceUuid;
    private String userLevel;
    private String os;
    private String clientVersion;
    private String team;

    @Builder
    public UserSessionRedis(String userId,
                            String accessToken,
                            String deviceUuid,
                            String userLevel,
                            String os,
                            String clientVersion,
                            String team,
                            Long expiredTime){
        this.accessToken = accessToken;
        this.userId = userId;
        this.deviceUuid = deviceUuid;
        this.userLevel = userLevel;
        this.os = os;
        this.clientVersion = clientVersion;
        this.team = team;

        this.expiredTime = expiredTime;
    }
}
