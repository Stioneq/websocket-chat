package com.laptech.chat.app.server.security;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@ConfigurationProperties(prefix = "jwt")
@PropertySource("classpath:application.yml")
@Data
@Configuration
public class JwtSettings {

  @Value("${secret}")
  private String secret;

  @Value("${exp-time}")
  private String expTime;

  @Value("${token-prefix}")
  private String tokenPrefix;

  @Value("${auth-header}")
  private String authorizationHeader;

  @Value("${get-param-key}")
  private String getParamKey;


}
