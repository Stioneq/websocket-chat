package com.laptech.chat.app.server.security;

import java.util.Collection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class JwtToken implements Authentication {

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public Object getDetails() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return null;
  }

  @Override
  public boolean isAuthenticated() {
    return false;
  }

  @Override
  public void setAuthenticated(boolean b) throws IllegalArgumentException {

  }

  @Override
  public String getName() {
    return null;
  }
}
