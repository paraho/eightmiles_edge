package com.eightmileslab.service.apigateway.apiconfig;

import com.eightmileslab.service.apigateway.paigeservices.AuthServiceImpl;
import com.eightmileslab.service.apigateway.paigeservices.BaseService;
import com.eightmileslab.service.apigateway.paigeservices.ContentsServiceImpl;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Data
@Component
public class ApiServiceBuilder {

    private final ApiServiceConfig apiServiceConfig;

    private BaseService authService;
    private BaseService contentsService;

    public ApiServiceBuilder(ApiServiceConfig apiServiceConfig) {
        this.apiServiceConfig = apiServiceConfig;
    }

    @PostConstruct
    public void postConstruct() {

        this.authService = new AuthServiceImpl(apiServiceConfig);
        this.contentsService = new ContentsServiceImpl(apiServiceConfig);
    }

    public BaseService createServiceInst() {

        return new ContentsServiceImpl(apiServiceConfig);
    }

    public BaseService authServiceInst() {

        return new AuthServiceImpl(apiServiceConfig);
    }

}
