/*
 * UpdateUserData.java 1.0 22/12/2020
 */

/*
  This service updates the information of a user

  @author Eduardo Gimeno
  @version 1.0, 22/12/2020
 */

package com.project.LearnAndTrade.Service;

import com.project.LearnAndTrade.Entity.User;
import com.project.LearnAndTrade.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserData {

    @Autowired
    private UserRepository userRepository;

    public User updateUser(User user) throws IllegalArgumentException {
        return userRepository.save(user);
    }

}
