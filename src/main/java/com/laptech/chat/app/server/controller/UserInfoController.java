package com.laptech.chat.app.server.controller;

import com.laptech.chat.app.server.model.UserInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping("/api/userInfo")
public class UserInfoController {


	@GetMapping
	public UserInfo userInfo() {
		UserInfo userInfo = new UserInfo();
		userInfo.setName("Stioneq");

		return userInfo;
	}
}
