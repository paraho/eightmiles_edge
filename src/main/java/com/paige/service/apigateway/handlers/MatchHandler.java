package com.paige.service.apigateway.handlers;

import com.paige.service.apigateway.apiconfig.ServiceBuilder;
import com.paige.service.apigateway.apiconfig.ApiServiceConfig;
import com.paige.service.apigateway.paigeservices.MatchServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
public class MatchHandler extends ApiServiceHandler{

    private MatchServiceImpl contentsService;

    public MatchHandler(final ApiServiceConfig serviceConfig
            , final ServiceBuilder serviceBuilder
            , final ErrorHandler errorHandler) {
        super(serviceConfig, errorHandler, serviceBuilder);

        contentsService = (MatchServiceImpl) serviceBuilder.getMatchService();
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
