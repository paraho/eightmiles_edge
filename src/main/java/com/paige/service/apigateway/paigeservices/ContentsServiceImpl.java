package com.paige.service.apigateway.paigeservices;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ContentsServiceImpl implements ContentsService {

    private final String endPoint;
    private final WebClient webClient;

    private final String remoteUrl1 = "http://localhost:9000/getcontents";

    public ContentsServiceImpl(final String endPoint)
    {
        this.endPoint = endPoint;
        this.webClient = WebClient.create();
    }

    @Override
    public Mono<String> fromContents(Mono<String> contentMono) {

        return contentMono
                .transform(this::buildUrl)
                .transform(this::getContent);
    }

    private Mono<String> getContent(Mono<String> urlMono) {

        return urlMono.flatMap(url -> webClient
                                .get()
                                .uri(remoteUrl1)
                                .accept(MediaType.APPLICATION_JSON)
                                .exchange()
                                .flatMap(clientResponse -> clientResponse.bodyToMono(String.class)));

    }

    private Mono<String> buildUrl(Mono<String> stringMono) {

        return stringMono.flatMap(address -> Mono.just("hello"));
    }

}
