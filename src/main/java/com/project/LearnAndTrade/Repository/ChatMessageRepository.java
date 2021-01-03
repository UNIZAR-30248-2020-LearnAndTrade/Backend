/*
  ChatMessageRepository.java 1.0 2/11/2020
 */

/*
  This interface is the repository for ChatMessage
  used for keep the persistence for the ChatMessage entity.
  This persistence is done in MongoDB (document format).

  @author Gonzalo Berné
  @version 1.0, 2/11/2020
 */


package com.project.LearnAndTrade.Repository;

import com.project.LearnAndTrade.Entity.ChatMessage;
import com.project.LearnAndTrade.Entity.MessageStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    long countBySenderIdAndRecipientIdAndStatus(
            String senderId, String recipientId, MessageStatus status);

    List<ChatMessage> findByChatIdAndType(String chatId, String type);
}