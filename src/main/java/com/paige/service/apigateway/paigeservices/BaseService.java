package com.paige.service.apigateway.paigeservices;

import com.paige.service.apigateway.apiconfig.ApiServiceConfig;
import com.paige.service.apigateway.exceptions.GetRemoteServiceException;
import com.paige.service.apigateway.model.ResultEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
public abstract class BaseService {

    private static final String ERROR_REMOTE_SERVICE = "error remote service";
    protected WebClient webClient;
    protected ApiServiceConfig apiServiceConfig;

    public BaseService(ApiServiceConfig apiServiceConfig) {

        this.apiServiceConfig = apiServiceConfig;
    }

    protected abstract Mono<ResultEntity> fromContents(Mono<ServerRequest> requestMono);

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
                //.doOnNext(this::buildHeader)
                .flatMap(url -> {

                    Map<String, String> resHeaders
                            = url.exchange().getResponse().getHeaders().toSingleValueMap();

                    Map<String, String> reqHeaders
                            = url.exchange().getRequest().getHeaders().toSingleValueMap();

                    ServerHttpRequest request = url.exchange().getRequest();

                    HttpMethod method = request.getMethod();
                    String uriInfo = url.path().replace("api/", "");
                    String queryParam = url.exchange().getRequest().getURI().getQuery();
                    if (url.exchange().getRequest().getURI().getQuery() != null)
                        uriInfo = uriInfo + "?" + queryParam;

                    WebClient.RequestBodySpec bodySpec = this.webClient.method(method)
                            .uri(uriInfo)
                            .accept(MediaType.APPLICATION_JSON)
                            .headers(httpHeaders -> {
                                httpHeaders.addAll(request.getHeaders());
                                httpHeaders.add("USER-ID", reqHeaders.get("USER-ID"));
                                httpHeaders.add("USER-LEVEL",reqHeaders.get("USER-LEVEL"));
                                httpHeaders.add("CLIENT-OS",resHeaders.get("CLIENT-OS"));
                                httpHeaders.add("CLIENT-VER",resHeaders.get("CLIENT-VER"));
                                httpHeaders.add("REQUEST-ID",resHeaders.get("REQUEST-ID"));
                                httpHeaders.add("ACCESS-TOKEN",resHeaders.get("ACCESS-TOKEN"));
                                //TODO: can this support preserviceHostHeader?
                                //httpHeaders.remove(HttpHeaders.HOST);
                            });

                    WebClient.RequestHeadersSpec<?> headersSpec;
                    headersSpec = bodySpec.body(BodyInserters.fromDataBuffers(request.getBody()));

                    Mono<ResultEntity> resultEntity =
                            headersSpec.exchange()
                                    .flatMap(clientResponse -> clientResponse.bodyToMono(ResultEntity.class));
                    return resultEntity;
                })
                .onErrorResume(throwable -> Mono.error(new GetRemoteServiceException(ERROR_REMOTE_SERVICE, throwable)));
    }


    protected Mono<String> buildUrl(final Mono<ServerRequest> urlMono) {

        return urlMono.flatMap( url -> {
            String uriInfo = url.path().replace("api/", "");
            String queryParam = url.exchange().getRequest().getURI().getQuery();
            if (url.exchange().getRequest().getURI().getQuery() != null)
                uriInfo = uriInfo + "?" + queryParam;

            return Mono.just(uriInfo);
        });

    }

    protected Mono<ResultEntity> get(final Mono<String> urlMono) {
        return urlMono.flatMap(url -> webClient
                .get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .flatMap(clientResponse -> clientResponse.bodyToMono(ResultEntity.class)));
    }

}
