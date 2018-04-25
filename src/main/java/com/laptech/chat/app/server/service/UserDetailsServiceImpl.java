package com.laptech.chat.app.server.service;

import com.laptech.chat.app.server.model.ChatUserPrincipal;
import com.laptech.chat.app.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    return userRepository
        .findByUsername(username)
        .map(ChatUserPrincipal::new)
        .orElseThrow(() -> new UsernameNotFoundException(username));
  }
}
