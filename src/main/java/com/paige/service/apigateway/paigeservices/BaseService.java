package com.paige.service.apigateway.paigeservices;

import com.paige.service.apigateway.apiconfig.ApiServiceConfig;
import com.paige.service.apigateway.model.ResultEntity;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

import java.util.Map;

public abstract class BaseService {

    protected WebClient webClient;
    protected ApiServiceConfig apiServiceConfig;

    public BaseService(ApiServiceConfig apiServiceConfig) {

        this.apiServiceConfig = apiServiceConfig;
    }

    protected abstract Mono<ResultEntity> fromContents(Mono<ServerRequest> requestMono);
    protected abstract Mono<ResultEntity> postContents(Mono<ServerRequest> requestMono);
    protected abstract Mono<ResultEntity> putContents(Mono<ServerRequest> requestMono);
    protected abstract Mono<ResultEntity> delContents(Mono<ServerRequest> requestMono);


    protected void buildHeader(ServerRequest requestMono) {
        Map<String, String> resHeaders
                = requestMono.exchange().getResponse().getHeaders().toSingleValueMap();

        Map<String, String> reqHeaders
                = requestMono.exchange().getRequest().getHeaders().toSingleValueMap();

        webClient
                .mutate()
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.add("USER-ID", reqHeaders.get("USER-ID"));
                    httpHeaders.add("USER-LEVEL",reqHeaders.get("USER-LEVEL"));
                    httpHeaders.add("CLIENT-OS",resHeaders.get("CLIENT-OS"));
                    httpHeaders.add("CLIENT-VER",resHeaders.get("CLIENT-VER"));
                    httpHeaders.add("REQUEST-ID",resHeaders.get("REQUEST-ID"));
                    httpHeaders.add("ACCESS-TOKEN",resHeaders.get("ACCESS-TOKEN"));
                })
                .build();
    }

    @SuppressWarnings("Duplicates")
    protected Mono<ResultEntity> getContent(Mono<ServerRequest> requestMono) {

        return requestMono
                .doOnNext(this::buildHeader)
                .flatMap(url -> {

                    String uriInfo = url.path().replace("api/", "");
                    String queryParam = url.exchange().getRequest().getURI().getQuery();
                    if (url.exchange().getRequest().getURI().getQuery() != null)
                        uriInfo = uriInfo + "?" + queryParam;

                    Mono<ResultEntity> resultEntity = webClient
                            .get()
                            .uri(uriInfo)
                            .accept(MediaType.APPLICATION_JSON)
                            .exchange()
                            .flatMap(clientResponse -> clientResponse.bodyToMono(ResultEntity.class));
                    return resultEntity;
                });
    }


    @SuppressWarnings("Duplicates")
    protected Mono<ResultEntity> postContent(Mono<ServerRequest> requestMono) {

        return requestMono
                .doOnNext(this::buildHeader)
                .flatMap(url -> {

                    String uriInfo = url.path().replace("api/", "");
                    String queryParam = url.exchange().getRequest().getURI().getQuery();
                    if (url.exchange().getRequest().getURI().getQuery() != null)
                        uriInfo = uriInfo + "?" + queryParam;

                    Mono<ResultEntity> resultEntity = webClient
                            .post()
                            .uri(uriInfo)
                            .accept(MediaType.APPLICATION_JSON)
                            .exchange()
                            .flatMap(clientResponse -> clientResponse.bodyToMono(ResultEntity.class));
                    return resultEntity;
                });
    }

    @SuppressWarnings("Duplicates")
    protected Mono<ResultEntity> putContent(Mono<ServerRequest> requestMono) {

        return requestMono
                .doOnNext(this::buildHeader)
                .flatMap(url -> {

                    String uriInfo = url.path().replace("api/", "");
                    String queryParam = url.exchange().getRequest().getURI().getQuery();
                    if (url.exchange().getRequest().getURI().getQuery() != null)
                        uriInfo = uriInfo + "?" + queryParam;

                    Mono<ResultEntity> resultEntity = webClient
                            .put()
                            .uri(uriInfo)
                            .accept(MediaType.APPLICATION_JSON)
                            .exchange()
                            .flatMap(clientResponse -> clientResponse.bodyToMono(ResultEntity.class));
                    return resultEntity;
                });
    }

    @SuppressWarnings("Duplicates")
    protected Mono<ResultEntity> delContent(Mono<ServerRequest> requestMono) {

        return requestMono
                .doOnNext(this::buildHeader)
                .flatMap(url -> {

                    String uriInfo = url.path().replace("api/", "");
                    String queryParam = url.exchange().getRequest().getURI().getQuery();
                    if (url.exchange().getRequest().getURI().getQuery() != null)
                        uriInfo = uriInfo + "?" + queryParam;

                    Mono<ResultEntity> resultEntity = webClient
                            .delete()
                            .uri(uriInfo)
                            .accept(MediaType.APPLICATION_JSON)
                            .exchange()
                            .flatMap(clientResponse -> clientResponse.bodyToMono(ResultEntity.class));
                    return resultEntity;
                });
    }

}
