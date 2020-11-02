/*
 * ParserUserDTO.java 1.0 2/11/2020
 */

/*
  This class parses the Entity User to UserDTO (Data Transfer Object) and the contrary

  @author Gonzalo Berné
  @version 1.0, 2/11/2020
 */

package com.project.LearnAndTrade.Service;

import com.project.LearnAndTrade.DTO.UserDTO;
import com.project.LearnAndTrade.Entity.User;
import com.project.LearnAndTrade.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParserUserDTO {

    @Autowired
    private UserRepository userRepository;

    public UserDTO userToUserDTO(User user) {
        return new UserDTO(user.getUsername(), user.getEmail(), user.getInterests(), user.getKnowledges(),
                user.getName(), user.getSurname(), user.getBirthDate());
    }

    public Optional<User> userDTOToUser(UserDTO userDTO) {
        Optional<User> oldUser = userRepository.findByUsername(userDTO.getUsername());
        if (oldUser.isPresent()) {
            return Optional.of(new User(userDTO.getUsername(), userDTO.getEmail(), oldUser.get().getPassword(),
                    userDTO.getInterests(), userDTO.getKnowledges(), userDTO.getName(), userDTO.getSurname(),
                    userDTO.getBirthDate()));
        } else {
            return Optional.empty();
        }
    }

}
