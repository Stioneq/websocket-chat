package com.laptech.chat.app.processor;

import com.laptech.chat.app.model.Chatmessage.ChatMessage;
import com.laptech.chat.app.server.ServerStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
@Slf4j
public class SendMessageProcessor implements ChatMessageProcessor {


  @Autowired
  private ServerStorage serverStorage;



  @Override
  public void process(WebSocketSession session, ChatMessage message) {
    String senderId = message.getSender();
    String content = message.getContent();
    serverStorage.sendMessage(message);
    log.info("User {} send message {}", senderId, content);
  }
}