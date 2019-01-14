package com.paige.service.apigateway.paigeservices;

import com.paige.service.apigateway.apiconfig.ApiServiceConfig;
import com.paige.service.apigateway.model.ResultEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

@Slf4j
public class UserServiceImpl extends BaseService {


    public UserServiceImpl(final ApiServiceConfig apiServiceConfig)
    {

        super(apiServiceConfig);
        webClient = WebClient.create(apiServiceConfig.getNews().getBaseurl());
    }


    @Override
    public Mono<ResultEntity> fromContents(Mono<ServerRequest> requestMono) {

        return requestMono
                .transform(this::getContent);
    }

    @Override
    protected Mono<ResultEntity> postContents(Mono<ServerRequest> requestMono) {
        return null;
    }

    @Override
    protected Mono<ResultEntity> putContents(Mono<ServerRequest> requestMono) {
        return null;
    }

    @Override
    protected Mono<ResultEntity> delContents(Mono<ServerRequest> requestMono) {
        return null;
    }

}
