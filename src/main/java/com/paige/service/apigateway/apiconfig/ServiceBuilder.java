package com.paige.service.apigateway.apiconfig;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.paige.service.apigateway.paigeservices.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
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
    BaseService getHomeService() {

        return new HomeServiceImpl(apiServiceConfig);
    }

    @Bean
    BaseService getNewsService() {
        return new NewsServiceImpl(apiServiceConfig);
    }

    @Bean
    BaseService getMatchService() {
        return new MatchServiceImpl(apiServiceConfig);
    }

    @Bean
    BaseService getRankingService() {
        return new RankingServiceImpl(apiServiceConfig);
    }

    @Bean
    BaseService getCommunityService() {
        return new CommunityServiceImpl(apiServiceConfig);
    }

    @PostConstruct
    public void postConstruct() {

        this.homeService = getHomeService();
        this.newsService = getNewsService();
        this.matchService = getMatchService();
        this.communityService = getCommunityService();
    }

    public BaseService getNewsServiceInst() {

        return this.newsService;
    }

    public BaseService getHomeServiceInst() {

        return this.homeService;
    }

    public BaseService getMatchServiceInst() {

        return this.matchService;
    }

    public BaseService getRankingServiceInst() {

        return this.rankingService;
    }

    public BaseService getCommunityServiceInst() {

        return this.communityService;
    }

}
