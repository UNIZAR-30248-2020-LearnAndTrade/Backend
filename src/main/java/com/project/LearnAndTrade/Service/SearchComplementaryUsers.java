/*
 * SearchComplementaryUsers.java 1.0 26/10/2020
 */

/*
  This service gets the complementary user from one user
  A complementary user is defined by the relation between the interests and the knowledges of users

  @author Gonzalo Berné
  @version 1.0, 30/10/2020
 */

package com.project.LearnAndTrade.Service;

import com.project.LearnAndTrade.Entity.User;
import com.project.LearnAndTrade.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchComplementaryUsers {

    @Autowired
    private UserRepository userRepository;

    public List<User> searchUsers(User user) {
        return userRepository.findComplementaryUsers(user.getInterests(), user.getKnowledges());
    }
}
