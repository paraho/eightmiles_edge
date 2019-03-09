package com.eightmileslab.service.apigateway.apiconfig;

import com.eightmileslab.service.apigateway.application.ApplicationPropertyFactory;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ComponentScan(basePackages = "com.eightmileslab.service.*")
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "apiservice")
@PropertySource(value = {"classpath:/apiservices.yml"}, factory = ApplicationPropertyFactory.class)
@Data
public class ApiServiceConfig {

     ApiServiceProperty auth;
     ApiServiceProperty buy;
     ApiServiceProperty sell;
     ApiServiceProperty wallet;
     ApiServiceProperty inq;
     ApiServiceProperty kmi;

     public ApiServiceProperty getServiceInfo(String serviceName) {

          switch (serviceName) {
               case "auth"    : return this.getAuth();
               case "buy"     : return this.getBuy();
               case "sell"    : return this.getSell();
               case "wallet"  : return this.getWallet();
               case "inq"     : return this.getInq();
          }
          return null;
     }
}

