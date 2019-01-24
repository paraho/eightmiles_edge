package com.paige.service.apigateway.apiconfig;

import com.paige.service.apigateway.paigeservices.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
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

    @Bean
    BaseService getHomeServiceInst() { return new HomeServiceImpl(apiServiceConfig); }

    @Bean
    BaseService getNewsServiceInst() {
        return new NewsServiceImpl(apiServiceConfig);
    }

    @Bean
    BaseService getMatchServiceInst() {
        return new MatchServiceImpl(apiServiceConfig);
    }

    @Bean
    BaseService getRankingServiceInst() {
        return new RankingServiceImpl(apiServiceConfig);
    }

    @Bean
    BaseService getCommunityServiceInst() {
        return new CommunityServiceImpl(apiServiceConfig);
    }


    @PostConstruct
    public void postConstruct() {

        this.homeService = getHomeServiceInst();
        this.newsService = getNewsServiceInst();
        this.matchService = getMatchServiceInst();
        this.rankingService = getRankingServiceInst();
        this.communityService = getCommunityServiceInst();
    }

}
