package com.paige.service.apigateway.paigeservices;

import com.paige.service.apigateway.application.ApiServiceConfig;
import com.paige.service.apigateway.model.ResultEntity;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

import java.util.Map;

public abstract class BaseService {

    WebClient webClient;
    ApiServiceConfig apiServiceConfig;

    public BaseService(ApiServiceConfig apiServiceConfig) {

        this.apiServiceConfig = apiServiceConfig;
    }

    protected abstract Mono<ResultEntity> fromContents(Mono<ServerRequest> requestMono);

    protected Mono<ResultEntity> getContent(Mono<ServerRequest> requestMono) {

        return requestMono.flatMap(url -> {

            Map<String, String> mapHeader = url.exchange().getResponse().getHeaders().toSingleValueMap();

            WebClient authClient = webClient.mutate()
                    .defaultHeaders(httpHeaders -> {
                        httpHeaders.add("USER-ID", mapHeader.get("USER-ID"));
                        httpHeaders.add("USER-LEVEL",mapHeader.get("USER-LEVEL"));
                        httpHeaders.add("CLIENT-OS",mapHeader.get("CLIENT-OS"));
                        httpHeaders.add("CLIENT-VER",mapHeader.get("CLIENT-VER"));
                        httpHeaders.add("REQUEST-ID",mapHeader.get("REQUEST-ID"));
                    })
                    .build();

            Mono<ResultEntity> resultEntity = authClient
                    .get()
                    .uri(url.path().replace("/api", ""))
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .flatMap(clientResponse -> clientResponse.bodyToMono(ResultEntity.class));

            return resultEntity;
        });
    }

}
