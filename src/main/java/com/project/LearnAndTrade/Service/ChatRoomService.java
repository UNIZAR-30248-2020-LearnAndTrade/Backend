package com.project.LearnAndTrade.Service;

import com.project.LearnAndTrade.Entity.ChatRoom;
import com.project.LearnAndTrade.Repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatRoomService {

    @Autowired private ChatRoomRepository chatRoomRepository;

    public Optional<String> getChatId(
            String senderId, String recipientId, boolean createIfNotExist) {

        Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findBySenderIdAndRecipientId(senderId, recipientId);
        if (chatRoomOptional.isPresent()) {
            return Optional.of(chatRoomOptional.get().getChatId());
        } else {
            if(!createIfNotExist) {
                return  Optional.empty();
            }
            String chatId = String.format("%s_%s", senderId, recipientId);

            ChatRoom senderRecipient = ChatRoom
                    .builder()
                    .chatId(chatId)
                    .senderId(senderId)
                    .recipientId(recipientId)
                    .build();

            ChatRoom recipientSender = ChatRoom
                    .builder()
                    .chatId(chatId)
                    .senderId(recipientId)
                    .recipientId(senderId)
                    .build();
            chatRoomRepository.save(senderRecipient);
            chatRoomRepository.save(recipientSender);

            return Optional.of(chatId);
        }
    }
}