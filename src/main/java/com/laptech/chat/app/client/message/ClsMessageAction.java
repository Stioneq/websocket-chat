package com.laptech.chat.app.client.message;

import com.laptech.chat.app.client.socket.WebSocket;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ActionType(value = "/cls", description = "Clear screen")
public class ClsMessageAction implements MessageAction {

  @Autowired
  private WebSocket clientEndpoint;

  @Override
  public void action(String message) {
    System.out.println(IntStream.range(0, 10).mapToObj(i -> "\n").collect(Collectors.joining()));
  }
}
