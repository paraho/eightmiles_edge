package com.paige.service.apigateway.handlers;

import com.paige.service.apigateway.apiconfig.ServiceBuilder;
import com.paige.service.apigateway.apiconfig.ApiServiceConfig;
import com.paige.service.apigateway.exceptions.ErrorHandler;
import com.paige.service.apigateway.paigeservices.AuthServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
public class AuthHandler extends ApiServiceHandler{

    public AuthHandler(final ApiServiceConfig serviceConfig
                        , final ServiceBuilder serviceBuilder
                        , final ErrorHandler errorHandler) {

        super(serviceConfig, errorHandler, serviceBuilder);
        this.contentService.setBaseUrl(serviceConfig.getAuth().getBaseurl());
    }

}
