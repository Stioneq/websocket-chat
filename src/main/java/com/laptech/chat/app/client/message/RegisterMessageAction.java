package com.laptech.chat.app.client.message;

import com.laptech.chat.app.client.UserInfo;
import com.laptech.chat.app.server.model.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
@ActionType(value = "/register", description = "Register on server. Syntax /register <username> <password> <email>")
public class RegisterMessageAction implements MessageAction {

  @Autowired
  private UserInfo userInfo;

  @Autowired
  private RestTemplate restTemplate;

  @Override
  public void action(String message) {
    String[] words = message.split(" ");
    if (words.length < 4) {
      log.error("Incorrect format of message. Should be /register <username> <password> <email>");
    }
    UserEntity userEntity = new UserEntity();
    userEntity.setUsername(words[1]);
    userEntity.setPassword(words[2]);
    userEntity.setEmail(words[3]);
    try {
      ResponseEntity<Void> result = restTemplate
          .exchange("http://localhost:8080/user/sign-up", HttpMethod.POST,
              new HttpEntity<>(userEntity), Void.class);
      if (result.getStatusCodeValue() == 200) {
        log.info("Register successfully");
      }
    } catch (RuntimeException e) {
      log.error("Cannot register. Cause " + e.getMessage());
    }

  }
}
