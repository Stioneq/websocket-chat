package com.laptech.chat.app.server.controller.rest;

import com.laptech.chat.app.server.model.ChatMessage;
import com.laptech.chat.app.server.service.MessageService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

  @Autowired
  private MessageService messageRepository;

  @GetMapping
  public List<ChatMessage> getMessages() {

    return messageRepository.getMessages();
  }
}
