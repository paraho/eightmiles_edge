package com.paige.service.apigateway.handlers;

import com.paige.service.apigateway.apiconfig.ApiServiceConfig;
import com.paige.service.apigateway.apiconfig.ServiceBuilder;
import com.paige.service.apigateway.exceptions.ErrorHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TeamHandler extends ApiServiceHandler{

    public TeamHandler(final ApiServiceConfig serviceConfig
                        , final ServiceBuilder serviceBuilder
                        , final ErrorHandler errorHandler) {

        super(serviceConfig, errorHandler, serviceBuilder);
        this.contentService.setBaseUrl(serviceConfig.getTeam().getBaseurl());
    }

}