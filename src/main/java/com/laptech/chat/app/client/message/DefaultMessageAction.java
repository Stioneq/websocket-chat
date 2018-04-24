package com.laptech.chat.app.client.message;

import com.laptech.chat.app.client.socket.WebSocket;
import com.laptech.chat.app.client.socket.WebSocketImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultMessageAction implements MessageAction {

	@Autowired
	private WebSocketImpl clientEndpoint;

	@Override
	public void action(String message) {
		clientEndpoint.sendText(message);
	}
}
