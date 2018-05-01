package com.laptech.chat.app.server;

import com.laptech.chat.app.server.model.UserEntity;
import com.laptech.chat.app.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AppApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		userRepository.deleteAll();
		UserEntity userEntity = new UserEntity();
		userEntity.setEmail("stioneq@gmail.com");
		userEntity.setUsername("admin");
		userEntity.setPassword(passwordEncoder.encode("admin"));

    UserEntity userEntity2 = new UserEntity();
    userEntity2.setEmail("stioneq@gmail.com");
    userEntity2.setUsername("userEntity");
    userEntity2.setPassword(passwordEncoder.encode("userEntity"));

    userRepository.insert(userEntity);
		userRepository.insert(userEntity2);
	}
}
