package com.laptech.chat.app.server.repository;

import com.laptech.chat.app.server.model.UserEntity;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity, String> {
  Optional<UserEntity> findByUsername(String username);
}
