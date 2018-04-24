package com.laptech.chat.app.client.socket;

import com.laptech.chat.app.server.model.ChatMessage;
import com.laptech.chat.app.server.model.ChatMessage.MessageType;
import java.lang.reflect.Type;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHttpHeaders;

/**
 *
 */
@Slf4j
@Component
public class CustomStompSessionHandler extends StompSessionHandlerAdapter {

	@Autowired
	private WebSocketImpl webSocket;

	@Override
	public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
		log.info("Status connected");
		ChatMessage chatMessage = new ChatMessage();
		chatMessage.setMessageType(MessageType.JOIN);
		webSocket.sendMessage(chatMessage);
	}

}
