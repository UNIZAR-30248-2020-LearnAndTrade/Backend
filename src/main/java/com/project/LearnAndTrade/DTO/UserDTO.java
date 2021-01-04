/*
 * UserDTO.java 1.0 2/11/2020
 */

/*
  This class represents the DTO (Data Transfer Object) of the entity User

  @author Gonzalo Berné
  @version 1.0, 2/11/2020
 */

package com.project.LearnAndTrade.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Schema(name = "UserDTO", type = "object")
@Getter
@AllArgsConstructor
public class UserDTO {

    @Schema(
            description = "Username of the user",
            example = "jorge",
            required = true
    )
    private String username;

    @Schema(
            description = "Email of the user",
            example = "jorge@gmail.com",
            required = true
    )
    private String email;

    @Schema(
            description = "Password of the user",
            example = "password123",
            required = true
    )
    private String password;

    @Schema(
            description = "Interests of the user",
            example = "[\"Baile\", \"Piano\"]",
            type = "array",
            required = true
    )
    private List<ThemeDTO> interests;

    @Schema(
            description = "Interests of the user",
            example = "[\"Programación\", \"Violín\"]",
            type = "array",
            required = true
    )
    private List<ThemeDTO> knowledges;

    @Schema(
            description = "Name of the user",
            example = "Jorge",
            required = true
    )
    private String name;

    @Schema(
            description = "Surname of the user",
            example = "Turbica",
            required = true
    )
    private String surname;

    @Schema(
            description = "Birthdate of the user",
            example = "05/06/1197",
            type = "Date",
            required = true
    )
    private Date birthDate;

    @Schema(
            description = "URL where the image of the user is stored",
            example = "https://learn-and-trade-backend.herokuapp.com/image.jpg",
            required = true
    )
    private String imageUrl;

}
