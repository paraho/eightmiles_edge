package com.paige.service.apigateway.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.paige.service.*")
@Slf4j
public class PaigeEdgeApplication {

    public static void main(String[] args){

        SpringApplication.run(PaigeEdgeApplication.class, args);
    }

}

