package com.laptech.chat.app.client;

import com.laptech.chat.app.client.message.MessageActionLocator;
import com.laptech.chat.app.client.socket.WebSocket;
import java.net.URISyntaxException;
import java.util.Scanner;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:client.yml")
public class SimpleWSClient {

  @Autowired
  private MessageActionLocator messageActionLocator;

  @Autowired
  private WebSocket websocketClient;
  @Value("${websockets.server.url:ws://localhost:8080/ws}")
  private String url;

  @PostConstruct
  public void init() throws URISyntaxException {

    websocketClient.connect(url);
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

      } while (true);
    }
  }
}
