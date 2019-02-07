package com.paige.service.apigateway.application;

import com.paige.service.apigateway.apiconfig.ApiServiceConfig;
import com.paige.service.apigateway.apiconfig.ApiServiceProperty;
import com.paige.service.apigateway.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@Slf4j
public class ApplicationInitializer implements CommandLineRunner {

    @Autowired
    ApiServiceConfig apiServiceConfig;

    private final PostRepository posts;
    Logger logger = Logger.getLogger("Application");

    public ApplicationInitializer(PostRepository posts) {
        this.posts = posts;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("start data initialization  ...");

        ApiServiceProperty getinfo = apiServiceConfig.getFeeds();
        ApiServiceProperty newsinfo = apiServiceConfig.getNews();
        logger.info("service url" + getinfo.getBaseurl());
        logger.info("service url" + newsinfo.getBaseurl());

        /* Redis 접속 테스트
            this.posts
                .deleteAll()
                .thenMany(
                        Flux
                                .just("Post one", "Post two")
                                .flatMap(
                                        title -> {
                                            String id = UUID.randomUUID().toString();
                                            return this.posts.save(Post.builder().id(id).title(title).content("content of " + title).build());
                                        }
                                )
                )
                .log()
                .subscribe(
                        null,
                        null,
                        () -> log.info("done initialization...")
                );
        */

    }
}
