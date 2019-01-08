package com.paige.service.apigateway.handlers;

import com.paige.service.apigateway.paigeservices.IApiService;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class SimpleHandler extends BaseHandler {

    protected SimpleHandler(IApiService contentsService, ErrorHandler errorHandler) {
        super(contentsService, errorHandler);
    }

    @Override
    public Mono<ServerResponse> getContent(ServerRequest serverRequest) {
        return null;
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
}
