package com.laptech.chat.app.client;

import com.google.protobuf.InvalidProtocolBufferException;
import com.laptech.chat.app.server.model.Chatmessage.ChatMessage;
import com.laptech.chat.app.server.model.Chatmessage.ChatMessage.MessageType;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@ClientEndpoint
@Slf4j
@Component
public class WebsocketClientEndpoint {

  private UserInfo userInfo = new UserInfo();
  private Session userSession = null;
  private MessageHandler messageHandler;
  private ByteBuffer buffer = ByteBuffer.allocate(512);


  @OnOpen
  public void open(Session session) {
    userSession = session;
    log.info("Connection opened");
    sendMessage(
        ChatMessage.newBuilder().setSender(userInfo.getId()).setType(MessageType.JOIN).build());
  }

  @OnMessage
  public void handleMessage(byte[] b, boolean last) {
    this.buffer = ByteBuffer.allocate(512);
    this.buffer.put(b);
    if (last) {
      try {
        this.buffer.flip();
        messageHandler.handle(ChatMessage.parseFrom(b));
        this.buffer.clear();
      } catch (InvalidProtocolBufferException e) {
        e.printStackTrace();
      }
    }
  }

  @OnClose
  public void close(Session userSession, CloseReason reason) {
    log.info("Closing websocket");
    this.userSession = null;
  }


  public void addMessageHandler(MessageHandler handler) {
    this.messageHandler = handler;
  }


  public void sendText(String text) {
    sendMessage(ChatMessage.newBuilder().setSender(userInfo.getId()).setType(MessageType.SEND)
        .setContent(text).build());
  }

  public void connect(URI uri) {
    WebSocketContainer container = ContainerProvider.getWebSocketContainer();
    try {
      container.connectToServer(this, uri);
    } catch (DeploymentException | IOException e) {
      log.error("Cannot connected to websocket endpoint");
    }
  }

  public void sendPrivateText(String text, String receiverId) {
    sendMessage(ChatMessage.newBuilder().setSender(userInfo.getId()).setReceiver(receiverId)
        .setType(MessageType.SEND)
        .setContent(text).build());
  }

  public void sendGetUsersMessage() {
    sendMessage(ChatMessage.newBuilder().setSender(userInfo.getId())
        .setType(MessageType.GET_USERS).build());
  }

  public interface MessageHandler {

    void handle(ChatMessage msg);
  }

  private void sendMessage(ChatMessage message) {
    userSession.getAsyncRemote().sendBinary(ByteBuffer.wrap(message.toByteArray()));
  }

}
