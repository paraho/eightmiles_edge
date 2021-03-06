package com.eightmileslab.service.apigateway.handlers;

import com.eightmileslab.service.apigateway.apiconfig.ApiServiceBuilder;
import com.eightmileslab.service.apigateway.apiconfig.ApiServiceConfig;
import com.eightmileslab.service.apigateway.exceptions.ErrorHandler;

public class WalletHandler extends ApiServiceHandler{

    public WalletHandler(final ApiServiceConfig serviceConfig
            , final ApiServiceBuilder serviceBuilder
            , final ErrorHandler errorHandler) {

        super(serviceConfig, errorHandler, serviceBuilder);
        this.contentService.setBaseUrl(serviceConfig.getWallet().getBaseurl());
    }
}
