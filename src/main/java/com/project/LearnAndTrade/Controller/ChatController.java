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
import com.project.LearnAndTrade.Entity.ChatRoom;
import com.project.LearnAndTrade.Service.ChatMessageService;
import com.project.LearnAndTrade.Service.ChatRoomService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@Api(tags = "Chat")
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
    @Operation(
            summary = "Save message in a chat",
            description = "This method receives a new ChatMessage.\n" +
                    "The message is saved in the database and it is sent to the other user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success adding message"),
                    @ApiResponse(responseCode = "404", description = "Error adding message"),
            })
    @MessageMapping("/chat")
    public ResponseEntity<Void> processMessage(
            @Parameter(description = "Chat message user wants to send", required = true) @Payload ChatMessage chatMessage
    ) {
        try {
            // By the "senderId" and the "recipientId" of the message,
            // the ChatRoomService get the "chatId" of the ChatRoom which it belongs.
            Optional<String> chatId = chatRoomService
                    .getChatId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true);
            chatMessage.setChatId(chatId.get());

            // The message is saved in the database.
            ChatMessage saved = chatMessageService.save(chatMessage);

            // The message is sent to the recipient user.
            messagingTemplate.convertAndSendToUser(
                    chatMessage.getRecipientId(), "/queue/messages",
                    new ChatNotification(
                            saved.getId(),
                            saved.getSenderId(),
                            saved.getSenderName()));
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /*
            This method counts the messages without read in a chat by a "senderId" and a "recipientId".
     */
    @Operation(
            summary = "Count non-read messages in a chat",
            description = "This method counts the messages without read in a chat by a \"senderId\" and a \"recipientId\"",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful non-read message count"),
                    @ApiResponse(responseCode = "404", description = "Error counting non-read messages"),
            })
    @GetMapping(path = "/messages/{senderId}/{recipientId}/count", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> countNewMessages(
            @Parameter(description = "Sender ID", required = true) @PathVariable String senderId,
            @Parameter(description = "Recipient ID", required = true) @PathVariable String recipientId
    ) {
        try {
            Long numMessages = chatMessageService.countNewMessages(senderId, recipientId);
            return ResponseEntity.ok(numMessages);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /*
            This method counts the messages without read in a chat by a "recipientId".
     */
    @Operation(
            summary = "Count non-read messages in a chat",
            description = "This method counts the messages without read in a chat by a \"recipientId\"",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful non-read message count"),
                    @ApiResponse(responseCode = "404", description = "Error counting non-read messages"),
            })
    @GetMapping(path = "/messages/{recipientId}/count", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> countNewMessages(
            @Parameter(description = "Recipient ID", required = true) @PathVariable String recipientId
    ) {
        return ResponseEntity
                .ok(chatMessageService.countAnyNewMessages(recipientId));
    }

    /*
            This method gets the messages in a chat by a "senderId" and a "recipientId".
     */
    @Operation(
            summary = "This method gets the messages in a chat",
            description = "This method gets the messages in a chat by a \"senderId\" and a \"recipientId\"",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful messages reading"),
                    @ApiResponse(responseCode = "404", description = "Error messages reading"),
            })
    @GetMapping(path = "/messages/{senderId}/{recipientId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ChatMessage>> findChatMessages(
            @Parameter(description = "Sender ID", required = true) @PathVariable String senderId,
            @Parameter(description = "Recipient ID", required = true) @PathVariable String recipientId) {
        try {
            List<ChatMessage> list = chatMessageService.findChatMessages(senderId, recipientId);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /*
            This method gets one message by its "id".
     */
    @Operation(
            summary = "This method gets the message by it's ID",
            description = "This method gets the message by it's ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful messages reading"),
                    @ApiResponse(responseCode = "404", description = "Error messages reading"),
            })
    @GetMapping(path = "/messages/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ChatMessage> findMessage(
            @Parameter(description = "Message ID", required = true) @PathVariable String id
    ) {
        try {
            ChatMessage chatMessage = chatMessageService.findById(id);
            return ResponseEntity.ok(chatMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    /*
            This method gets one room by its "senderId" and "recipientId".
     */
    @Operation(
            summary = "This method gets one room by its \"senderId\" and \"recipientId\"",
            description = "This method gets one room by its \"senderId\" and \"recipientId\"",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful room get"),
                    @ApiResponse(responseCode = "404", description = "Error room reading"),
            })
    @GetMapping(path = "/rooms/{senderId}/{recipientId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ChatRoom> findRoom(
            @Parameter(description = "Sender ID", required = true) @PathVariable String senderId,
            @Parameter(description = "Recipient ID", required = true) @PathVariable String recipientId
    ) {
        try {
            Optional<ChatRoom> chatRoom = chatRoomService.getChatRoom(senderId, recipientId, true);
            return chatRoom.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /*
            This method gets all the room by its "senderId"".
     */
    @Operation(
            summary = "This method gets all the room by its \"senderId\"",
            description = "This method gets all the room by its \"senderId\"",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful rooms get"),
                    @ApiResponse(responseCode = "404", description = "Error rooms reading"),
            })
    @GetMapping(path = "/rooms/{senderId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ChatRoom>> findRooms(
            @Parameter(description = "Sender ID", required = true) @PathVariable String senderId
    ) {
        try {
            List<ChatRoom> list = chatRoomService.getChatRooms(senderId);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}