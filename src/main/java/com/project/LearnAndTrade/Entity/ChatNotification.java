/*
 * ChatNotification.java 1.0 2/11/2020
 */

/*
  This class represents the entity ChatNotification.

  A ChatNotification represents a notification of messages between users.

  @author Gonzalo Berné
  @version 1.0, 2/11/2020
 */

package com.project.LearnAndTrade.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatNotification {
    private String id;
    private String senderId;
    private String senderName;
}