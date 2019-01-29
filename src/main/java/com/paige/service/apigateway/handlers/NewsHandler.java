package com.paige.service.apigateway.handlers;

import com.paige.service.apigateway.apiconfig.ServiceBuilder;
import com.paige.service.apigateway.apiconfig.ApiServiceConfig;
import com.paige.service.apigateway.model.ResultEntity;
import com.paige.service.apigateway.paigeservices.NewsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
public class NewsHandler extends ApiServiceHandler{

    private NewsServiceImpl contentsService;

    public NewsHandler(final ApiServiceConfig serviceConfig
                        , final ServiceBuilder serviceBuilder
                        , final ErrorHandler errorHandler) {
        super(serviceConfig, errorHandler, serviceBuilder);

        contentsService = (NewsServiceImpl) serviceBuilder.getNewsService();
    }

    @Override
    public Mono<ServerResponse> getContent(ServerRequest request) {
        return Mono.just(request)
                .doOnNext(req -> log.info(req.toString()))
                .transform(this::buildContentResponse);
    }


    Mono<ServerResponse> buildContentResponse(Mono<ServerRequest> request) {
        return request
                .transform(contentsService::fromContents)
                .transform(this::response)
                .onErrorResume(errorHandler::throwableError);
    }

}
