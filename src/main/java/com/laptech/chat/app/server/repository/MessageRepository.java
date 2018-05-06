package com.laptech.chat.app.server.repository;


import com.laptech.chat.app.server.model.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<ChatMessage, String> {

}
