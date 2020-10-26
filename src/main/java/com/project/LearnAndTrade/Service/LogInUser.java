/*
 * LogInUser.java 1.0 26/10/2020
 */

/*
  This service checks the log in of an user

  @author Gonzalo Berné
  @version 1.0, 26/10/2020
 */

package com.project.LearnAndTrade.Service;

import com.project.LearnAndTrade.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogInUser {

    @Autowired
    private UserRepository userRepository;

    public boolean logIn(String name, String password) {
        if (!userRepository.existsByEmailAndPassword(name, password)) {
            return userRepository.existsByUsernameAndPassword(name, password);
        } else {
            return true;
        }
    }
}
