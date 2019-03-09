package com.eightmileslab.service.apigateway.testservices;

import org.springframework.web.bind.annotation.GetMapping;

public class NewsService {

    @GetMapping("/hello2")
    public String getTestService1() {

        return "hello2";
    }

/*    public static void main(String[] args) {

        //TODO : 설정정보로 빼기
        System.setProperty("SERVER.PORT","9001");
        SpringApplication.run(ApiApplication.class, args);
    }*/
}
