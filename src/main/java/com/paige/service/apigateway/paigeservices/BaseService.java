package com.paige.service.apigateway.paigeservices;

import com.paige.service.apigateway.apiconfig.ApiServiceConfig;
import com.paige.service.apigateway.exceptions.GetRemoteServiceException;
import com.paige.service.apigateway.model.ResultEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.function.BodyInserters;
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

    protected abstract Mono<ResultEntity> requestApi(Mono<ServerRequest> requestMono);

    protected Mono<ResultEntity> request(Mono<ServerRequest> requestMono) {

        return requestMono
                .flatMap(serverRequest -> {

                    ServerHttpRequest request   = serverRequest.exchange().getRequest();
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
                                //httpHeaders.addAll(request.getHeaders());

                                httpHeaders.add("REQUEST-ID",       resHeaders.get("REQUEST-ID")    );

                                if(reqHeaders.containsKey("ACCESS-TOKEN"))
                                    httpHeaders.add("ACCESS-TOKEN", reqHeaders.get("ACCESS-TOKEN")  );
                                if(reqHeaders.containsKey("CLIENT-OS"))
                                    httpHeaders.add("CLIENT-OS",    reqHeaders.get("CLIENT-OS")     );
                                if(reqHeaders.containsKey("CLIENT-VER"))
                                    httpHeaders.add("CLIENT-VER",   reqHeaders.get("CLIENT-VER")    );
                                if(reqHeaders.containsKey("DEVICE-UUID"))
                                    httpHeaders.add("DEVICE-UUID",  reqHeaders.get("DEVICE-UUID")   );

                                if(resHeaders.containsKey("USER-ID"))
                                    httpHeaders.add("USER-ID",      resHeaders.get("USER-ID")       );
                                if(resHeaders.containsKey("USER-LEVEL"))
                                    httpHeaders.add("USER-LEVEL",   resHeaders.get("USER-LEVEL")    );
                                if(resHeaders.containsKey("USER-TEAM"))
                                    httpHeaders.add("USER-TEAM",    resHeaders.get("USER-TEAM")     );

                                logger.info("[REQ API] [Headers] : {}", httpHeaders.toSingleValueMap().toString());
                            });

                    WebClient.RequestHeadersSpec<?> headersSpec;
                    if (requiresBody(method)) {
                        headersSpec = bodySpec.body(BodyInserters.fromDataBuffers(request.getBody()));
                    } else {
                        headersSpec = bodySpec;
                    }

                    Mono<ResultEntity> resultEntity =
                            headersSpec.exchange()
                                    .flatMap(clientResponse -> clientResponse.bodyToMono(ResultEntity.class));
                    return resultEntity;
                })
                .doOnNext(logging -> logger.debug("[RES API] [RESULT] = {}:", this.webClient.toString()))
                .onErrorResume(throwable -> Mono.error(new GetRemoteServiceException(throwable.getMessage(), throwable)));
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
