package com.paige.service.apigateway.handlers;

import com.paige.service.apigateway.paigeservices.ContentsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


@Slf4j
public class ApiHandler {

    private static final String CARD = "card";
    private final ErrorHandler errorHandler;
    private final ContentsService contentsService;

    public ApiHandler(final ContentsService contentsService, final ErrorHandler errorHandler)
    {
        this.errorHandler = errorHandler;
        this.contentsService = contentsService;
    }

    public Mono<ServerResponse> getContentsCard(final ServerRequest request) {
        return Mono.just(request.pathVariable(CARD))
                .transform(this::buildContentResponse)
                .onErrorResume(errorHandler::throwableError);
    }

    Mono<ServerResponse> buildContentResponse(Mono<String> card) {
        return card
                .transform(contentsService::fromContents)
                .doOnNext( res -> log.info(res.toString()))
                .transform(this::remoteService1);
    }

    Mono<ServerResponse> remoteService1(Mono<String> stringMono) {
        return stringMono.flatMap(serverResponse ->
                ServerResponse.ok().body(Mono.just(serverResponse), String.class));
    }


}
