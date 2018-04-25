package com.laptech.chat.app.server.listener;

import com.laptech.chat.app.server.ServerStorage;
import com.laptech.chat.app.server.model.ChatMessage;
import com.laptech.chat.app.server.model.ChatMessage.MessageType;
import java.security.Principal;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 *
 */
@Component
@Slf4j
public class WebSocketEventListener {
	@Autowired
	private SimpMessageSendingOperations sendingOperations;
	@Autowired
	private ServerStorage serverStorage;

	@EventListener
	public void handleConnect(SessionConnectedEvent event){
		SimpMessageHeaderAccessor msg = SimpMessageHeaderAccessor.wrap(event.getMessage());
		String name = Optional.of(event.getUser()).map(Principal::getName).get();
		serverStorage.add(msg.getSessionId(), name);
    sendJoinMessage(name);
		log.info("New user {}:{} connected", msg.getSessionId(), name);
	}

  private void sendJoinMessage(String name) {
    ChatMessage msg = new ChatMessage();
    msg.setContent(name);
    msg.setMessageType(MessageType.JOIN);
    sendingOperations.convertAndSend("/topic/chat/message/public", msg);
  }

  @EventListener
	public void handleDisconnect(SessionDisconnectEvent event){
    SimpMessageHeaderAccessor msg = SimpMessageHeaderAccessor.wrap(event.getMessage());
    String name = Optional.of(event.getUser()).map(Principal::getName).get();
    sendLogoutMessage(name);
		log.info("disconnected"+event.getSessionId());
		serverStorage.remove(event.getSessionId());

	}

  private void sendLogoutMessage(String name) {
    ChatMessage msg = new ChatMessage();
    msg.setContent(name);
    msg.setMessageType(MessageType.LOGOUT);
    sendingOperations.convertAndSend("/topic/chat/message/public", msg);
  }
}
