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
public class UserHandler extends ApiServiceHandler{

    private NewsServiceImpl contentsService;

    public UserHandler(final ApiServiceConfig serviceConfig
                        , final ServiceBuilder serviceBuilder
                        , final ErrorHandler errorHandler) {
        super(serviceConfig, errorHandler, serviceBuilder);

        contentsService = (NewsServiceImpl) serviceBuilder.getNewsService();
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
        return null;
    }

    @Override
    public Mono<ServerResponse> putContent(ServerRequest serverRequest) {
        return null;
    }

    @Override
    public Mono<ServerResponse> delContent(ServerRequest serverRequest) {
        return null;
    }


    Mono<ServerResponse> buildContentResponse(Mono<ServerRequest> request) {
        return request
                .transform(contentsService::fromContents)
                .transform(this::response);
    }
}
