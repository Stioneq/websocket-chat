package com.laptech.chat.app.client.message;

import com.laptech.chat.app.client.UserInfo;
import com.laptech.chat.app.client.socket.WebSocketImpl;
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
      log.error("Incorrect format of message. Should be /loing <username> <password>");
    }
    HttpHeaders httpHeaders = new HttpHeaders();
    String token =
        "Basic " + new String(Base64.getEncoder().encode((words[1] + ":" + words[2]).getBytes()));
    httpHeaders.set("Authorization",
        token);
    ResponseEntity<Void> result = restTemplate
        .exchange("http://localhost:8080/api/userInfo", HttpMethod.GET,
            new HttpEntity<>(httpHeaders), Void.class);
    if(result.getStatusCodeValue() == 200){
      userInfo.setToken(token);
      userInfo.setName(words[1]);
      log.info("Login successfully");
    }
  }
}
