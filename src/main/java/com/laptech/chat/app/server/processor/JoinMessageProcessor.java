package com.laptech.chat.app.server.processor;

import com.laptech.chat.app.server.ServerStorage;
import com.laptech.chat.app.server.model.ChatMessage;
import com.laptech.chat.app.server.model.ChatMessage.MessageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
@Slf4j
public class JoinMessageProcessor implements ChatMessageProcessor {


  @Autowired
  private ServerStorage serverStorage;

  @Override
  public void process(WebSocketSession session, ChatMessage message) {
    String senderId = message.getSender();
    serverStorage.add(senderId, session);
    ChatMessage chatMessage = new ChatMessage();
    chatMessage.setContent(senderId);
    chatMessage.setMessageType(MessageType.JOIN);
    chatMessage.setSender(senderId);
    serverStorage.sendMessage(chatMessage);
    log.info("User {} joined chat", senderId);

  }
}
