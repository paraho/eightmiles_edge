package com.paige.service.apigateway.handlers;

import com.paige.service.apigateway.exceptions.PathNotFoundException;
import com.paige.service.apigateway.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Slf4j
public class ErrorHandler {

    private static Logger logger = LoggerFactory.getLogger(ErrorHandler.class);
    private static final String NOT_FOUND = "not found";
    private static final String ERROR_RAISED = "error raised";

    private static BiFunction<HttpStatus,String,Mono<ServerResponse>> response =
            (status,value)-> ServerResponse.status(status).body(Mono.just(new ErrorResponse(value)),
                    ErrorResponse.class);

    public Mono<ServerResponse> throwableError(final Throwable error) {

        logger.error(ERROR_RAISED, error);
        return Mono.just(error).transform(this::getResponse);
    }

    private <T extends Throwable> Mono<ServerResponse> getResponse(final Mono<T> throwableMono) {
        return throwableMono.transform(ThrowableTranslator::translate)
                .flatMap(translation -> ServerResponse
                    .status(translation.getHttpStatus())
                    .body(
                            Mono.just(
                                    new ErrorResponse(translation.getHttpStatus().value(), translation.getMessage())), ErrorResponse.class));
    }

    public Mono<ServerResponse> notFound(final ServerRequest request) {
        return Mono.just(new PathNotFoundException(NOT_FOUND)).transform(this::getResponse);
    }

    public Mono<ServerResponse> unAuthoriztion(Throwable eroror) {
        return Mono.just(new PathNotFoundException(NOT_FOUND)).transform(this::getResponse);
    }

    public Mono<ServerResponse> interalError(Throwable eroror) {
        return Mono.just(new PathNotFoundException(NOT_FOUND)).transform(this::getResponse);
    }

    public Mono<ServerResponse> badRequest(Throwable error){
        logger.error("error raised", error);
        return response.apply(HttpStatus.BAD_REQUEST, error.getMessage());
    }

}
