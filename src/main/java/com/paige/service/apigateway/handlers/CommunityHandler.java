package com.paige.service.apigateway.handlers;

import com.paige.service.apigateway.apiconfig.ApiServiceConfig;
import com.paige.service.apigateway.apiconfig.ServiceBuilder;
import com.paige.service.apigateway.model.ResultEntity;
import com.paige.service.apigateway.paigeservices.CommunityServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
public class CommunityHandler extends ApiServiceHandler{

    private CommunityServiceImpl contentsService;

    public CommunityHandler(final ApiServiceConfig serviceConfig
                        , final ServiceBuilder serviceBuilder
                        , final ErrorHandler errorHandler) {
        super(serviceConfig, errorHandler, serviceBuilder);

        contentsService = (CommunityServiceImpl) serviceBuilder.getCommunityServiceInst();
    }

    @Override
    public Mono<ServerResponse> getContent(ServerRequest request) {
        return Mono.just(request)
                .doOnNext(req -> log.info(req.toString()))
                .transform(this::buildContentResponse)
                .onErrorResume(errorHandler::throwableError);
    }

    @Override
    public Mono<ServerResponse> postContent(ServerRequest serverRequest) {
        return Mono.just(serverRequest)
                .doOnNext(req -> log.info(req.toString()))
                .transform(this::buildContentResponse)
                .onErrorResume(errorHandler::throwableError);
    }

    @Override
    public Mono<ServerResponse> putContent(ServerRequest serverRequest) {
        return Mono.just(serverRequest)
                .doOnNext(req -> log.info(req.toString()))
                .transform(this::buildContentResponse)
                .onErrorResume(errorHandler::throwableError);
    }

    @Override
    public Mono<ServerResponse> delContent(ServerRequest serverRequest) {
        return Mono.just(serverRequest)
                .doOnNext(req -> log.info(req.toString()))
                .transform(this::buildContentResponse)
                .onErrorResume(errorHandler::throwableError);
    }


    Mono<ServerResponse> buildContentResponse(Mono<ServerRequest> request) {
        return request
                .transform(contentsService::fromContents)
                .transform(this::response);
    }

    Mono<ServerResponse> response(Mono<ResultEntity> stringMono) {
        return stringMono.flatMap(serverResponse ->
                ServerResponse.ok().body(Mono.just(serverResponse), ResultEntity.class));
    }

}
