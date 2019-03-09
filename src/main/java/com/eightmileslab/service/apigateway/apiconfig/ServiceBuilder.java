package com.eightmileslab.service.apigateway.apiconfig;

import com.eightmileslab.service.apigateway.paigeservices.AuthServiceImpl;
import com.eightmileslab.service.apigateway.paigeservices.BaseService;
import com.eightmileslab.service.apigateway.paigeservices.ContentsServiceImpl;
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
    BaseService getAuthService() {

        return new AuthServiceImpl(apiServiceConfig);
    }

    @Bean
    BaseService getContentService() {
        return new ContentsServiceImpl(apiServiceConfig);

    }

    public BaseService getNewsServiceInst() {

        return this.newsService;
    }

    public BaseService getHomeServiceInst() {

        return this.homeService;
    }

}
