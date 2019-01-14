package com.paige.service.apigateway.paigeservices;

import com.paige.service.apigateway.apiconfig.ApiServiceConfig;
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
    protected abstract Mono<ResultEntity> postContents(Mono<ServerRequest> requestMono);
    protected abstract Mono<ResultEntity> putContents(Mono<ServerRequest> requestMono);
    protected abstract Mono<ResultEntity> delContents(Mono<ServerRequest> requestMono);

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
                        httpHeaders.add("ACCESS-TOKEN",mapHeader.get("ACCESS-TOKEN"));
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

    protected Mono<ResultEntity> postContent(Mono<ServerRequest> requestMono) {

        return requestMono.flatMap(url -> {

            Map<String, String> mapHeader = url.exchange().getResponse().getHeaders().toSingleValueMap();

            WebClient authClient = webClient.mutate()
                    .defaultHeaders(httpHeaders -> {
                        httpHeaders.add("USER-ID", mapHeader.get("USER-ID"));
                        httpHeaders.add("USER-LEVEL",mapHeader.get("USER-LEVEL"));
                        httpHeaders.add("CLIENT-OS",mapHeader.get("CLIENT-OS"));
                        httpHeaders.add("CLIENT-VER",mapHeader.get("CLIENT-VER"));
                        httpHeaders.add("REQUEST-ID",mapHeader.get("REQUEST-ID"));
                        httpHeaders.add("ACCESS-TOKEN",mapHeader.get("ACCESS-TOKEN"));
                    })
                    .build();

            Mono<ResultEntity> resultEntity = authClient
                    .post()
                    .uri(url.path().replace("/api", ""))
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .flatMap(clientResponse -> clientResponse.bodyToMono(ResultEntity.class));

            return resultEntity;
        });
    }

    protected Mono<ResultEntity> putContent(Mono<ServerRequest> requestMono) {

        return requestMono.flatMap(url -> {

            Map<String, String> mapHeader = url.exchange().getResponse().getHeaders().toSingleValueMap();

            WebClient authClient = webClient.mutate()
                    .defaultHeaders(httpHeaders -> {
                        httpHeaders.add("USER-ID", mapHeader.get("USER-ID"));
                        httpHeaders.add("USER-LEVEL",mapHeader.get("USER-LEVEL"));
                        httpHeaders.add("CLIENT-OS",mapHeader.get("CLIENT-OS"));
                        httpHeaders.add("CLIENT-VER",mapHeader.get("CLIENT-VER"));
                        httpHeaders.add("REQUEST-ID",mapHeader.get("REQUEST-ID"));
                        httpHeaders.add("ACCESS-TOKEN",mapHeader.get("ACCESS-TOKEN"));
                    })
                    .build();

            Mono<ResultEntity> resultEntity = authClient
                    .put()
                    .uri(url.path().replace("/api", ""))
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .flatMap(clientResponse -> clientResponse.bodyToMono(ResultEntity.class));

            return resultEntity;
        });
    }

    protected Mono<ResultEntity> delContent(Mono<ServerRequest> requestMono) {

        return requestMono.flatMap(url -> {

            Map<String, String> mapHeader = url.exchange().getResponse().getHeaders().toSingleValueMap();

            WebClient authClient = webClient.mutate()
                    .defaultHeaders(httpHeaders -> {
                        httpHeaders.add("USER-ID", mapHeader.get("USER-ID"));
                        httpHeaders.add("USER-LEVEL",mapHeader.get("USER-LEVEL"));
                        httpHeaders.add("CLIENT-OS",mapHeader.get("CLIENT-OS"));
                        httpHeaders.add("CLIENT-VER",mapHeader.get("CLIENT-VER"));
                        httpHeaders.add("REQUEST-ID",mapHeader.get("REQUEST-ID"));
                        httpHeaders.add("ACCESS-TOKEN",mapHeader.get("ACCESS-TOKEN"));
                    })
                    .build();

            Mono<ResultEntity> resultEntity = authClient
                    .delete()
                    .uri(url.path().replace("/api", ""))
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .flatMap(clientResponse -> clientResponse.bodyToMono(ResultEntity.class));

            return resultEntity;
        });
    }



}
