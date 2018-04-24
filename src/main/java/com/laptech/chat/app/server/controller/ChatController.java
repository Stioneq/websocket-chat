package com.laptech.chat.app.server.controller;

import com.laptech.chat.app.server.ServerStorage;
import com.laptech.chat.app.server.model.ChatMessage;
import com.laptech.chat.app.server.model.ChatMessage.MessageType;
import com.laptech.chat.app.server.processor.ChatMessageProcessor;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class ChatController {

	@Autowired
	ServerStorage serverStorage;


	@SubscribeMapping("/chat/users")
	public String users(){
		ChatMessage chatMessage = new ChatMessage();
		chatMessage.setMessageType(MessageType.GET_USERS);
		//chatMessage.setContent(serverStorage.getUsers().collect(Collectors.joining(",")));
		chatMessage.setContent("DASDASDASDSADASDASDASDAS");
		return "dasdasdad";
	}

	@MessageMapping("/chat/message")
	@SendTo("/chat/message")
	public ChatMessage onMessage(ChatMessage message) {
		log.info(message.toString());
		switch (message.getMessageType()) {
			case JOIN:
				//joinMessageProcessor.process(session, message);
				break;
			case SEND:
				//sendMessageProcessor.process(session, message);
				break;
			case GET_USERS:
				//getUsersMessageProcessor.process(session, message);
				break;
			default:
		}

		return message;
	}


}
