package com.paige.service.apigateway.routers;

import com.paige.service.apigateway.Filter.HandlerFilter;
import com.paige.service.apigateway.apiconfig.ApiServiceConfig;
import com.paige.service.apigateway.exceptions.ErrorHandler;
import com.paige.service.apigateway.handlers.ApiServiceHandler;
import com.paige.service.apigateway.handlers.ServiceHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

import java.util.function.BiFunction;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Slf4j
@Component
public class ApiRouter {

    private static ApiServiceConfig apiServiceConfig;
    private static HandlerFilter handlerFilter;
    private static final String API_PATH = "/api";
    private static final String USER_PATH = "/signup";

    public ApiRouter(ApiServiceConfig apiServiceConfig, HandlerFilter handlerFilter) {
        this.apiServiceConfig = apiServiceConfig;
        this.handlerFilter = handlerFilter;
    }


    private static BiFunction<String,ApiServiceHandler,RouterFunction<?>> router = (serviceName, handler) -> {
        log.info("route info = {}, {}", serviceName, handler.toString());
        return RouterFunctions
                .route(GET(API_PATH + apiServiceConfig.getServiceInfo(serviceName).getGet())
                        .and(accept(APPLICATION_JSON))
                        , handler::exchange)
                .andRoute(POST(API_PATH + apiServiceConfig.getServiceInfo(serviceName).getPost())
                        .and(accept(APPLICATION_JSON))
                        , handler::exchange)
                .andRoute(PUT(API_PATH + apiServiceConfig.getServiceInfo(serviceName).getPut())
                        .and(accept(APPLICATION_JSON))
                        , handler::exchange)
                .andRoute(DELETE(API_PATH + apiServiceConfig.getServiceInfo(serviceName).getDel())
                        .and(accept(APPLICATION_JSON))
                        , handler::exchange)
                .filter(handlerFilter);
    };

    public static RouterFunction<?> bindToHandler(final ServiceHandler serviceHandler
            , final ErrorHandler errorHandler) {

        return RouterFunctions
                .route(GET(API_PATH + apiServiceConfig.getServiceInfo("auth").getGet())
                        .and(accept(APPLICATION_JSON))
                        , serviceHandler.getAuthHandler()::exchange)
                .andRoute(POST(API_PATH + apiServiceConfig.getServiceInfo("auth").getPost())
                        .and(accept(APPLICATION_JSON))
                        , serviceHandler.getAuthHandler()::exchange)
                .andRoute(POST(API_PATH + USER_PATH + "/**").and(accept(APPLICATION_JSON))
                        , serviceHandler.getAuthHandler()::exchange)
                .andOther(router.apply("feeds", serviceHandler.getFeedsHandler()))
                .andOther(router.apply("news", serviceHandler.getNewsHandler()))
                .andOther(router.apply("match", serviceHandler.getMatchHandler()))
                .andOther(router.apply("team", serviceHandler.getTeamHandler()))
                .andOther(router.apply("ranking", serviceHandler.getRankingHandler()))
                .andOther(router.apply("community", serviceHandler.getCommunityHandler()))
                .andOther(router.apply("quiz", serviceHandler.getQuizHandler()))
                .andOther(router.apply("notice", serviceHandler.getNoticeHandler()))
                .andOther(router.apply("chat", serviceHandler.getChatHandler()))
                .andOther(router.apply("user", serviceHandler.getUsersHandler()))
                .andOther(router.apply("culog", serviceHandler.getCulogHandler()))
                .andOther(route(RequestPredicates.all(), errorHandler::notFound));
    }
}
