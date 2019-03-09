package com.eightmileslab.service.apigateway.Filter;


import com.eightmileslab.service.apigateway.exceptions.ErrorHandler;
import com.eightmileslab.service.apigateway.model.ErrorResponse;
import com.eightmileslab.service.apigateway.paigeservices.AuthServiceImpl;
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
@ComponentScan("com.eightmileslab.service.*")
public class RequestHandlerFilter implements HandlerFilterFunction<ServerResponse, ServerResponse> {

    private ErrorHandler error;

    private final AuthServiceImpl authService;

    public static Logger logger = LogManager.getLogger("Application");

    final LocalTransactionId.Unique uid = new LocalTransactionId.Unique();

    public RequestHandlerFilter(AuthServiceImpl authService) {
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

        String sessionId = requestHeaders.get("ACCESS-TOKEN");
        return this.authService.getUserSession(sessionId)
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
                        args.add("USER-TEAM", sessionRedis.getTeam());

                        request.exchange().getResponse().getHeaders().addAll(args);

                        logger.info("[session info]: {}", args.toString());

                        return next.handle(request);
                    }
                )
                //.log() : 디버깅시 주석제거
                .onErrorResume(error::throwableError);

    }

}
