package com.eightmileslab.service.apigateway.paigeservices;

import com.eightmileslab.service.apigateway.apiconfig.ApiServiceConfig;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

public class ContentsServiceImpl extends BaseService {

    public ContentsServiceImpl(final ApiServiceConfig apiServiceConfig)
    {
        super(apiServiceConfig);
    }


    @Override
    public Mono<ClientResponse> requestApi(Mono<ServerRequest> requestMono) {

        return requestMono
                .transform(this::request);
                //.transform(this::response);
    }

}
