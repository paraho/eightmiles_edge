package com.paige.service.apigateway.handlers;

import com.paige.service.apigateway.paigeservices.IApiService;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public abstract class BaseHandler {

    private final ErrorHandler errorHandler;
    private final IApiService contentsService;

    protected BaseHandler(final IApiService contentsService, final ErrorHandler errorHandler) {

        this.contentsService = contentsService;
        this.errorHandler = errorHandler;
    }



    public abstract Mono<ServerResponse> getContent(final ServerRequest serverRequest);
    public abstract Mono<ServerResponse> postContent(final ServerRequest serverRequest);
    public abstract Mono<ServerResponse> putContent(final ServerRequest serverRequest);
    public abstract Mono<ServerResponse> delContent(final ServerRequest serverRequest);
}

