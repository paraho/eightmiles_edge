package com.eightmileslab.service.apigateway.handlers;

import com.eightmileslab.service.apigateway.apiconfig.ApiServiceBuilder;
import com.eightmileslab.service.apigateway.apiconfig.ApiServiceConfig;
import com.eightmileslab.service.apigateway.exceptions.ErrorHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthHandler extends ApiServiceHandler{

    public AuthHandler(final ApiServiceConfig serviceConfig
                        , final ApiServiceBuilder serviceBuilder
                        , final ErrorHandler errorHandler) {

        super(serviceConfig, errorHandler, serviceBuilder);
        this.contentService.setBaseUrl(serviceConfig.getAuth().getBaseurl());
    }

}
