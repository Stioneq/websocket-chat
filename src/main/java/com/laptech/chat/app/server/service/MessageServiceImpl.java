package com.laptech.chat.app.server.service;


import com.laptech.chat.app.server.model.ChatMessage;
import com.laptech.chat.app.server.repository.MessageRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

  @Autowired
  private MessageRepository messageRepository;

  @Override
  public List<ChatMessage> getMessages() {
    return messageRepository.findAll();
  }

  @Override
  public void saveMessage(ChatMessage message) {
    messageRepository.save(message);
  }
}
