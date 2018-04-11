package com.laptech.chat.app.processor;

import com.laptech.chat.app.model.Chatmessage;
import com.laptech.chat.app.model.Chatmessage.ChatMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * Handle {@link Chatmessage}
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
