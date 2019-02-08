package com.paige.service.apigateway.paigeservices;

import com.paige.service.apigateway.apiconfig.ApiServiceConfig;
import com.paige.service.apigateway.model.ResultEntity;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

public class ContentsServiceImpl extends BaseService {

    public ContentsServiceImpl(final ApiServiceConfig apiServiceConfig)
    {
        super(apiServiceConfig);
    }


    @Override
    public Mono<ResultEntity> requestApi(Mono<ServerRequest> requestMono) {

        return requestMono
                .transform(this::request);
                //.transform(this::response);
    }

}
