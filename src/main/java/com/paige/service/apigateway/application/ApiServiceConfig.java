package com.paige.service.apigateway.application;

import com.paige.service.apigateway.apiconfig.ApiServiceInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "apiservice")
@PropertySource(value = {"classpath:/apiservices.yml"}, factory = ApplicationPropertyFactory.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiServiceConfig {

     ApiServiceInfo home;
     ApiServiceInfo news;
     ApiServiceInfo match;
     ApiServiceInfo rank;

}

