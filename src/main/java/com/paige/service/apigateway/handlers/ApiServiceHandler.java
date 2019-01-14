package com.paige.service.apigateway.handlers;

import com.paige.service.apigateway.apiconfig.ServiceBuilder;
import com.paige.service.apigateway.apiconfig.ApiServiceConfig;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public abstract class ApiServiceHandler {

    public final ApiServiceConfig apiServiceConfig;
    public final ServiceBuilder serviceBuilder;
    public final ErrorHandler errorHandler;

    public ApiServiceHandler(final ApiServiceConfig apiServiceConfig,
                             final ErrorHandler errorHandler,
                             final ServiceBuilder serviceBuilder) {

        this.apiServiceConfig = apiServiceConfig;
        this.errorHandler = errorHandler;
        this.serviceBuilder = serviceBuilder;
    }

    public abstract Mono<ServerResponse> getContent(final ServerRequest serverRequest);
    public abstract Mono<ServerResponse> postContent(final ServerRequest serverRequest);
    public abstract Mono<ServerResponse> putContent(final ServerRequest serverRequest);
    public abstract Mono<ServerResponse> delContent(final ServerRequest serverRequest);

}
