package com.paige.service.apigateway.handlers;

import com.paige.service.apigateway.apiconfig.ApiServiceBuilder;
import com.paige.service.apigateway.apiconfig.ApiServiceConfig;
import com.paige.service.apigateway.exceptions.ErrorHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UsersHandler extends ApiServiceHandler{

    public UsersHandler(final ApiServiceConfig serviceConfig
                        , final ApiServiceBuilder serviceBuilder
                        , final ErrorHandler errorHandler) {

        super(serviceConfig, errorHandler, serviceBuilder);
        this.contentService.setBaseUrl(serviceConfig.getAuth().getBaseurl());
    }

}