package com.paige.service.apigateway.routers;

import com.paige.service.apigateway.Filter.HandlerFilter;
import com.paige.service.apigateway.apiconfig.ApiServiceConfig;
import com.paige.service.apigateway.handlers.*;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

public class ApiRouter {

    private static ApiServiceConfig apiServiceConfig;
    private static ErrorHandler errorHandler;
    private static final String API_PATH = "/api";
    private static final String CONTENT_PATH = "/auth";

    public ApiRouter(ApiServiceConfig apiServiceConfig, ErrorHandler errorHandler) {
        this.apiServiceConfig = apiServiceConfig;
        this.errorHandler = errorHandler;
    }

    @Deprecated
    public static RouterFunction<?> doRoute(ApiHandler handler) {
        return
                nest(path(API_PATH),
                    nest(accept(APPLICATION_JSON),
                        route(GET(CONTENT_PATH), handler::getContentsCard)
                            .filter(new HandlerFilter())
                    ).andOther(route(RequestPredicates.all(), errorHandler::notFound))
                );
    }

    private static RouterFunction<?> bindToHomeHandler(ApiServiceConfig apiServiceConfig, HomeHandler homeHandler) {

        return RouterFunctions
                .route(GET(API_PATH + apiServiceConfig.getHome().getGet()).and(accept(APPLICATION_JSON))
                        , homeHandler::getContent)
                .andRoute(POST("/api/post/**").and(accept(APPLICATION_JSON)), homeHandler::getContent)
                .andRoute(PUT("/api/put/**").and(accept(APPLICATION_JSON)), homeHandler::getContent)
                .andRoute(DELETE("/api/delete/**").and(accept(APPLICATION_JSON)), homeHandler::getContent);
                //.filter(new HandlerFilter());
    }

    private static RouterFunction<?> bindToNewsHandler(ApiServiceConfig apiServiceConfig, NewsHandler newsHandler) {

        return RouterFunctions
                .route(GET(API_PATH + apiServiceConfig.getNews().getGet()).and(accept(APPLICATION_JSON))
                        , newsHandler::getContent)
                .andRoute(POST("/api/post/**").and(accept(APPLICATION_JSON)), newsHandler::getContent)
                .andRoute(PUT("/api/put/**").and(accept(APPLICATION_JSON)), newsHandler::getContent)
                .andRoute(DELETE("/api/delete/**").and(accept(APPLICATION_JSON)), newsHandler::getContent);
                //.filter(new HandlerFilter());
    }

    private static RouterFunction<?> bindToMatchHandler(ApiServiceConfig apiServiceConfig, MatchHandler matchHandler) {

        return RouterFunctions
                .route(GET(API_PATH + apiServiceConfig.getMatch().getGet()).and(accept(APPLICATION_JSON))
                        , matchHandler::getContent)
                .andRoute(POST("/api/post/**").and(accept(APPLICATION_JSON)), matchHandler::getContent)
                .andRoute(PUT("/api/put/**").and(accept(APPLICATION_JSON)), matchHandler::getContent)
                .andRoute(DELETE("/api/delete/**").and(accept(APPLICATION_JSON)), matchHandler::getContent);
                //.filter(new HandlerFilter());
    }

    private static RouterFunction<?> bindToRankingHandler(RankingHandler rankingHandler) {

        return RouterFunctions
                .route(GET(API_PATH + apiServiceConfig.getRank().getGet()).and(accept(APPLICATION_JSON))
                        , rankingHandler::getContent)
                .andRoute(POST("/api/post/**").and(accept(APPLICATION_JSON)), rankingHandler::getContent)
                .andRoute(PUT("/api/put/**").and(accept(APPLICATION_JSON)), rankingHandler::getContent)
                .andRoute(DELETE("/api/delete/**").and(accept(APPLICATION_JSON)), rankingHandler::getContent);
                //.filter(new HandlerFilter());
    }

    private static RouterFunction<?> bindToCommunityHandler(ApiServiceConfig apiServiceConfig, CommunityHandler communityHandler) {

        return RouterFunctions
                .route(GET(API_PATH + apiServiceConfig.getCommunity().getGet()).and(accept(APPLICATION_JSON))
                        , communityHandler::getContent)
                .andRoute(POST(API_PATH + apiServiceConfig.getCommunity().getGet()).and(accept(APPLICATION_JSON))
                        , communityHandler::postContent)
                .andRoute(PUT(API_PATH + apiServiceConfig.getCommunity().getGet()).and(accept(APPLICATION_JSON))
                        , communityHandler::putContent)
                .andRoute(DELETE(API_PATH + apiServiceConfig.getCommunity().getGet()).and(accept(APPLICATION_JSON))
                        , communityHandler::delContent);
        //.filter(new HandlerFilter());
    }

    public static RouterFunction<?> bindToHandlerEx(ApiServiceConfig apiServiceConfig, ServiceHandler serviceHandler
            , ErrorHandler errorHandler) {

        return RouterFunctions
                .route(GET(API_PATH + apiServiceConfig.getNews().getGet()).and(accept(APPLICATION_JSON))
                        , serviceHandler.getNewsHandler()::getContent)
                .filter(new HandlerFilter())
                .andOther(bindToHomeHandler(apiServiceConfig, serviceHandler.getHomeHandler()))
                .andOther(bindToMatchHandler(apiServiceConfig, serviceHandler.getMatchHandler()))
                .andOther(bindToCommunityHandler(apiServiceConfig, serviceHandler.getCommunityHandler()))
                .andOther(route(RequestPredicates.all(), errorHandler::notFound));
    }

}
