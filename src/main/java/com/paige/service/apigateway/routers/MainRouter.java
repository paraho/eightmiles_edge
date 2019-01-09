package com.paige.service.apigateway.routers;

import com.paige.service.apigateway.application.ApiServiceConfig;
import com.paige.service.apigateway.handlers.ApiHandler;
import com.paige.service.apigateway.handlers.ErrorHandler;
import com.paige.service.apigateway.handlers.ServiceHandler;
import org.springframework.web.reactive.function.server.RouterFunction;

public class MainRouter {

    /*
        중첩 라우팅 지원을 위한 용도이나, Multiple Routing 지원을 위해 보류
     */
    @Deprecated
    public static RouterFunction<?> doRoute(final ApiHandler handler, final ErrorHandler errorHandler) {
        return ApiRouter
                .doRoute(handler);
    }

    public static RouterFunction<?> bindToHandler(final ApiServiceConfig apiServiceConfig
            , final ServiceHandler serviceHandler
            , final ErrorHandler errorHandler) {

        return ApiRouter
                .bindToHandlerEx(apiServiceConfig, serviceHandler, errorHandler);
    }

}
