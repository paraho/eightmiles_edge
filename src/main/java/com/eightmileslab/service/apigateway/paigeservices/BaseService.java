package com.eightmileslab.service.apigateway.paigeservices;

import com.eightmileslab.service.apigateway.apiconfig.ApiServiceConfig;
import com.eightmileslab.service.apigateway.exceptions.GetRemoteServiceException;
import com.eightmileslab.service.apigateway.model.ResultEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
public abstract class BaseService {

    public static Logger logger = LogManager.getLogger("Application");
    private static final String ERROR_REMOTE_SERVICE = "error remote service";
    protected WebClient webClient;
    protected ApiServiceConfig apiServiceConfig;
    protected String baseUrl;

    public BaseService(ApiServiceConfig apiServiceConfig) {

        this.apiServiceConfig = apiServiceConfig;
    }

    public void setBaseUrl(String url) {

        this.baseUrl = url;
        this.webClient = WebClient.create(this.baseUrl);

    }

    protected abstract Mono<ClientResponse> requestApi(Mono<ServerRequest> requestMono);

    protected Mono<ClientResponse> request(Mono<ServerRequest> requestMono) {

        Mono<ClientResponse> clientResponse =  requestMono
            .flatMap(serverRequest -> {

                ServerHttpRequest request = serverRequest.exchange().getRequest();
                ServerHttpResponse response = serverRequest.exchange().getResponse();

                Map<String, String> resHeaders = response.getHeaders().toSingleValueMap();
                Map<String, String> reqHeaders = request.getHeaders().toSingleValueMap();
                HttpMethod method = request.getMethod();

                String uriInfo = serverRequest.path().replace("api/", "");

                if (request.getURI().getQuery() != null)
                    uriInfo = uriInfo + "?" + request.getURI().getQuery();

                WebClient.RequestBodySpec bodySpec = this.webClient.method(method)
                        .uri(uriInfo)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .headers(httpHeaders -> {
                            httpHeaders.addAll(request.getHeaders());
                            httpHeaders.add("REQUEST-ID", resHeaders.get("REQUEST-ID"));
                            if (resHeaders.containsKey("USER-ID"))
                                httpHeaders.add("USER-ID", resHeaders.get("USER-ID"));
                            if (resHeaders.containsKey("USER-LEVEL"))
                                httpHeaders.add("USER-LEVEL", resHeaders.get("USER-LEVEL"));
                            if (resHeaders.containsKey("USER-TEAM"))
                                httpHeaders.add("USER-TEAM", resHeaders.get("USER-TEAM"));

                            logger.info("[REQ API] [Headers] : {}", httpHeaders.toSingleValueMap().toString());
                        });

                WebClient.RequestHeadersSpec<?> headersSpec;
                if (requiresBody(method)) {
                    headersSpec = bodySpec.body(BodyInserters.fromDataBuffers(request.getBody()));
                } else {
                    headersSpec = bodySpec;
                }

                return headersSpec.exchange();
            })
            .onErrorResume(throwable -> Mono.error(new GetRemoteServiceException(throwable.getMessage(), throwable)));

        return clientResponse;

}


    protected Mono<ResultEntity> response(final Mono<ResultEntity> resultEntityMono) {

        return resultEntityMono.flatMap( resultEntity -> {
            if (resultEntity.getError_code() == null)
                return Mono.error(new GetRemoteServiceException(ERROR_REMOTE_SERVICE));
            else
                return Mono.just(resultEntity);
        });
    }

    protected boolean requiresBody(HttpMethod method) {
        switch (method) {
            case PUT:
            case POST:
            case PATCH:
                return true;
            default:
                return false;
        }
    }
}
