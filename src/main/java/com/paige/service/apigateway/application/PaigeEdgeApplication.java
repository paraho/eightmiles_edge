package com.paige.service.apigateway.application;

import com.paige.service.apigateway.apiconfig.ApiServiceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class PaigeEdgeApplication implements CommandLineRunner {

    @Autowired
    ApiServiceConfig apiServiceConfig;

    public static void main(String[] args){

        SpringApplication.run(PaigeEdgeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

/*        ApiServiceInfo getinfo = apiServiceConfig.getHome();
        ApiServiceInfo newsinfo = apiServiceConfig.getNews();
        log.info("service url" + getinfo.getBaseurl());
        log.info("service url" + newsinfo.getBaseurl());*/
    }

}

