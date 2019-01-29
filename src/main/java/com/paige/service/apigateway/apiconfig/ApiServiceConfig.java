package com.paige.service.apigateway.apiconfig;

import com.paige.service.apigateway.application.ApplicationPropertyFactory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ComponentScan(basePackages = "com.paige.service.*")
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
     ApiServiceInfo community;

     public ApiServiceInfo createServiceInfo(String serviceName) {

          switch (serviceName) {
               case "home" : return this.getHome();
               case "news" : return this.getNews();
               case "match" : return this.getMatch();
               case "rank" : return this.getRank();
               case "community" : return this.getCommunity();
          }
          return null;
          /*
          try {
               Class<?> cls = Class.forName(serviceName);
               Object obj = cls.newInstance();
               return (ApiServiceInfo)obj;
          } catch (Exception e) {
               return null;
          }
          */
     }
}

