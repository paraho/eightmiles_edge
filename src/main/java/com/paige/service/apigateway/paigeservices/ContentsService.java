package com.paige.service.apigateway.paigeservices;

import reactor.core.publisher.Mono;

public interface ContentsService {
    Mono<String> fromContents(Mono<String> stringMono);
}
