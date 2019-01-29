package com.paige.service.apigateway.routers;

import com.paige.service.apigateway.Filter.HandlerFilter;
import com.paige.service.apigateway.apiconfig.ApiServiceConfig;
import com.paige.service.apigateway.handlers.ApiHandler;
import com.paige.service.apigateway.handlers.ApiServiceHandler;
import com.paige.service.apigateway.handlers.ErrorHandler;
import com.paige.service.apigateway.handlers.ServiceHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

import java.util.function.BiFunction;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Slf4j
@Component
public class ApiRouter {

    private static ApiServiceConfig apiServiceConfig;
    private static ErrorHandler errorHandler;
    private static HandlerFilter handlerFilter;
    private static final String API_PATH = "/api";
    private static final String CONTENT_PATH = "/auth";


    public ApiRouter(ApiServiceConfig apiServiceConfig, ErrorHandler errorHandler, HandlerFilter handlerFilter) {
        this.apiServiceConfig = apiServiceConfig;
        this.errorHandler = errorHandler;
        this.handlerFilter = handlerFilter;
    }

    @Deprecated
    public static RouterFunction<?> doRoute(ApiHandler handler) {
        return
                nest(path(API_PATH),
                        nest(accept(APPLICATION_JSON),
                                route(GET(CONTENT_PATH), handler::getContentsCard)
                                        .filter(handlerFilter)
                        ).andOther(route(RequestPredicates.all(), errorHandler::notFound))
                );
    }

    private static BiFunction<String,ApiServiceHandler,RouterFunction<?>> router = (config, handler) -> {
        log.info("route info = {}, {}", config, handler.toString());
        return RouterFunctions
                .route(GET(API_PATH + apiServiceConfig.createServiceInfo(config).getGet()).and(accept(APPLICATION_JSON))
                        , handler::getContent)
                .andRoute(POST(API_PATH + apiServiceConfig.createServiceInfo(config).getPost()), handler::getContent)
                .andRoute(PUT(API_PATH + apiServiceConfig.createServiceInfo(config).getPut()), handler::getContent)
                .andRoute(DELETE(API_PATH + apiServiceConfig.createServiceInfo(config).getDel()), handler::getContent)
                .filter(handlerFilter);
    };

    public static RouterFunction<?> bindToHandlerEx(ApiServiceConfig apiServiceConfig, ServiceHandler serviceHandler
            , ErrorHandler errorHandler) {

        return RouterFunctions
                .route(GET(API_PATH + apiServiceConfig.getNews().getGet()).and(accept(APPLICATION_JSON))
                        , serviceHandler.getNewsHandler()::getContent)
                .filter(handlerFilter)
                .andOther(router.apply("home", serviceHandler.getHomeHandler()))
                .andOther(router.apply("match", serviceHandler.getMatchHandler()))
                .andOther(router.apply("community", serviceHandler.getCommunityHandler()))
                .andOther(route(RequestPredicates.all(), errorHandler::notFound));
    }

}
