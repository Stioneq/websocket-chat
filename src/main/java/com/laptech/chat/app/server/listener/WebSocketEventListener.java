package com.laptech.chat.app.server.listener;

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

	@EventListener
	public void handleConnect(SessionConnectedEvent event){
		SimpMessageHeaderAccessor wrap = SimpMessageHeaderAccessor.wrap(event.getMessage());
		System.out.println(wrap.getSessionId());
		log.info("connected" + event.getMessage());
	}

	@EventListener
	public void handleDisconnect(SessionDisconnectEvent event){
		log.info("disconnected"+event.getSessionId());

	}
}
