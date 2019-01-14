package com.paige.service.apigateway.paigeservices;

import com.paige.service.apigateway.model.UserSessionRedis;
import com.paige.service.apigateway.repository.SessionRedisHashRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@ComponentScan("com.paige.service.*")
public class AuthServiceImpl {

    @Autowired
    private SessionRedisHashRepository sessionRedisHashRepository;
    private final ReactiveRedisOperations<String, UserSessionRedis> coffeeOps;


    public AuthServiceImpl(ReactiveRedisOperations<String, UserSessionRedis> coffeeOps) {
        this.coffeeOps = coffeeOps;
    }

//    @Autowired
//    ReactiveRedisTemplate stringTemplate;

//    public AuthServiceImpl(final ApiServiceConfig apiServiceConfig)
//    {
//
//        super(apiServiceConfig);
//        webClient = WebClient.create(apiServiceConfig.getNews().getBaseurl());
//    }
//
//
//    @Override
//    public Mono<ResultEntity> fromContents(Mono<ServerRequest> requestMono) {
//
//        return requestMono
//                .transform(this::getContent);
//    }
//
//    @Override
//    protected Mono<ResultEntity> postContents(Mono<ServerRequest> requestMono) {
//        return null;
//    }
//
//    @Override
//    protected Mono<ResultEntity> putContents(Mono<ServerRequest> requestMono) {
//        return null;
//    }
//
//    @Override
//    protected Mono<ResultEntity> delContents(Mono<ServerRequest> requestMono) {
//        return null;
//    }

    public UserSessionRedis getUserSession() {

//        List<UserSessionRedis> list = (List<UserSessionRedis>) sessionRedisHashRepository.findAll();
//        for (UserSessionRedis userSessionRedis:
//             list) {
//            log.info(userSessionRedis.toString());
//        }
//
//        log.info("session test");
//        return sessionRedisHashRepository
//                .findById("session:A5153684-8FEF-4D7F-93E4-A5BA14776682").get();

        return null;

    }


}
