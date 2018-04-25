package com.laptech.chat.app.client.socket;

import com.laptech.chat.app.client.UserInfo;
import com.laptech.chat.app.server.model.ChatMessage;
import com.laptech.chat.app.server.model.ChatMessage.MessageType;
import com.laptech.chat.app.server.model.User;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.websocket.CloseReason;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

@Slf4j
@Component
public class WebSocketImpl implements WebSocket {

  @Autowired
  private UserInfo userInfo;
  private StompSession stompSession;
  @Autowired
  private ChatMessageHandler messageHandler;


  @Override
  @SneakyThrows
  public void connect(String url) {
    ArrayList<Transport> transports = new ArrayList<>();
    transports.add(new WebSocketTransport(new StandardWebSocketClient()));
    SockJsClient sockJsClient = new SockJsClient(transports);
    WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
    stompClient.setMessageConverter(new MappingJackson2MessageConverter());
    WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
    if(StringUtils.isEmpty(userInfo.getToken())){
      log.error("You must login first");
    }else {
      headers.set("Authorization", userInfo.getToken());
      stompSession = stompClient
          .connect(url, new WebSocketHttpHeaders(headers), new CustomStompSessionHandler()).get();
      stompSession.subscribe("/topic/chat/message/public", messageHandler);
      stompSession.subscribe("/topic/chat/message/private/"+userInfo.getName(), messageHandler);

    }
  }







/*
	public void close(Session userSession, CloseReason reason) {
		log.info("Closing websocket");
		this.userSession = null;
	}
*/


  public void sendText(String text) {
    ChatMessage chatMessage = new ChatMessage();
    chatMessage.setMessageType(MessageType.SEND);
    chatMessage.setContent(text);
    sendMessage(chatMessage);

  }


  public void sendPrivateText(String text, String receiverId) {
    ChatMessage chatMessage = new ChatMessage();
    chatMessage.setMessageType(MessageType.SEND);
    chatMessage.setContent(text);
    chatMessage.setReceiver(receiverId);
    stompSession.send("/app/chat/message/private", chatMessage);
  }

  public void sendGetUsersMessage() {
    stompSession.subscribe("/app/chat/users", new StompFrameHandler() {
      @Override
      public Type getPayloadType(StompHeaders stompHeaders) {
        return List.class;
      }

      @Override
      public void handleFrame(StompHeaders stompHeaders, Object o) {
        System.out.println(o);
      }
    });
  }


  public void sendMessage(ChatMessage message) {
    if (stompSession.isConnected()) {
      stompSession.send("/app/chat/message/public", message);
    }
  }

}
