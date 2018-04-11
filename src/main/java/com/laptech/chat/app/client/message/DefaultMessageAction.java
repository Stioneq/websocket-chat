package com.laptech.chat.app.client.message;

import com.laptech.chat.app.client.WebsocketClientEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultMessageAction implements MessageAction{

  @Autowired
  private WebsocketClientEndpoint clientEndpoint;

  @Override
  public void action(String message) {
    clientEndpoint.sendText(message);
  }
}
