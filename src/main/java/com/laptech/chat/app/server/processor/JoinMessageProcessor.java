package com.laptech.chat.app.server.processor;

import com.laptech.chat.app.server.model.Chatmessage.ChatMessage;
import com.laptech.chat.app.server.ServerStorage;
import com.laptech.chat.app.server.model.Chatmessage.ChatMessage.MessageType;
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
    serverStorage.sendMessage(
        ChatMessage.newBuilder().setContent(senderId).setType(MessageType.JOIN).setSender(senderId)
            .build());
    log.info("User {} joined chat", senderId);

  }
}
