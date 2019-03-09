package com.eightmileslab.service.apigateway.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.eightmileslab.service.*")
@Slf4j
public class EdgeApplication {

    public static void main(String[] args){

        SpringApplication.run(EdgeApplication.class, args);
    }

}

