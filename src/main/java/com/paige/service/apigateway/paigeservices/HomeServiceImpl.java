package com.paige.service.apigateway.paigeservices;

import com.paige.service.apigateway.application.ApiServiceConfig;
import com.paige.service.apigateway.model.ResultEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

@Slf4j
public class HomeServiceImpl extends BaseService {


    private final String remoteUrl1 = "http://localhost:9000/hello";

    public HomeServiceImpl(final ApiServiceConfig apiServiceConfig)
    {
        super(apiServiceConfig);
    }

    private Mono<ResultEntity> getContent(Mono<String> urlMono) {

        return urlMono.flatMap(url -> webClient
                .get()
                .uri(remoteUrl1)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .flatMap(clientResponse -> clientResponse.bodyToMono(ResultEntity.class)));
    }

    @Override
    public Mono<ResultEntity> fromContents(Mono<ServerRequest> stringMono) {

        return stringMono
                .transform(this::buildUrl)
                .transform(this::getContent);
    }

    private Mono<String> buildUrl(Mono<ServerRequest> requestMono) {

        return Mono.just("");
    }
}
