/*
 * ParserUserDTO.java 1.0 2/11/2020
 */

/*
  This class parses the Entity User to UserDTO (Data Transfer Object) and the contrary

  @author Gonzalo Berné
  @version 1.0, 2/11/2020
 */

package com.project.LearnAndTrade.Service;

import com.project.LearnAndTrade.DTO.ThemeDTO;
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
        List<ThemeDTO> interests = parserThemeDTO.themeToThemeDTOList(user.getInterests());
        List<ThemeDTO> knowledges = parserThemeDTO.themeToThemeDTOList(user.getKnowledges());
        return new UserDTO(user.getUsername(), user.getEmail(), user.getPassword(), interests, knowledges, user.getName(), user.getSurname(),
                user.getBirthDate(), user.getImageUrl());
    }

    public Optional<User> userDTOToUser(UserDTO userDTO) {
        Optional<User> oldUser = userRepository.findByUsername(userDTO.getUsername());
        if (oldUser.isPresent()) {
            List<Theme> interests = parserThemeDTO.themeDTOToThemeList(userDTO.getInterests());
            List<Theme> knowledges = parserThemeDTO.themeDTOToThemeList(userDTO.getKnowledges());
            if (!interests.isEmpty() && !knowledges.isEmpty()) {
                return Optional.of(new User(userDTO.getUsername(), userDTO.getEmail(), oldUser.get().getPassword(),
                        interests, knowledges, userDTO.getName(), userDTO.getSurname(),
                        userDTO.getBirthDate(), userDTO.getImageUrl()));
            } else {
                if (userDTO.getInterests().isEmpty() && userDTO.getKnowledges().isEmpty()) {
                    return Optional.of(new User(userDTO.getUsername(), userDTO.getEmail(), oldUser.get().getPassword(),
                            new ArrayList<>(), new ArrayList<>(), userDTO.getName(), userDTO.getSurname(),
                            userDTO.getBirthDate(), userDTO.getImageUrl()));
                } else if (userDTO.getInterests().isEmpty()) {
                    return Optional.of(new User(userDTO.getUsername(), userDTO.getEmail(), oldUser.get().getPassword(),
                            new ArrayList<>(), knowledges, userDTO.getName(), userDTO.getSurname(),
                            userDTO.getBirthDate(), userDTO.getImageUrl()));
                } else if (userDTO.getKnowledges().isEmpty()) {
                    return Optional.of(new User(userDTO.getUsername(), userDTO.getEmail(), oldUser.get().getPassword(),
                            interests, new ArrayList<>(), userDTO.getName(), userDTO.getSurname(),
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
