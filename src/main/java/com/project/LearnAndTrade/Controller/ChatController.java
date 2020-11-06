/*
 * ChatController.java 1.0 2/11/2020
 */

/*
  This controller acts for the client petitions about the chat

  @author Gonzalo Berné
  @version 1.0, 2/11/2020
 */

package com.project.LearnAndTrade.Controller;

import com.project.LearnAndTrade.Entity.ChatMessage;
import com.project.LearnAndTrade.Entity.ChatNotification;
import com.project.LearnAndTrade.Service.ChatMessageService;
import com.project.LearnAndTrade.Service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private ChatRoomService chatRoomService;

    /*
        This method receives a new ChatMessage.
        The message is saved in the database and it is sent to the other user.
     */
    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage) {

        /*
            By the "senderId" and the "recipientId" of the message,
            the ChatRoomService get the "chatId" of the ChatRoom which it belongs.
         */
        Optional<String> chatId = chatRoomService
                .getChatId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true);
        chatMessage.setChatId(chatId.get());

        // The message is saved in the database.
        ChatMessage saved = chatMessageService.save(chatMessage);

        // The message is sent to the recipient user.
        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientId(),"/queue/messages",
                new ChatNotification(
                        saved.getId(),
                        saved.getSenderId(),
                        saved.getSenderName()));
    }

    /*
            This method counts the messages without read in a chat by a "senderId" and a "recipientId".
     */
    @GetMapping("/messages/{senderId}/{recipientId}/count")
    public ResponseEntity<Long> countNewMessages(
            @PathVariable String senderId,
            @PathVariable String recipientId) {

        return ResponseEntity
                .ok(chatMessageService.countNewMessages(senderId, recipientId));
    }

    /*
            This method gets the messages in a chat by a "senderId" and a "recipientId".
     */
    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<?> findChatMessages ( @PathVariable String senderId,
                                                @PathVariable String recipientId) {
        return ResponseEntity
                .ok(chatMessageService.findChatMessages(senderId, recipientId));
    }

    /*
            This method gets one message by its "id".
     */
    @GetMapping("/messages/{id}")
    public ResponseEntity<?> findMessage ( @PathVariable String id) {
        return ResponseEntity
                .ok(chatMessageService.findById(id));
    }
}