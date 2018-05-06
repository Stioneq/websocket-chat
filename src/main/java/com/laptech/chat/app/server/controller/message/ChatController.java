package com.laptech.chat.app.server.controller.message;

import com.laptech.chat.app.server.ServerStorage;
import com.laptech.chat.app.server.model.ChatMessage;
import com.laptech.chat.app.server.repository.MessageRepository;
import com.laptech.chat.app.server.service.MessageService;
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
	private MessageService messageService;
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
		messageService.saveMessage(message);
		return message;
	}
//24.09 19.25   5.10.18 10.45
	@MessageMapping("/chat/message/private")
	public void onPrivateMessage(ChatMessage message, Principal principal){
		message.setSender(principal.getName());
		messageService.saveMessage(message);
		messagingTemplate.convertAndSend("/topic/chat/message/private/"+message.getReceiver(), message);
	}

}
