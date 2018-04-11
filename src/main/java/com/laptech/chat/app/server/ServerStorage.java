package com.laptech.chat.app.server;


import com.laptech.chat.app.server.model.Chatmessage.ChatMessage;
import com.laptech.chat.app.server.model.Chatmessage.ChatMessage.MessageType;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * Contains information about users , state etc
 */
@Component
@Slf4j
public class ServerStorage {

  private Map<String, WebSocketSession> users = new HashMap<>();

  public void add(String userId, WebSocketSession socketSession) {
    Objects.requireNonNull(userId);
    if (users.containsKey(userId)) {
      throw new RuntimeException("User already existed");
    }
    users.put(userId, socketSession);
  }

  public void sendMessage(ChatMessage chatMessage) {

    users
        .entrySet()
        .stream()
        .filter(
            e -> isValidReceiver(chatMessage, e))
        .forEach(e -> {
          try {
            e.getValue().sendMessage(new BinaryMessage(
                ChatMessage.newBuilder()
                    .setReceiver(e.getKey())
                    .setType(MessageType.SEND)
                    .setContent(chatMessage.getContent())
                    .setSender(chatMessage.getSender())
                    .build()
                    .toByteArray()));
          } catch (IOException e1) {
            log.error("Cannot send msg to {}", e.getKey());
          }
        });
  }

  private boolean isValidReceiver(ChatMessage chatMessage, Entry<String, WebSocketSession> e) {
    return !chatMessage.getSender().equals(e.getKey()) && (chatMessage.getReceiver().isEmpty()
        || chatMessage.getReceiver().equals(e.getKey()));
  }
}
