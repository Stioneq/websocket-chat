package com.laptech.chat.app.client.message;

import com.laptech.chat.app.client.socket.WebSocketImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ActionType(value = "/users", description = "Whisper message to the given client. Syntax /users")
public class GetUsersMessageAction implements MessageAction {

	@Autowired
	private WebSocketImpl clientEndpoint;

	@Override
	public void action(String message) {
		clientEndpoint.sendGetUsersMessage();
	}
}
