package com.paige.service.apigateway.application;

import com.paige.service.apigateway.apiconfig.ServiceBuilder;
import com.paige.service.apigateway.handlers.*;
import com.paige.service.apigateway.routers.MainRouter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;

@Configuration
@EnableWebFlux
public class ApplicationConfig {

    @Bean
    ServiceBuilder serviceBuilder(final ApiServiceConfig apiServiceConfig) {
        return new ServiceBuilder(apiServiceConfig);
    }

    @Bean
    HomeHandler homeHandler(final ApiServiceConfig apiServiceConfig
                                        , final ErrorHandler errorHandler
                                        , final ServiceBuilder serviceBuilder) {

        return new HomeHandler(apiServiceConfig, serviceBuilder, errorHandler);
    }

    @Bean
    NewsHandler newsHandler(final ApiServiceConfig apiServiceConfig
            , final ErrorHandler errorHandler
            , final ServiceBuilder serviceBuilder) {

        return new NewsHandler(apiServiceConfig, serviceBuilder, errorHandler);
    }

    @Bean
    MatchHandler matchHandler(final ApiServiceConfig apiServiceConfig
            , final ErrorHandler errorHandler
            , final ServiceBuilder serviceBuilder) {

        return new MatchHandler(apiServiceConfig, serviceBuilder, errorHandler);
    }

    @Bean
    RankingHandler rankingHandler(final ApiServiceConfig apiServiceConfig
            , final ErrorHandler errorHandler
            , final ServiceBuilder serviceBuilder) {

        return new RankingHandler(apiServiceConfig, serviceBuilder, errorHandler);
    }

    @Bean
    ErrorHandler errorHandler() {

        return new ErrorHandler();
    }

    @Bean
    RouterFunction<?> mainRouterFunction(final ApiServiceConfig apiServiceConfig
                                        , final ErrorHandler errorHandler
                                        , final HomeHandler homeHandler
                                        , final NewsHandler newsHandler
                                        , final MatchHandler matchHandler
                                        , final RankingHandler rankingHandler) {

        MainRouter.initialize(apiServiceConfig, errorHandler);
        return MainRouter.bindToRouter(homeHandler, newsHandler, matchHandler, rankingHandler);
    }

}
