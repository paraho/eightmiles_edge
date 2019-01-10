package com.paige.service.apigateway.Filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class HandlerFilter implements HandlerFilterFunction<ServerResponse, ServerResponse> {

    final TransactionId.Unique uid = new TransactionId.Unique();

    @Override
    public Mono<ServerResponse> filter(ServerRequest request, HandlerFunction<ServerResponse> next) {

//        if(request.pathVariable("card").equalsIgnoreCase("test")) {
//            return ServerResponse.status(HttpStatus.FORBIDDEN).build();
//        }

        uid.increment();

        MultiValueMap<String, String> args = new LinkedMultiValueMap<>();
        args.add("USER-ID", "baseball000001");
        args.add("USER-LEVEL","user");
        args.add("CLIENT-OS","ios");
        args.add("CLIENT-VER","1.0");
        args.add("REQUEST-ID", Long.toString(uid.value()));

        request.exchange().getResponse().getHeaders().addAll(args);

        return next.handle(request);
    }

}
