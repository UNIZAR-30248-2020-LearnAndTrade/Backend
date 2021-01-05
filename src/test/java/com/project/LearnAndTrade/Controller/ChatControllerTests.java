package com.project.LearnAndTrade.Controller;

import com.project.LearnAndTrade.Entity.ChatMessage;
import com.project.LearnAndTrade.Entity.ChatRoom;
import com.project.LearnAndTrade.Entity.MessageStatus;
import com.project.LearnAndTrade.Exception.ResourceNotFoundException;
import com.project.LearnAndTrade.Service.ChatMessageService;
import com.project.LearnAndTrade.Service.ChatRoomService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ChatControllerTests {

    @Autowired
    private final ChatController chatController;

    @Autowired
    private final ChatRoomService chatRoomService;

    @Autowired
    private final ChatMessageService chatMessageService;

    private ChatMessage chatMessage1;

    public ChatControllerTests() {
        this.chatController = new ChatController();
        chatRoomService = new ChatRoomService();
        chatMessageService = new ChatMessageService();
        chatMessage1 = new ChatMessage("5ff3364f5f50f96068b97c3c", "prueba1_prueba2", "prueba1", "prueba2", "Test User 1",
                "Test User 2", "Mensaje de prueba para el test", new Date(), MessageStatus.DELIVERED, "Message");
    }

    @BeforeEach
    public void beforeEach() {
        chatMessage1 = new ChatMessage("5ff3364f5f50f96068b97c3c", "prueba1_prueba2", "prueba1", "prueba2", "Test User 1",
                "Test User 2", "Mensaje de prueba para el test", new Date(), MessageStatus.DELIVERED, "Message");
    }

    @AfterAll
    public void after() {
        chatMessageService.deleteById(chatMessage1.getId());
        chatRoomService.deleteChatRoom(chatMessage1.getChatId());
    }

    @Test
    @Order(1)
    public void canProcessMessage() {
        ResponseEntity<Void> responseEntity = chatController.processMessage(chatMessage1);
        Assertions.assertEquals(200, responseEntity.getStatusCode().value());
        System.out.println("1. 'canProcessMessage' test passed");
    }

    @Test
    @Order(2)
    public void cantProcessMessage() {
        chatMessage1.setRecipientId(null);
        ResponseEntity<Void> responseEntity = chatController.processMessage(chatMessage1);
        Assertions.assertEquals(404, responseEntity.getStatusCode().value());
        System.out.println("2. 'cantProcessMessage' test passed");
    }

    @Test
    @Order(3)
    public void canCountNewMessages() {
        ResponseEntity<Long> responseEntity = chatController.countNewMessages(chatMessage1.getSenderId(), chatMessage1.getRecipientId());
        Assertions.assertEquals(200, responseEntity.getStatusCode().value());
        Assertions.assertNotNull(responseEntity.getBody());
        long result = responseEntity.getBody();
        Assertions.assertTrue(result >= 0);
        System.out.println("3. 'canCountNewMessages' test passed");
    }

    @Test
    @Order(4)
    public void cantCountNewMessages() {
        ResponseEntity<Long> responseEntity = chatController.countNewMessages(null, null);
        Assertions.assertEquals(404, responseEntity.getStatusCode().value());
        System.out.println("4. 'cantCountNewMessages' test passed");
    }

    @Test
    @Order(5)
    public void canReadChat() {
        ResponseEntity<List<ChatMessage>> responseEntity = chatController.findChatMessages(chatMessage1.getSenderId(), chatMessage1.getRecipientId());
        Assertions.assertEquals(200, responseEntity.getStatusCode().value());
        List<ChatMessage> result = responseEntity.getBody();
        Assertions.assertNotNull(result);
        if (!result.isEmpty()) {
            ChatMessage chatMessage = result.get(0);
            Assertions.assertNotNull(chatMessage.getId());
            Assertions.assertNotNull(chatMessage.getChatId());
            Assertions.assertNotNull(chatMessage.getSenderId());
            Assertions.assertNotNull(chatMessage.getRecipientId());
            Assertions.assertNotNull(chatMessage.getSenderName());
            Assertions.assertNotNull(chatMessage.getRecipientName());
            Assertions.assertNotNull(chatMessage.getContent());
            Assertions.assertNotNull(chatMessage.getTimestamp());
            Assertions.assertNotNull(chatMessage.getStatus());
            Assertions.assertNotNull(chatMessage.getType());
        }
        System.out.println("5. 'canReadChat' test passed");
    }

    @Test
    @Order(6)
    public void cantReadChat() {
        ResponseEntity<List<ChatMessage>> responseEntity = chatController.findChatMessages(null, null);
        Assertions.assertEquals(404, responseEntity.getStatusCode().value());
        System.out.println("6. 'cantReadChat' test passed");
    }

    @Test
    @Order(7)
    public void canFindMessage() {
        ResponseEntity<ChatMessage> responseEntity = chatController.findMessage(chatMessage1.getId());
        Assertions.assertEquals(200, responseEntity.getStatusCode().value());
        ChatMessage result = responseEntity.getBody();
        Assertions.assertNotNull(result);
        System.out.println("7. 'canFindMessage' test passed");
    }

    @Test
    @Order(8)
    public void cantFindMessage() {
        ResponseEntity<ChatMessage> responseEntity = chatController.findMessage("");
        Assertions.assertEquals(404, responseEntity.getStatusCode().value());
        System.out.println("8. 'cantFindMessage' test passed");
    }

    @Test
    @Order(9)
    public void canFindRoom() {
        ResponseEntity<ChatRoom> responseEntity = chatController.findRoom(chatMessage1.getSenderId(), chatMessage1.getRecipientId());
        Assertions.assertEquals(200, responseEntity.getStatusCode().value());
        ChatRoom result = responseEntity.getBody();
        Assertions.assertNotNull(result);
        System.out.println("7. 'canFindMessage' test passed");
    }

    @Test
    @Order(10)
    public void cantFindRoom() {
        ResponseEntity<ChatRoom> responseEntity = chatController.findRoom(null, null);
        Assertions.assertEquals(404, responseEntity.getStatusCode().value());
        System.out.println("8. 'cantFindMessage' test passed");
    }

    @Test
    @Order(11)
    public void canFindRooms() {
        ResponseEntity<List<ChatRoom>> responseEntity = chatController.findRooms(chatMessage1.getSenderId());
        Assertions.assertEquals(200, responseEntity.getStatusCode().value());
        List<ChatRoom> result = responseEntity.getBody();
        Assertions.assertNotNull(result);
        System.out.println("7. 'canFindMessage' test passed");
    }

    @Test
    @Order(12)
    public void cantFindRooms() {
        ResponseEntity<List<ChatRoom>> responseEntity = chatController.findRooms(null);
        Assertions.assertEquals(404, responseEntity.getStatusCode().value());
        System.out.println("8. 'cantFindMessage' test passed");
    }

}
