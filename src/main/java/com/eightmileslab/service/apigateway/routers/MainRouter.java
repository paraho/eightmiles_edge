package com.eightmileslab.service.apigateway.routers;

import com.eightmileslab.service.apigateway.exceptions.ErrorHandler;
import com.eightmileslab.service.apigateway.handlers.ServiceHandler;
import org.springframework.web.reactive.function.server.RouterFunction;

public class MainRouter {

    public static RouterFunction<?> bindToHandler(final ServiceHandler serviceHandler
            , final ErrorHandler errorHandler) {

        return ApiRouter
                .bindToHandler(serviceHandler, errorHandler);
    }

}
