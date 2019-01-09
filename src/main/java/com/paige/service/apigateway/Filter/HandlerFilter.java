package com.paige.service.apigateway.Filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class HandlerFilter implements HandlerFilterFunction<ServerResponse, ServerResponse> {

    @Override
    public Mono<ServerResponse> filter(ServerRequest request, HandlerFunction<ServerResponse> next) {

//        if(request.pathVariable("card").equalsIgnoreCase("test")) {
//            return ServerResponse.status(HttpStatus.FORBIDDEN).build();
//        }

        request.exchange().getResponse().getHeaders().add("test", "test");
        //request.exchange().getRequest().getHeaders().put("test", h);

        log.debug(request.toString());
        return next.handle(request);
    }
}
