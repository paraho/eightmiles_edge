package com.paige.service.apigateway.paigeservices;

import com.paige.service.apigateway.application.ApiServiceConfig;
import com.paige.service.apigateway.model.ResultEntity;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

public class MatchServiceImpl extends BaseService {


    public MatchServiceImpl(final ApiServiceConfig apiServiceConfig)
    {

        super(apiServiceConfig);
        webClient = WebClient.create(apiServiceConfig.getNews().getBaseurl());
    }


    @Override
    public Mono<ResultEntity> fromContents(Mono<ServerRequest> requestMono) {

        return requestMono
                .transform(this::getContent);
    }

}
