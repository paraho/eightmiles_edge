package com.eightmileslab.service.apigateway.apiconfig;

import com.eightmileslab.service.apigateway.exceptions.ErrorHandler;
import com.eightmileslab.service.apigateway.handlers.ServiceHandler;
import com.eightmileslab.service.apigateway.routers.MainRouter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;

@Configuration
@EnableWebFlux
@ComponentScan("com.eightmileslab.service.*")
public class ApplicationConfig {

    @Bean
    ServiceHandler serviceHandler() {

        return new ServiceHandler();
    }

    @Bean
    ErrorHandler errorHandler() {

        return new ErrorHandler();
    }

    @Bean
    RouterFunction<?> mainRouterFunction(final ErrorHandler errorHandler
            , final ServiceHandler serviceHandler) {

        return MainRouter.bindToHandler(serviceHandler, errorHandler);
    }



}
