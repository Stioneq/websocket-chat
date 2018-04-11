package com.laptech.chat.app.client.message;


import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageActionLocator {

  @Autowired
  List<MessageAction> actions;

  public void action(String message) {
    actions.stream()
        .filter(a -> Arrays
        .stream(AopUtils.getTargetClass(a).getAnnotationsByType(ActionType.class))
        .anyMatch(type -> message.startsWith(type.value()))).findAny()
        .orElse(s -> log.error("No processors found for {}", message)).action(message);
  }
}
