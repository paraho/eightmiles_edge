package com.paige.service.apigateway.paigeservices;

import com.paige.service.apigateway.apiconfig.ApiServiceConfig;
import com.paige.service.apigateway.model.ResultEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

@Slf4j
public class HomeServiceImpl extends BaseService {

    public HomeServiceImpl(final ApiServiceConfig apiServiceConfig)
    {

        super(apiServiceConfig);
        webClient = WebClient.create(apiServiceConfig.getHome().getBaseurl());
    }

    @Override
    public Mono<ResultEntity> fromContents(Mono<ServerRequest> requestMono) {

        return requestMono
                .transform(this::getContent);
    }

    @Override
    protected Mono<ResultEntity> postContents(Mono<ServerRequest> requestMono) {
        return null;
    }

    @Override
    protected Mono<ResultEntity> putContents(Mono<ServerRequest> requestMono) {
        return null;
    }

    @Override
    protected Mono<ResultEntity> delContents(Mono<ServerRequest> requestMono) {
        return null;
    }

/*    private Mono<ResultEntity> getContent(Mono<ServerRequest> requestMono) {

        return requestMono.flatMap(url -> {

            Map<String, String> mapHeader = url.exchange().getResponse().getHeaders().toSingleValueMap();

            WebClient authClient = webClient.mutate()
                    .defaultHeaders(httpHeaders -> {
                        httpHeaders.add("USER-ID", mapHeader.get("USER-ID"));
                        httpHeaders.add("USER-LEVEL",mapHeader.get("USER-LEVEL"));
                        httpHeaders.add("CLIENT-OS",mapHeader.get("CLIENT-OS"));
                        httpHeaders.add("CLIENT-VER",mapHeader.get("CLIENT-VER"));
                        httpHeaders.add("REQUEST-ID",mapHeader.get("REQUEST-ID"));
                    })
                    .build();

            Mono<ResultEntity> resultEntity = authClient
                    .get()
                    .uri(url.path().replace("/api", ""))
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .flatMap(clientResponse -> clientResponse.bodyToMono(ResultEntity.class));

            return resultEntity;
        });
    }*/
}