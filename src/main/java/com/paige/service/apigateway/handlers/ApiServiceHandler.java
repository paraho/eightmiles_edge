package com.paige.service.apigateway.handlers;

import com.paige.service.apigateway.apiconfig.ServiceBuilder;
import com.paige.service.apigateway.apiconfig.ApiServiceConfig;
import com.paige.service.apigateway.exceptions.ErrorHandler;
import com.paige.service.apigateway.model.ResultEntity;
import com.paige.service.apigateway.paigeservices.ContentsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
public abstract class ApiServiceHandler {

    public final ApiServiceConfig apiServiceConfig;
    public final ServiceBuilder serviceBuilder;
    public final ErrorHandler errorHandler;
    protected ContentsServiceImpl contentService;

    public ApiServiceHandler(final ApiServiceConfig apiServiceConfig,
                             final ErrorHandler errorHandler,
                             final ServiceBuilder serviceBuilder) {

        this.apiServiceConfig = apiServiceConfig;
        this.errorHandler = errorHandler;
        this.serviceBuilder = serviceBuilder;
        //this.contentService = (ContentsServiceImpl) serviceBuilder.getContentsService();
        this.contentService = (ContentsServiceImpl) serviceBuilder.createServiceInst();
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
                .transform(this::response)
                .onErrorResume(errorHandler::throwableError);
    }

    public Mono<ServerResponse> response(Mono<ResultEntity> stringMono) {
        return stringMono.flatMap(serverResponse ->
                ServerResponse.ok().body(Mono.just(serverResponse), ResultEntity.class));
    }

}
