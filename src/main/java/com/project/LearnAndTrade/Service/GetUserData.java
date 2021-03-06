/*
 * GetUserData.java 1.0 27/10/2020
 */

/*
  This service gets the information of a user

  @author Eduardo Gimeno
  @version 1.0, 27/10/2020
 */

package com.project.LearnAndTrade.Service;

import com.project.LearnAndTrade.Entity.User;
import com.project.LearnAndTrade.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetUserData {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUser(String username) {
        return userRepository.findByUsername(username);
    }

}
