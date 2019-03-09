package com.eightmileslab.service.apigateway.handlers;

import com.eightmileslab.service.apigateway.apiconfig.ApiServiceBuilder;
import com.eightmileslab.service.apigateway.apiconfig.ApiServiceConfig;
import com.eightmileslab.service.apigateway.apiconfig.ServiceBuilder;
import com.eightmileslab.service.apigateway.exceptions.ErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

/**
 * API Service 정보와 인스턴스관리를 위한 클래스
 * @author      snjeong
 */
public class ServiceHandler {

    @Autowired
    ApiServiceConfig apiServiceConfig;

    @Autowired
    ApiServiceBuilder serviceBuilder;

    @Autowired
    ErrorHandler errorHandler;

    @Bean
    AuthHandler authHandler() {

        return new AuthHandler(apiServiceConfig, serviceBuilder, errorHandler);
    }

    @Bean
    BuyHandler buyHandler() {

        return new BuyHandler(apiServiceConfig, serviceBuilder, errorHandler);
    }

    @Bean
    InqHandler inqHandler() {

        return new InqHandler(apiServiceConfig, serviceBuilder, errorHandler);
    }

    @Bean
    SellHandler sellHandler() {

        return new SellHandler(apiServiceConfig, serviceBuilder, errorHandler);
    }

    @Bean
    WalletHandler walletHandler() {

        return new WalletHandler(apiServiceConfig, serviceBuilder, errorHandler);
    }

    public AuthHandler getAuthHandler() {

        return authHandler();
    }

    public BuyHandler getBuyHandler() {

        return buyHandler();
    }

    public InqHandler getInqHandler() {
        return inqHandler();
    }

    public SellHandler getSellHandler() {
        return sellHandler();
    }

    public WalletHandler getWalletHandler() {
        return walletHandler();
    }

}
