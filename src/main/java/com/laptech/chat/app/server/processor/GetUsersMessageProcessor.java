package com.laptech.chat.app.server.processor;

import com.laptech.chat.app.server.ServerStorage;
import com.laptech.chat.app.server.model.Chatmessage.ChatMessage;
import com.laptech.chat.app.server.model.Chatmessage.ChatMessage.MessageType;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

/**
 * Handle message when the user requests information about chat's users
 */
@Component
@Slf4j
public class GetUsersMessageProcessor implements ChatMessageProcessor {


  @Autowired
  private ServerStorage serverStorage;

  @Override
  public void process(WebSocketSession session, ChatMessage message) {
    String senderId = message.getSender();
    serverStorage.sendMessage(
        ChatMessage.newBuilder().setReceiver(message.getSender()).setType(MessageType.GET_USERS)
            .setContent(serverStorage.getUsers().collect(Collectors.joining(",")))
            .build());
    log.info("User {} joined chat", senderId);
  }
}
