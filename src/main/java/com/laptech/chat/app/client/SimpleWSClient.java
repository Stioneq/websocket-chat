package com.laptech.chat.app.client;

import com.laptech.chat.app.client.message.MessageActionLocator;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SimpleWSClient {

  @Autowired
  private MessageActionLocator messageActionLocator;

  private WebsocketClientEndpoint websocketClientEndpoint;

  @PostConstruct
  public void init() throws URISyntaxException {


    websocketClientEndpoint = new WebsocketClientEndpoint(
        new URI("ws://localhost:8080/ws"));
    websocketClientEndpoint.addMessageHandler(x -> System.out.println(x));
    handleInput();
  }

  public static void main(String[] args) throws URISyntaxException {

    SpringApplication.run(SimpleWSClient.class);

  }


  void handleInput() {
    try (Scanner scanner = new Scanner(System.in)) {
      String message;

      do {
        message = scanner.nextLine();
        if("exit".equals(message)){
          break;
        }
        messageActionLocator.action(message);
        websocketClientEndpoint.sendText(message);

      } while (true);
    }
  }
}
