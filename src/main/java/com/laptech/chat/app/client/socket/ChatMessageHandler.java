package com.laptech.chat.app.client.socket;

import com.laptech.chat.app.server.model.ChatMessage;
import java.lang.reflect.Type;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class ChatMessageHandler implements StompFrameHandler{

	@Override
	public Type getPayloadType(StompHeaders headers) {
		return ChatMessage.class;
	}

	@Override
	public void handleFrame(StompHeaders headers, Object payload) {
		System.out.println(payload);
	}
}
