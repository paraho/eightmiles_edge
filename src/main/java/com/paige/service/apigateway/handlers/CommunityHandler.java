package com.paige.service.apigateway.handlers;

import com.paige.service.apigateway.apiconfig.ApiServiceConfig;
import com.paige.service.apigateway.apiconfig.ServiceBuilder;
import com.paige.service.apigateway.exceptions.ErrorHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommunityHandler extends ApiServiceHandler{

    public CommunityHandler(final ApiServiceConfig serviceConfig
                        , final ServiceBuilder serviceBuilder
                        , final ErrorHandler errorHandler) {

        super(serviceConfig, errorHandler, serviceBuilder);
        this.contentService.setBaseUrl(serviceConfig.getCommunity().getBaseurl());
    }
}
