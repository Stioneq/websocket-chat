package com.laptech.chat.app.server.config;

import com.google.protobuf.InvalidProtocolBufferException;
import com.laptech.chat.app.server.model.Chatmessage.ChatMessage;
import com.laptech.chat.app.server.processor.ChatMessageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@Slf4j
public class ChatSocketHandler extends TextWebSocketHandler {

  @Autowired
  @Qualifier("joinMessageProcessor")
  private ChatMessageProcessor messageProcessor;

  @Autowired
  @Qualifier("sendMessageProcessor")
  private ChatMessageProcessor sendMessageProcessor;

  @Override
  protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
    try {

      ChatMessage chatMessage = ChatMessage.parseFrom(message.getPayload().array());
      log.info(chatMessage.toString());
      switch (chatMessage.getType()) {
        case JOIN:
          messageProcessor.process(session, chatMessage);
          break;
        case SEND:
          sendMessageProcessor.process(session, chatMessage);
          break;
        default:
      }
    } catch (InvalidProtocolBufferException e) {
      e.printStackTrace();
    }
  }
}
