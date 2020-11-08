package com.project.LearnAndTrade.Controller;

import com.project.LearnAndTrade.Entity.ChatMessage;
import com.project.LearnAndTrade.Entity.MessageStatus;
import com.project.LearnAndTrade.Exception.ResourceNotFoundException;
import com.project.LearnAndTrade.Service.ChatMessageService;
import com.project.LearnAndTrade.Service.ChatRoomService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
public class ChatControllerTests {

    @Autowired
    private final ChatRoomService chatRoomService;

    @Autowired
    private final ChatMessageService chatMessageService;

    private final ChatMessage chatMessage1;
//    private final ChatMessage chatMessage2;

    public ChatControllerTests() {
        chatMessage1 = new ChatMessage("", "", "chatTest1", "chatTest2", "testName1", "testName2", "Contenido del mensaje enviado", new Date(),
                MessageStatus.DELIVERED);
//        chatMessage2 = new ChatMessage("", "", "chatTest1", "chatTest2", "testName1", "testName2", "Contenido del mensaje recibido", new Date(), MessageStatus.RECEIVED);

        chatRoomService = new ChatRoomService();
        chatMessageService = new ChatMessageService();
    }

    @Test
    @Order(1)
    public void canGetChat() {
        Optional<String> chatId = chatRoomService
                .getChatId(chatMessage1.getSenderId(), chatMessage1.getRecipientId(), false);
        assertTrue(chatId.isPresent());
        chatMessage1.setChatId(chatId.get());
        assertNotEquals("", chatMessage1.getId());
        System.out.println("1. 'canGetChat' test passed");
    }

    @Test
    @Order(2)
    public void cantGetChat() {
        Optional<String> chatId = chatRoomService
                .getChatId(null, null, false);
        assertFalse(chatId.isPresent());
        System.out.println("2. 'cantGetChat' test passed");
    }

    @Test
    @Order(3)
    public void canCountNewMessages() {
        long result = chatMessageService.countNewMessages(chatMessage1.getSenderId(), chatMessage1.getRecipientId());
        assertTrue(result >= 0);
        System.out.println("3. 'canCountNewMessages' test passed");
    }

    @Test
    @Order(4)
    public void cantCountNewMessages() {
        long result = chatMessageService.countNewMessages(null, null);
        assertTrue(result >= 0);
        System.out.println("4. 'cantCountNewMessages' test passed");
    }

    @Test
    @Order(5)
    public void canReadChat() {
        List<ChatMessage> result = chatMessageService.findChatMessages(chatMessage1.getSenderId(), chatMessage1.getRecipientId());
        assertNotNull(result);
        System.out.println("5. 'canReadChat' test passed");
    }

    @Test
    @Order(6)
    public void cantReadChat() {
        List<ChatMessage> result = chatMessageService.findChatMessages(null, null);
        assertTrue(result.isEmpty());
        System.out.println("6. 'cantReadChat' test passed");
    }

    @Test
    @Order(7)
    public void canFinMessage() {
        ChatMessage result = chatMessageService.findById(chatMessage1.getId());
        assertNotNull(result);
        System.out.println("7. 'canFinMessage' test passed");
    }

    @Test
    @Order(8)
    public void cantFindMessage() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            ChatMessage result = chatMessageService.findById("");
            assertNull(result);
        });
        System.out.println("8. 'cantFindMessage' test passed");
    }

}
