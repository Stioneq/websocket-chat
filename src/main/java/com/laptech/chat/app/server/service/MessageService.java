package com.laptech.chat.app.server.service;

import com.laptech.chat.app.server.model.ChatMessage;
import java.util.List;

public interface MessageService {

  List<ChatMessage> getMessages();
  void saveMessage(ChatMessage message);
}
