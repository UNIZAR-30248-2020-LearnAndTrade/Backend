/*
 * ChatMessage.java 1.0 2/11/2020
 */

/*
  This class represents the entity ChatMessage.

  A ChatMessage represents a message in a conversation between two users.
  Every ChatMessage has a different "id" and the variable "chatId" represents the room
    in which the message belongs.
  The sender is the user who delivers the message, the recipients is the user who receives the message.

  @author Gonzalo Berné
  @version 1.0, 2/11/2020
 */

package com.project.LearnAndTrade.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "Chats")
public class ChatMessage {
    @Id
    private String id;
    private String chatId;
    private String senderId;
    private String recipientId;
    private String senderName;
    private String recipientName;
    private String content;
    private Date timestamp;
    private MessageStatus status;
}
