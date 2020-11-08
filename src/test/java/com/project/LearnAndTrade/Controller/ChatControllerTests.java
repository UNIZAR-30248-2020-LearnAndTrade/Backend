package com.project.LearnAndTrade.Controller;

import com.project.LearnAndTrade.Entity.ChatMessage;
import com.project.LearnAndTrade.Entity.MessageStatus;
import com.project.LearnAndTrade.Service.ChatMessageService;
import com.project.LearnAndTrade.Service.ChatRoomService;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
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
    private final ChatMessage chatMessage2;

    public ChatControllerTests() {
        chatMessage1 = new ChatMessage("", "", "chatTest1", "chatTest2", "testName1", "testName2", "Contenido del mensaje enviado", new Date(),
                MessageStatus.DELIVERED);
        chatMessage2 = new ChatMessage("", "", "chatTest1", "chatTest2", "testName1", "testName2", "Contenido del mensaje recibido", new Date(),
                MessageStatus.RECEIVED);

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
        Optional<String> chatId = chatRoomService
                .getChatId(null, null, false);
        assertFalse(chatId.isPresent());
        System.out.println("2. 'cantGetChat' test passed");
    }





}
