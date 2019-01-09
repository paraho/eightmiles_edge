package com.paige.service.apigateway.handlers;

import com.paige.service.apigateway.apiconfig.ServiceBuilder;
import com.paige.service.apigateway.application.ApiServiceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

/**
 * API Service 정보와 인스턴스관리를 위한 클래스
 * @author      snjeong
 */
public class ServiceHandler {

    @Autowired
    ApiServiceConfig apiServiceConfig;

    @Autowired
    ServiceBuilder serviceBuilder;

    @Autowired
    ErrorHandler errorHandler;

    @Bean
    HomeHandler homeHandler() {

        return new HomeHandler(apiServiceConfig, serviceBuilder, errorHandler);
    }

    @Bean
    NewsHandler newsHandler() {

        return new NewsHandler(apiServiceConfig, serviceBuilder, errorHandler);
    }

    @Bean
    MatchHandler matchHandler() {

        return new MatchHandler(apiServiceConfig, serviceBuilder, errorHandler);
    }

    @Bean
    RankingHandler rankingHandler() {

        return new RankingHandler(apiServiceConfig, serviceBuilder, errorHandler);
    }

    public HomeHandler getHomeHandler() {
        return homeHandler();
    }

    public NewsHandler getNewsHandler() {
        return newsHandler();
    }

    public MatchHandler getMatchHandler() {
        return matchHandler();
    }

    public RankingHandler getRankingHandler() {
        return rankingHandler();
    }

}
