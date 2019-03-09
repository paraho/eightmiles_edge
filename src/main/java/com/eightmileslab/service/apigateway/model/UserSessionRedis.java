package com.eightmileslab.service.apigateway.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@RedisHash("session")
public class UserSessionRedis implements Serializable {

    @Id
    private String accessToken;

    @TimeToLive
    String expiredTime;

    private String userId;
    private String deviceUuid;
    private String userLevel;
    private String os;
    private String clientVersion;
    private String team;
}
