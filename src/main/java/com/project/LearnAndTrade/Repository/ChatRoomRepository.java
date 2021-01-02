/*
  ChatRoomRepository.java 1.0 2/11/2020
 */

/*
  This interface is the repository for ChatRoom
  used for keep the persistence for the ChatRoom entity.
  This persistence is done in MongoDB (document format).

  @author Gonzalo Berné
  @version 1.0, 2/11/2020
 */

package com.project.LearnAndTrade.Repository;

import com.project.LearnAndTrade.Entity.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {

    Optional<ChatRoom> findFirstBySenderIdAndRecipientId(String senderId, String recipientId);
}