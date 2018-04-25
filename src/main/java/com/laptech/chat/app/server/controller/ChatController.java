package com.laptech.chat.app.server.controller;

import com.laptech.chat.app.server.ServerStorage;
import com.laptech.chat.app.server.model.ChatMessage;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class ChatController {

	@Autowired
	private ServerStorage serverStorage;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@SubscribeMapping("/chat/users")
	public List<String> users(){
		return serverStorage.getUsers().collect(Collectors.toList());
	}

	@MessageMapping("/chat/message/public")
	public ChatMessage onMessage(ChatMessage message, Principal principal) {
		log.info(message.toString());
		message.setSender(principal.getName());
		return message;
	}

	@MessageMapping("/chat/message/private")
	public void onPrivateMessage(ChatMessage message, Principal principal){
		message.setSender(principal.getName());
		messagingTemplate.convertAndSend("/topic/chat/message/private/"+message.getReceiver(), message);
	}

}
