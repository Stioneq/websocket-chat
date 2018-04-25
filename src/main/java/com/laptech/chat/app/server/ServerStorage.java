package com.laptech.chat.app.server;


import com.laptech.chat.app.server.model.ChatMessage;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import javax.validation.constraints.NotNull;
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

  private Map<String, String> users = new HashMap<>();

  public void add(String sessionId, String name) {
    Objects.requireNonNull(sessionId);
    if (users.containsKey(sessionId)) {
      throw new RuntimeException("User already existed");
    }
    users.put(sessionId, name);
  }


  private boolean isValidReceiver(ChatMessage chatMessage, Entry<String, WebSocketSession> e) {
    return (chatMessage.getReceiver().isEmpty()
        || chatMessage.getReceiver().equals(e.getKey()));
  }
  /**
   * Remove entry by sessionid
   * @param sessionId
   * @return
   */
  public String remove(@NotNull String sessionId) {

    Optional<Entry<String, String>> userEntry = users.entrySet().stream()
        .filter(a -> sessionId.equals(a.getKey())).findFirst();
    users.remove(userEntry
        .orElseThrow(() -> new RuntimeException("Session wasn't stored")).getKey());
    return userEntry.get().getKey();
  }

  /**
   * @return stream of users in the server
   */
  public Stream<String> getUsers() {
    return users.values().stream().distinct();
  }
}
