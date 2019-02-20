package com.paige.service.apigateway.routers;

import com.paige.service.apigateway.apiconfig.ApiServiceConfig;
import com.paige.service.apigateway.exceptions.ErrorHandler;
import com.paige.service.apigateway.handlers.ServiceHandler;
import org.springframework.web.reactive.function.server.RouterFunction;

public class MainRouter {

    public static RouterFunction<?> bindToHandler(final ServiceHandler serviceHandler
            , final ErrorHandler errorHandler) {

        return ApiRouter
                .bindToHandler(serviceHandler, errorHandler);
    }

}
