package com.laptech.chat.app.server.controller;

import com.laptech.chat.app.server.model.ChatUserPrincipal;
import java.security.Principal;
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
	public String userInfo(Principal principal) {
		return principal.getName();
	}
}
