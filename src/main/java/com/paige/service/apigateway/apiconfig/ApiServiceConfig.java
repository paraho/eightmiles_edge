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

     ApiServiceInfo auth;
     ApiServiceInfo feeds;
     ApiServiceInfo news;
     ApiServiceInfo match;
     ApiServiceInfo team;
     ApiServiceInfo ranking;
     ApiServiceInfo community;
     ApiServiceInfo quiz;
     ApiServiceInfo notice;
     ApiServiceInfo chat;

     public ApiServiceInfo getServiceInfo(String serviceName) {

          switch (serviceName) {
               case "auth"         : return this.getAuth();
               case "feeds"        : return this.getFeeds();
               case "news"         : return this.getNews();
               case "match"        : return this.getMatch();
               case "team"         : return this.getTeam();
               case "ranking"      : return this.getRanking();
               case "community"    : return this.getCommunity();
               case "quiz"         : return this.getChat();
               case "notice"       : return this.getNotice();
               case "chat"         : return this.getChat();
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

