package com.laptech.chat.app.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class SimpleWSClient {

  private final WebsocketClientEndpoint websocketClientEndpoint;

  public SimpleWSClient() throws URISyntaxException {
    websocketClientEndpoint = new WebsocketClientEndpoint(
        new URI("ws://localhost:8080/ws"));
    websocketClientEndpoint.addMessageHandler(x -> System.out.println(x));

  }

  public static void main(String[] args) throws URISyntaxException {

    SimpleWSClient simpleWSClient = new SimpleWSClient();
    simpleWSClient.handleInput();

  }


  void handleInput() {
    try (Scanner scanner = new Scanner(System.in)) {
      String message;

      do {
        message = scanner.nextLine();
        if("exit".equals(message)){
          break;
        }
        websocketClientEndpoint.sendText(message);

      } while (true);
    }
  }
}
