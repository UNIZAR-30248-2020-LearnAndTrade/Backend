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
import com.project.LearnAndTrade.Entity.Theme;
import com.project.LearnAndTrade.Entity.User;
import com.project.LearnAndTrade.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ParserUserDTO {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParserThemeDTO parserThemeDTO;

    public UserDTO userToUserDTO(User user) {
        return new UserDTO(user.getUsername(), user.getEmail(), parserThemeDTO.themeToThemeDTOList(user.getInterests()),
                parserThemeDTO.themeToThemeDTOList(user.getKnowledges()), user.getName(), user.getSurname(),
                user.getBirthDate(), user.getImageUrl());
    }

    public Optional<User> userDTOToUser(UserDTO userDTO) {
        Optional<User> oldUser = userRepository.findByUsername(userDTO.getUsername());
        if (oldUser.isPresent()) {
            Optional<List<Theme>> interests = parserThemeDTO.themeDTOToThemeList(userDTO.getInterests());
            Optional<List<Theme>> knowledges = parserThemeDTO.themeDTOToThemeList(userDTO.getKnowledges());
            if (interests.isPresent() && knowledges.isPresent()) {
                return Optional.of(new User(userDTO.getUsername(), userDTO.getEmail(), oldUser.get().getPassword(),
                        interests.get(), knowledges.get(), userDTO.getName(), userDTO.getSurname(),
                        userDTO.getBirthDate(), userDTO.getImageUrl()));
            } else {
                if (userDTO.getInterests().isEmpty() && userDTO.getKnowledges().isEmpty()) {
                    return Optional.of(new User(userDTO.getUsername(), userDTO.getEmail(), oldUser.get().getPassword(),
                            new ArrayList<>(), new ArrayList<>(), userDTO.getName(), userDTO.getSurname(),
                            userDTO.getBirthDate(), userDTO.getImageUrl()));
                } else if (userDTO.getInterests().isEmpty()) {
                    return Optional.of(new User(userDTO.getUsername(), userDTO.getEmail(), oldUser.get().getPassword(),
                            new ArrayList<>(), knowledges.get(), userDTO.getName(), userDTO.getSurname(),
                            userDTO.getBirthDate(), userDTO.getImageUrl()));
                } else if (userDTO.getKnowledges().isEmpty()) {
                    return Optional.of(new User(userDTO.getUsername(), userDTO.getEmail(), oldUser.get().getPassword(),
                            interests.get(), new ArrayList<>(), userDTO.getName(), userDTO.getSurname(),
                            userDTO.getBirthDate(), userDTO.getImageUrl()));
                } else {
                    return Optional.empty();
                }
            }
        } else {
            return Optional.empty();
        }
    }

}
