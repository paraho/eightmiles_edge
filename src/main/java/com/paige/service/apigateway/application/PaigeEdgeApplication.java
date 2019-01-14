package com.paige.service.apigateway.application;

import com.paige.service.apigateway.apiconfig.ApiServiceConfig;
import com.paige.service.apigateway.apiconfig.ApiServiceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.logging.Logger;

@SpringBootApplication
@ComponentScan("com.paige.service.*")
public class PaigeEdgeApplication implements CommandLineRunner {

    Logger logger = Logger.getLogger("Application");

    @Autowired
    ApiServiceConfig apiServiceConfig;


    public static void main(String[] args){

        //TODO : 설정정보로 빼기
        SpringApplication.run(PaigeEdgeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        ApiServiceInfo getinfo = apiServiceConfig.getHome();
        ApiServiceInfo newsinfo = apiServiceConfig.getNews();
        logger.info("service url" + getinfo.getBaseurl());
        logger.info("service url" + newsinfo.getBaseurl());
    }

}

