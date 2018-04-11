package com.laptech.chat.app.client.message;

/**
 * Each message typed in client should be associated with some messageaction
 */
public interface MessageAction {

  void action(String message);
}
