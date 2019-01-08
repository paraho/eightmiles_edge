package com.paige.service.apigateway.handlers;

import com.paige.service.apigateway.apiconfig.ServiceBuilder;
import com.paige.service.apigateway.application.ApiServiceConfig;
import com.paige.service.apigateway.model.ContentsCardResponse;
import com.paige.service.apigateway.model.ResultEntity;
import com.paige.service.apigateway.paigeservices.NewsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
public class RankingHandler extends ApiServiceHandler {

    private NewsServiceImpl contentsService;

    public RankingHandler(final ApiServiceConfig serviceConfig
                        , final ServiceBuilder serviceBuilder
                        , final ErrorHandler errorHandler) {
        super(serviceConfig, errorHandler, serviceBuilder);

        contentsService = (NewsServiceImpl) serviceBuilder.getRankingServiceInst();
    }

    @Override
    public Mono<ServerResponse> getContent(ServerRequest request) {
        return Mono.just(request)
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

    Mono<ServerResponse> serverResponse(Mono<ContentsCardResponse> contentsCardResponseMono) {
        return contentsCardResponseMono.flatMap(serverResponse ->
                ServerResponse.ok().body(Mono.just(serverResponse), ContentsCardResponse.class));
    }
}
