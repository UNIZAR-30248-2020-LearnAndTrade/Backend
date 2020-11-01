package com.project.LearnAndTrade.Service;

import com.project.LearnAndTrade.Entity.User;
import com.project.LearnAndTrade.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UpdateUserData {

    @Autowired
    private UserRepository userRepository;

    public String updateUser(User user) {
        try {
            userRepository.save(user);
            return "OK";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

}