package com.paige.service.apigateway.handlers;

import com.paige.service.apigateway.exceptions.PathNotFoundException;
import com.paige.service.apigateway.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
public class ErrorHandler {

    private static final String NOT_FOUND = "not found";
    private static final String ERROR_RAISED = "error raised";

    public Mono<ServerResponse> throwableError(final Throwable error) {

        log.error(ERROR_RAISED, error);
        return Mono.just(error).transform(this::getResponse);
    }

    private <T extends Throwable> Mono<ServerResponse> getResponse(final Mono<T> throwableMono) {
        return throwableMono.transform(ThrowableTranslator::translate)
                .flatMap(translation -> ServerResponse
                    .status(translation.getHttpStatus())
                    .body(Mono.just(new ErrorResponse(translation.getHttpStatus().value(), translation.getMessage())), ErrorResponse.class));
    }

    public Mono<ServerResponse> notFound(final ServerRequest request) {
        return Mono.just(new PathNotFoundException(NOT_FOUND)).transform(this::getResponse);
    }

}
