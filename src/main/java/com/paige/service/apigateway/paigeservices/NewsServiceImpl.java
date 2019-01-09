package com.paige.service.apigateway.paigeservices;

import com.paige.service.apigateway.application.ApiServiceConfig;
import com.paige.service.apigateway.model.ResultEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
public class NewsServiceImpl extends BaseService {


    public NewsServiceImpl(final ApiServiceConfig apiServiceConfig)
    {

        super(apiServiceConfig);
        webClient = WebClient.create(apiServiceConfig.getNews().getBaseurl());
    }


    @Override
    public Mono<ResultEntity> fromContents(Mono<ServerRequest> requestMono) {

        return requestMono
                .transform(this::getContent);
    }

    private Mono<ResultEntity> getContent(Mono<ServerRequest> requestMono) {

        String key = "test";
        return requestMono.flatMap(url -> webClient
                .get()
                .uri(url.path().replace("/api", ""))
                .accept(MediaType.APPLICATION_JSON)
                .header("test", url.exchange().getResponse().getHeaders().get(key))
                .exchange()
                .flatMap(clientResponse -> clientResponse.bodyToMono(ResultEntity.class)));
    }
}
