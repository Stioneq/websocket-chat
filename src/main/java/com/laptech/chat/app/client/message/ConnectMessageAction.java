package com.laptech.chat.app.client.message;

import com.laptech.chat.app.client.UserInfo;
import com.laptech.chat.app.client.socket.WebSocket;
import java.util.Base64;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
@ActionType(value = "/connect", description = "Connect to chat")
public class ConnectMessageAction implements MessageAction {

  @Value("${websockets.server.url:ws://localhost:8080/ws}")
  private String url;
  @Autowired
  private WebSocket webSocket;


  @Override
  public void action(String message) {
    webSocket.connect(url);
  }
}
