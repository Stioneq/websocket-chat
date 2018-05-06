package com.laptech.chat.app.server.controller.rest;

import com.laptech.chat.app.server.model.UserEntity;
import com.laptech.chat.app.server.repository.UserRepository;
import com.laptech.chat.app.server.service.JwtTokenService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {



	@Autowired
	private JwtTokenService tokenService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@PostMapping("/sign-up")
	public void signUp(@RequestBody UserEntity userEntity){
		if(!userRepository.findByUsername(userEntity.getUsername()).isPresent()) {
			userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
			userRepository.save(userEntity);
		}
	}

	@PostMapping("/login")
	public String login(@RequestBody UserEntity userEntity){
		return tokenService.generateToken(userEntity);
	}

	@GetMapping
	public Principal user(Principal principal){
		return principal;
	}
}
