package com.paige.service.apigateway.Filter;

import com.paige.service.apigateway.model.UserSessionRedis;
import com.paige.service.apigateway.paigeservices.AuthServiceImpl;
import com.paige.service.apigateway.repository.SessionRedisHashRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;

@Component
public class RequestFilter implements WebFilter {



    @Autowired
    private AuthServiceImpl authService;

    public static Logger logger = LogManager.getLogger("Application");

    final TransactionId.Unique uid = new TransactionId.Unique();

//    @PostConstruct
//    public void postConstruct(SessionRedisHashRepository sessionRedisHashRepository) {
//        this.sessionRedisHashRepository = sessionRedisHashRepository;
//    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        Map<String, String> requestHeaders =
                exchange.getRequest().getHeaders().toSingleValueMap();

        for (String key : requestHeaders.keySet()) {
            logger.info("[Request Info] :{} = {}", key, requestHeaders.get(key));
        }

        MultiValueMap<String, String> args = new LinkedMultiValueMap<>();
        args.add("USER-ID", "1111");
        args.add("USER-LEVEL","USER");
        args.add("CLIENT-OS",
                requestHeaders.containsKey("CLIENT-OS") == true ? requestHeaders.get("CLIENT-OS") : "");
        args.add("CLIENT-VER",
                requestHeaders.containsKey("CLIENT-VER") == true ? requestHeaders.get("CLIENT-VER") : "");
        args.add("REQUEST-ID", Long.toString(uid.value()));
        args.add("ACCESS-TOKEN", "secret-key");

        exchange.getResponse().getHeaders().addAll(args);

        authService.getUserSession();

        return chain.filter(exchange);
    }
}
