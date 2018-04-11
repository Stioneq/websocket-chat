package com.laptech.chat.app.client;

import lombok.Getter;


public class UserInfo {

  @Getter
  private String id = java.util.UUID.randomUUID().toString();

}
