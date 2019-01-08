package com.paige.service.apigateway.application;

import com.paige.service.apigateway.apiconfig.ApiServiceInfo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "apiservice")
@PropertySource(value = {"classpath:/apiservices.yml"}, factory = ApplicationPropertyFactory.class)
@Data
public class ApiServiceConfig {

    private ApiServiceInfo home;
    private ApiServiceInfo news;
    private ApiServiceInfo match;
    private ApiServiceInfo rank;

}

