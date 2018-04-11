package com.laptech.chat.app.client.message;


import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@ActionType("/help")
@Component
public class HelpMessageAction implements MessageAction{

  @Override
  public void action(String message) {
    System.out.println("<Help>");
  }
}
