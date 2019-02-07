package com.paige.service.apigateway.handlers;

import com.paige.service.apigateway.apiconfig.ApiServiceConfig;
import com.paige.service.apigateway.apiconfig.ServiceBuilder;
import com.paige.service.apigateway.exceptions.ErrorHandler;

public class ChatHandler extends ApiServiceHandler{

    public ChatHandler(final ApiServiceConfig serviceConfig
            , final ServiceBuilder serviceBuilder
            , final ErrorHandler errorHandler) {

        super(serviceConfig, errorHandler, serviceBuilder);
        this.contentService.setBaseUrl(serviceConfig.getChat().getBaseurl());
    }
}
