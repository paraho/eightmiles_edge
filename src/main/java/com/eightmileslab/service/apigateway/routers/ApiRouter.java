package com.eightmileslab.service.apigateway.routers;

import com.eightmileslab.service.apigateway.apiconfig.ApiServiceConfig;
import com.eightmileslab.service.apigateway.exceptions.ErrorHandler;
import com.eightmileslab.service.apigateway.handlers.ApiServiceHandler;
import com.eightmileslab.service.apigateway.handlers.ServiceHandler;
import com.eightmileslab.service.apigateway.Filter.RequestHandlerFilter;
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
    private static RequestHandlerFilter requestHandlerFilter;
    private static final String API_PATH = "/api";
    private static final String USER_PATH = "/signup";

    public ApiRouter(ApiServiceConfig apiServiceConfig, RequestHandlerFilter requestHandlerFilter) {
        this.apiServiceConfig = apiServiceConfig;
        this.requestHandlerFilter = requestHandlerFilter;
    }


    private static BiFunction<String, ApiServiceHandler,RouterFunction<?>> router = (serviceName, handler) -> {
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
                .filter(requestHandlerFilter);
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
                .andOther(router.apply("auth", serviceHandler.getAuthHandler()))
                .andOther(router.apply("buy", serviceHandler.getBuyHandler()))
                .andOther(router.apply("sell", serviceHandler.getSellHandler()))
                .andOther(router.apply("inq", serviceHandler.getInqHandler()))
                .andOther(router.apply("wallet", serviceHandler.getWalletHandler()))
                .andOther(route(RequestPredicates.all(), errorHandler::notFound));
    }
}
