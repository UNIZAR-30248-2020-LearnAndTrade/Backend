/*
 * ChatRoom.java 1.0 2/11/2020
 */

/*
  This class represents the entity ChatRoom.

  A ChatRoom represents a conversation between the users (room between them).
  For every conversation, two ChatRoom are created, both with same "chatId" but different "id".
  "senderId" and "recipientId" are the ids of the users involve in the conversation.

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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "Chats")
public class ChatRoom {
    @Id
    private String id;
    private String chatId;
    private String senderId;
    private String recipientId;
}
