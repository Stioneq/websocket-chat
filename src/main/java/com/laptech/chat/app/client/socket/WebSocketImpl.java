package com.laptech.chat.app.client.socket;

import com.laptech.chat.app.client.UserInfo;
import com.laptech.chat.app.server.model.ChatMessage;
import com.laptech.chat.app.server.model.ChatMessage.MessageType;
import java.nio.ByteBuffer;
import javax.websocket.CloseReason;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@Slf4j
@Component
public class WebSocketImpl implements WebSocket{

	private UserInfo userInfo = new UserInfo();
	private StompSession stompSession;
	@Autowired
	private ChatMessageHandler messageHandler;


	@Override
	@SneakyThrows
	public void connect(String url) {
		WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());
		stompSession = stompClient.connect(url, new CustomStompSessionHandler()).get();
		stompSession.subscribe("/topic/chat/message", messageHandler);
		stompSession.subscribe("/app/chat/users", messageHandler);
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
		sendMessage(chatMessage);
	}

	public void sendGetUsersMessage() {
		ChatMessage chatMessage = new ChatMessage();
		chatMessage.setMessageType(MessageType.GET_USERS);
		sendMessage(chatMessage);
	}



	public void sendMessage(ChatMessage message) {
		if(stompSession.isConnected()){
			message.setSender(userInfo.getId());
			stompSession.send("/app/chat/message", message);
		}
	}

}
