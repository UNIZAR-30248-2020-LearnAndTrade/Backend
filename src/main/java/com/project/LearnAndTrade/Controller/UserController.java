/*
 * UserController.java 1.0 26/10/2020
 */

/*
  This controller acts for the client petitions about users

  @author Gonzalo Berné
  @author Eduardo Gimeno
  @version 4.0, 30/10/2020
 */

package com.project.LearnAndTrade.Controller;

import com.project.LearnAndTrade.DTO.UserDTO;
import com.project.LearnAndTrade.Entity.User;
import com.project.LearnAndTrade.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@CrossOrigin(origins = "*", methods = {RequestMethod.GET})
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private LogInUser logInUser;

    @Autowired
    private GetUserData getUserData;

    @Autowired
    private UpdateUserData updateUserData;

    @Autowired
    private SearchComplementaryUsers searchComplementaryUsers;

    @Autowired
    private ParserUserDTO parserUserDTO;

    @GetMapping(path = "/login")
    public ResponseEntity<Object> logIn(String name, String password) {
        Optional<User> userOptional = logInUser.logIn(name, password);
        if (userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(parserUserDTO.userToUserDTO(userOptional.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
        }
    }

    @GetMapping(path = "/getuser")
    public ResponseEntity<Object> getUser(String username) {
        Optional<User> userOptional = getUserData.getUser(username);
        if (userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(parserUserDTO.userToUserDTO(userOptional.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
        }
    }

    @PostMapping(path = "/updateuser")
    public ResponseEntity<Object> updateInterests(@RequestBody UserDTO userDTO) {
        try {
            Optional<User> userOptional = parserUserDTO.userDTOToUser(userDTO);
            if (userOptional.isPresent()) {
                User updatedUser = updateUserData.updateUser(userOptional.get());
                return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping(path = "/getcomplementaryusers")
    public ResponseEntity<Object> searchComplementaryUsers(String username) {
        Optional<User> userOptional = getUserData.getUser(username);
        if (userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(searchComplementaryUsers.searchUsers(userOptional.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
        }
    }

}
