package com.paige.service.apigateway.apiconfig;

import com.paige.service.apigateway.paigeservices.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Data
@Component
public class ServiceBuilder {

    private final ApiServiceConfig apiServiceConfig;

    private BaseService homeService;
    private BaseService newsService;
    private BaseService matchService;
    private BaseService rankingService;
    private BaseService communityService;

    public ServiceBuilder(ApiServiceConfig apiServiceConfig) {
        this.apiServiceConfig = apiServiceConfig;
    }

    @PostConstruct
    public void postConstruct() {

        this.homeService = new HomeServiceImpl(apiServiceConfig);
        this.newsService = new NewsServiceImpl(apiServiceConfig);
        this.matchService = new MatchServiceImpl(apiServiceConfig);
        this.rankingService = new RankingServiceImpl(apiServiceConfig);
        this.communityService = new CommunityServiceImpl(apiServiceConfig);
    }

}
