package com.laptech.chat.app.client.message;

import com.laptech.chat.app.client.socket.WebSocketImpl;
import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ActionType(value = "/w", description = "Whisper message to the given client. Syntax /w <clientid> <message>")
public class WhisperMessageAction implements MessageAction {

	@Autowired
	private WebSocketImpl clientEndpoint;

	@Override
	public void action(String message) {
		String[] words = message.split(" ");
		if (words.length < 3) {
			log.error("Incorrect format of message. Should be /w <clientid> <message>");
		}
		clientEndpoint.sendPrivateText(Arrays.stream(words).skip(2).collect(Collectors.joining(" ")), words[1]);
	}
}
