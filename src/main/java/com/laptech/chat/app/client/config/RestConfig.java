package com.laptech.chat.app.client.config;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@Configuration
@Slf4j
public class RestConfig {
  @Bean
  public RestTemplate restTemplate(){
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.setErrorHandler(new DefaultResponseErrorHandler(){
      @Override
      public void handleError(ClientHttpResponse response) throws IOException {
        if(response.getStatusCode() == HttpStatus.UNAUTHORIZED){
          log.error("Incorrect user details");
        }

      }
    });
    return restTemplate;
  }

}
