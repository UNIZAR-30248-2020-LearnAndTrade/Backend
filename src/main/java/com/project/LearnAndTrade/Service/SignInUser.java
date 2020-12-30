/*
 * SignInUser.java 1.0 30/12/2020
 */

/*
  This service signs in a new user

  @author Eduardo Gimeno
  @version 1.0, 30/12/2020
 */

package com.project.LearnAndTrade.Service;

import com.project.LearnAndTrade.Entity.User;
import com.project.LearnAndTrade.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignInUser {

    @Autowired
    private UserRepository userRepository;

    public User signIn(User user) throws IllegalArgumentException {
        return userRepository.save(user);
    }

}
