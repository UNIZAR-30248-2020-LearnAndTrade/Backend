/*
 * ChatMessageService.java 1.0 2/11/2020
 */

/*
  This file has all the services referred to the chat messages (ChatMessage entity).

  @author Gonzalo Berné
  @version 1.0, 2/11/2020
 */

package com.project.LearnAndTrade.Service;

import com.project.LearnAndTrade.Entity.ChatMessage;
import com.project.LearnAndTrade.Entity.MessageStatus;
import com.project.LearnAndTrade.Exception.ResourceNotFoundException;
import com.project.LearnAndTrade.Repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChatMessageService {

    @Autowired
    private ChatMessageRepository repository;

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private MongoOperations mongoOperations;

    /*
        This service save a ChatMessage in the database.
     */
    public ChatMessage save(ChatMessage chatMessage) {
        chatMessage.setStatus(MessageStatus.RECEIVED);
        repository.save(chatMessage);
        return chatMessage;
    }

    /*
        This service counts all the recieved messages in a chat between two users by
            a "senderId" and a "recipientId".
     */
    public long countNewMessages(String senderId, String recipientId) throws IllegalArgumentException {
        Assert.notNull(senderId, "senderId must not be null");
        Assert.notNull(recipientId, "recipientId must not be null");
        return repository.countBySenderIdAndRecipientIdAndStatusAndType(senderId, recipientId, MessageStatus.RECEIVED, "Message");
    }

    /*
        This service counts all the recieved messages in a chat between two users by
            a "senderId" and a "recipientId".
     */
    public long countAnyNewMessages(String recipientId) {
        return repository.countByRecipientIdAndStatusAndType(recipientId, MessageStatus.RECEIVED, "Message");
    }

    /*
        This service finds all the messages in a chat between two users by
            a "senderId" and a "recipientId".
     */
    public List<ChatMessage> findChatMessages(String senderId, String recipientId) throws IllegalArgumentException {
        Assert.notNull(senderId, "senderId must not be null");
        Assert.notNull(recipientId, "recipientId must not be null");
        Optional<String> chatId = chatRoomService.getChatId(senderId, recipientId, false);

        List<ChatMessage> messages =
                chatId.map(cId -> repository.findByChatIdAndType(cId, "Message")).orElse(new ArrayList<>());

        if (messages.size() > 0) {
            updateStatuses(senderId, recipientId, MessageStatus.DELIVERED);
        }

        return messages;
    }

    /*
        This service finds a messages in a chat between two users by the message "id".
     */
    public ChatMessage findById(String id) throws ResourceNotFoundException {
        return repository
                .findById(id)
                .map(chatMessage -> {
                    chatMessage.setStatus(MessageStatus.DELIVERED);
                    return repository.save(chatMessage);
                })
                .orElseThrow(() ->
                        new ResourceNotFoundException("can't find message (" + id + ")"));
    }

    /*
        This service modifies messages in a chat between two users by
            a "senderId", a "recipientId" and a MessageStatus.
     */
    public void updateStatuses(String senderId, String recipientId, MessageStatus status) {
        Query query = new Query(
                Criteria
                        .where("senderId").is(recipientId)
                        .and("recipientId").is(senderId)
                        .and("type").is("Message"));
        Update update = Update.update("status", status);
        mongoOperations.updateMulti(query, update, ChatMessage.class);
    }

    /*
        This service deletes a chat message by its id
     */
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
