package com.eightmileslab.service.apigateway.handlers;

import com.eightmileslab.service.apigateway.apiconfig.ApiServiceBuilder;
import com.eightmileslab.service.apigateway.apiconfig.ApiServiceConfig;
import com.eightmileslab.service.apigateway.exceptions.ErrorHandler;
import com.eightmileslab.service.apigateway.paigeservices.AuthServiceImpl;
import com.eightmileslab.service.apigateway.paigeservices.ContentsServiceImpl;
import com.eightmileslab.service.apigateway.model.ResultEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
public abstract class ApiServiceHandler {

    public final ApiServiceConfig apiServiceConfig;
    public final ApiServiceBuilder serviceBuilder;
    public final ErrorHandler errorHandler;
    protected ContentsServiceImpl contentService;
    protected AuthServiceImpl authService;

    public ApiServiceHandler(final ApiServiceConfig apiServiceConfig,
                             final ErrorHandler errorHandler,
                             final ApiServiceBuilder serviceBuilder) {

        this.apiServiceConfig = apiServiceConfig;
        this.errorHandler = errorHandler;
        this.serviceBuilder = serviceBuilder;
        this.contentService = (ContentsServiceImpl) serviceBuilder.createServiceInst();
        this.authService = (AuthServiceImpl) serviceBuilder.authServiceInst();
    }

    //public abstract Mono<ServerResponse> exchange(final ServerRequest serverRequest);
    public Mono<ServerResponse> exchange(ServerRequest request) {
        return Mono.just(request)
                .doOnNext(req -> log.info(req.toString()))
                .transform(this::buildContentResponse);
    }

    Mono<ServerResponse> buildContentResponse(Mono<ServerRequest> request) {
        return request
                .transform(contentService::requestApi)
                .transform(this::clientResponse)
                .onErrorResume(errorHandler::throwableError);
    }

    @Deprecated
    public Mono<ServerResponse> response(Mono<ResultEntity> stringMono) {
        return stringMono.flatMap(serverResponse ->
                ServerResponse.ok().body(Mono.just(serverResponse), ResultEntity.class));
    }

    public Mono<ServerResponse> clientResponse(Mono<ClientResponse> clientResponse) {

        //TODO : 헤더정보 제거 되어야 함!!!!
        return clientResponse.flatMap( r -> {

            Mono<ServerResponse> response = ServerResponse.status(r.statusCode())
                    .headers(headerConsumer -> r.headers().asHttpHeaders().forEach(headerConsumer::addAll))
                    .body(r.bodyToMono(ResultEntity.class), ResultEntity.class);

            return response;
        });
    }

}
