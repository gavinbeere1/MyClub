package com.FYP.Club.services;

import com.FYP.Club.Notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
  
  // The SimpMessagingTemplate is used to send Stomp over WebSocket messages.
  @Autowired
  private SimpMessagingTemplate messagingTemplate;
  
  /**
   * Send notification to users subscribed on channel "/user/queue/notify".
   *
   * The message will be sent only to the user with the given username.
   * 
   * @param notification The notification message.
   * @param username The username for the user to send notification.
   */
  public void notify(Notification notification, String userName) {
    messagingTemplate.convertAndSendToUser(
      userName, 
      "/queue/notify", 
      notification
    );
    return;
  }
  
} 