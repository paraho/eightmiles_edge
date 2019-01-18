package com.paige.service.apigateway.Filter;


import com.paige.service.apigateway.handlers.ErrorHandler;
import com.paige.service.apigateway.model.ErrorResponse;
import com.paige.service.apigateway.paigeservices.AuthServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;


@Slf4j
@Component
@ComponentScan("com.paige.service.*")
public class HandlerFilter implements HandlerFilterFunction<ServerResponse, ServerResponse> {

    private ErrorHandler error;

    private final AuthServiceImpl authService;

    public static Logger logger = LogManager.getLogger("Application");

    final TransactionId.Unique uid = new TransactionId.Unique();

    public HandlerFilter(AuthServiceImpl authService) {
        this.authService = authService;
        this.error = new ErrorHandler();
    }

    /*
        ACCESS-TOKEN 이 없을 경우 Authorization Error 리턴
        ACCESS-TOKEN 이 있을 경우
         - 세션정보가 없는 경우 Authorization Error 로 반환
         - 세션정보가 있는 경우 Access Level 셋팅
    */
    @Override
    public Mono<ServerResponse> filter(ServerRequest request, HandlerFunction<ServerResponse> next) {

        Map<String, String> requestHeaders =
                request.exchange().getRequest().getHeaders().toSingleValueMap();

        if (!requestHeaders.containsKey("ACCESS-TOKEN")
            || requestHeaders.get("ACCESS-TOKEN").isEmpty()) {

            return ServerResponse.ok().body(
                    BodyInserters.fromObject(new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "")));
        }

        return this.authService.getUserSession(requestHeaders.get("ACCESS-TOKEN"))
                .flatMap(
                    sessionRedis -> {
                        if(sessionRedis == null) {
                            logger.error("[session info] : " + sessionRedis.getAccessToken());
                            return next.handle(request);
                        }

                        MultiValueMap<String, String> args = new LinkedMultiValueMap<>();
                        args.add("USER-ID", sessionRedis.getUserId());
                        args.add("USER-LEVEL",sessionRedis.getUserLevel());
                        args.add("ACCESS-TOKEN", sessionRedis.getAccessToken());

                        request.exchange().getResponse().getHeaders().addAll(args);

                        return next.handle(request);
                    }
                )
                .log()
                .onErrorResume(error::throwableError);

    }

}
