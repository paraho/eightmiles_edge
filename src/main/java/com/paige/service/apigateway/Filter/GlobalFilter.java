package com.paige.service.apigateway.Filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@ComponentScan("com.paige.service.*")
@Slf4j
public class GlobalFilter implements WebFilter {

    public static Logger logger = LogManager.getLogger("Application");
    final TransactionId.Unique uid = new TransactionId.Unique();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        Map<String, String> requestHeaders =
                exchange.getRequest().getHeaders().toSingleValueMap();

        MultiValueMap<String, String> args = new LinkedMultiValueMap<>();
        args.add("CLIENT-OS",
                requestHeaders.containsKey("CLIENT-OS") == true ? requestHeaders.get("CLIENT-OS") : "");
        args.add("CLIENT-VER",
                requestHeaders.containsKey("CLIENT-VER") == true ? requestHeaders.get("CLIENT-VER") : "");
        args.add("REQUEST-ID", Long.toString(uid.value()));

        exchange.getResponse().getHeaders().addAll(args);

        for (String key : requestHeaders.keySet()) {
            logger.info("[Request Info] :{} = {}", key, requestHeaders.get(key));
        }
        return chain.filter(exchange);
    }
}
