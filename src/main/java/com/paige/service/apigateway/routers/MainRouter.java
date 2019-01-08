package com.paige.service.apigateway.routers;

import com.paige.service.apigateway.application.ApiServiceConfig;
import com.paige.service.apigateway.handlers.*;
import org.springframework.web.reactive.function.server.RouterFunction;

public class MainRouter {

    public static RouterFunction<?> doRoute(final ApiHandler handler, final ErrorHandler errorHandler) {
        return ApiRouter
                .doRoute(handler);
    }

    public static void initialize(final ApiServiceConfig apiServiceConfig, final ErrorHandler errorHandler) {
        ApiRouter apiRouter = new ApiRouter(apiServiceConfig, errorHandler);
    }


    public static RouterFunction<?> bindToRouter(final HomeHandler homeHandler
                                                , final NewsHandler newsHandler
                                                , final MatchHandler matchHandler
                                                , final RankingHandler rankingHandler) {

        //ApiRouter.bindToHomeHandler(homeHandler);
        return ApiRouter.bindToNewsHandler(newsHandler);
        //ApiRouter.bindToMatchHandler(matchHandler);
        //return ApiRouter.bindToRankingHandler(rankingHandler);
    }



}
