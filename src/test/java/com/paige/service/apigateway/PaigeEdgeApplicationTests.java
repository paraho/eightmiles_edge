package com.paige.service.apigateway;

import com.paige.service.apigateway.application.ApiServiceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApiServiceConfig.class})
public class PaigeEdgeApplicationTests {

    @Autowired
    ApiServiceConfig apiServiceConfig;

    @Test
    public void contextLoads() {

        System.out.println("service info : " + apiServiceConfig.getNews().getBaseurl());

    }

}

