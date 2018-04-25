package com.laptech.chat.app.client;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Data
@Component
public class UserInfo {

  private String token;
  private String name;

}
