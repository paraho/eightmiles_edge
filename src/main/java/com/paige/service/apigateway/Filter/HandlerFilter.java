package com.paige.service.apigateway.Filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;



public class HandlerFilter implements HandlerFilterFunction<ServerResponse, ServerResponse> {

    public static Logger logger = LogManager.getLogger("Application");

    final TransactionId.Unique uid = new TransactionId.Unique();

    @Override
    public Mono<ServerResponse> filter(ServerRequest request, HandlerFunction<ServerResponse> next) {

//        if(request.pathVariable("card").equalsIgnoreCase("test")) {
//            return ServerResponse.status(HttpStatus.FORBIDDEN).build();
//        }

        /*
        Map<String, String> requestHeaders =
                request.exchange().getRequest().getHeaders().toSingleValueMap();

        for (String key : requestHeaders.keySet()) {
            logger.info("[Request Info] :{} = {}", key, requestHeaders.get(key));
        }

        MultiValueMap<String, String> args = new LinkedMultiValueMap<>();
        args.add("USER-ID", "baseball000001");
        args.add("USER-LEVEL","user");
        args.add("CLIENT-OS",
                requestHeaders.containsKey("CLIENT-OS") == true ? requestHeaders.get("CLIENT-OS") : "");
        args.add("CLIENT-VER",
                requestHeaders.containsKey("CLIENT-VER") == true ? requestHeaders.get("CLIENT-VER") : "");
        args.add("REQUEST-ID", Long.toString(uid.value()));
        args.add("ACCESS-TOKEN", "secret-key");

        request.exchange().getResponse().getHeaders().addAll(args);
*/
        return next.handle(request);
    }

}
