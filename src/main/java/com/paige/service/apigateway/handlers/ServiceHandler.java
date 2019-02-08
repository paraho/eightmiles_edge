package com.paige.service.apigateway.handlers;

import com.paige.service.apigateway.apiconfig.ApiServiceBuilder;
import com.paige.service.apigateway.apiconfig.ApiServiceConfig;
import com.paige.service.apigateway.exceptions.ErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

/**
 * API Service 정보와 인스턴스관리를 위한 클래스
 * @author      snjeong
 */
public class ServiceHandler {

    @Autowired
    ApiServiceConfig apiServiceConfig;

    @Autowired
    ApiServiceBuilder serviceBuilder;

    @Autowired
    ErrorHandler errorHandler;

    @Bean
    AuthHandler authHandler() {
        return new AuthHandler(apiServiceConfig, serviceBuilder, errorHandler);
    }

    @Bean
    FeedsHandler feedsHandler() {
        return new FeedsHandler(apiServiceConfig, serviceBuilder, errorHandler);
    }

    @Bean
    NewsHandler newsHandler() {
        return new NewsHandler(apiServiceConfig, serviceBuilder, errorHandler);
    }

    @Bean
    MatchHandler matchHandler() {
        return new MatchHandler(apiServiceConfig, serviceBuilder, errorHandler);
    }

    @Bean
    TeamHandler teamHandler() {
        return new TeamHandler(apiServiceConfig, serviceBuilder, errorHandler);
    }

    @Bean
    RankingHandler rankingHandler() {
        return new RankingHandler(apiServiceConfig, serviceBuilder, errorHandler);
    }

    @Bean
    CommunityHandler communityHandler() {
        return new CommunityHandler(apiServiceConfig, serviceBuilder, errorHandler);
    }

    @Bean
    QuizHandler quizHandler() {
        return new QuizHandler(apiServiceConfig, serviceBuilder, errorHandler);
    }

    @Bean
    NoticeHandler noticeHandler() {
        return new NoticeHandler(apiServiceConfig, serviceBuilder, errorHandler);
    }

    @Bean
    ChatHandler chatHandler() {
        return new ChatHandler(apiServiceConfig, serviceBuilder, errorHandler);
    }

    public AuthHandler getAuthHandler() {
        return authHandler();
    }

    public FeedsHandler getFeedsHandler() {
        return feedsHandler();
    }

    public NewsHandler getNewsHandler() {
        return newsHandler();
    }

    public MatchHandler getMatchHandler() {
        return matchHandler();
    }

    public TeamHandler getTeamHandler() {
        return teamHandler();
    }

    public RankingHandler getRankingHandler() {
        return rankingHandler();
    }

    public CommunityHandler getCommunityHandler() {
        return communityHandler();
    }

    public QuizHandler getQuizHandler() {
        return quizHandler();
    }

    public NoticeHandler getNoticeHandler() {
        return noticeHandler();
    }

    public ChatHandler getChatHandler() {
        return chatHandler();
    }

}
