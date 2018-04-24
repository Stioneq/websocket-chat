package com.laptech.chat.app.server.processor;

import com.laptech.chat.app.server.model.ChatMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * Handle {@link ChatMessage}
 * ChatMessageProcessor per type
 */
public interface ChatMessageProcessor {

  /**
   * Handle message from client with session
   * @param session
   * @param message
   */
  void process(WebSocketSession session, ChatMessage message);
}
