package com.laptech.chat.app.client.message;

import com.laptech.chat.app.client.UserInfo;
import com.laptech.chat.app.client.socket.WebSocketImpl;
import com.laptech.chat.app.server.model.UserEntity;
import java.util.Arrays;
import java.util.Base64;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
@ActionType(value = "/login", description = "Login to chat. Syntax /login <username> <password>")
public class LoginMessageAction implements MessageAction {

  @Autowired
  private UserInfo userInfo;

  @Autowired
  private RestTemplate restTemplate;

  @Override
  public void action(String message) {
    String[] words = message.split(" ");
    if (words.length < 3) {
      log.error("Incorrect format of message. Should be /login <username> <password>");
    }
    UserEntity userEntity = new UserEntity();
    userEntity.setUsername(words[1]);
    userEntity.setPassword(words[2]);
    try {
      ResponseEntity<String> result = restTemplate
          .exchange("http://localhost:8080/user/login", HttpMethod.POST,
              new HttpEntity<>(userEntity), String.class);
      if (result.getStatusCodeValue() == 200) {
        userInfo.setToken(result.getBody());
        userInfo.setName(words[1]);
        log.info("Login successfully");
      }
    } catch (RuntimeException e) {
      log.error("Cannot login. Cause " + e.getMessage());
    }

  }
}
