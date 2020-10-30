/*
 * LogInUser.java 1.0 26/10/2020
 */

/*
  This service checks the log in of an user

  @author Gonzalo Berné
  @version 1.0, 26/10/2020
 */

package com.project.LearnAndTrade.Service;

import com.project.LearnAndTrade.Entity.User;
import com.project.LearnAndTrade.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchComplementaryUsers {

    @Autowired
    private UserRepository userRepository;

    public List<User> searchUsers(User user) {
        return userRepository.findComplementaryUsers(user.getInterests(), user.getKnowledges());
    }
}
