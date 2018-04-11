package com.laptech.chat.app.client.message;


import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageActionLocator {

  @Autowired
  private List<MessageAction> actions;

  @Autowired
  @Qualifier("defaultMessageAction")
  private MessageAction messageAction;

  public void action(String message) {
    log.info("Handle {}\n\n\n", message);
    actions.stream()
        .filter(a -> Arrays
            .stream(AopUtils.getTargetClass(a).getAnnotationsByType(ActionType.class))
            .anyMatch(type -> message.split(" ")[0].equals(type.value()))).findAny()
        .orElse(messageAction).action(message);
  }
}
