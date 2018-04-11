package com.laptech.chat.app.client.message;


import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@ActionType("/help")
@Component
public class HelpMessageAction implements MessageAction {

  @Autowired
  private List<MessageAction> action;

  private String helpText;

  @PostConstruct
  public void init() {
    this.helpText = "Chat client: \n List of all commands: \n"+action.stream()
        .filter(a -> AopUtils.getTargetClass(a).isAnnotationPresent(ActionType.class))
        .map(a -> AopUtils.getTargetClass(a).getAnnotation(ActionType.class))
        .map(a -> a.value() + " -- " + a.description()).collect(
            Collectors.joining("\n"));
  }

  @Override
  public void action(String message) {
    System.out.println(helpText);
  }
}
