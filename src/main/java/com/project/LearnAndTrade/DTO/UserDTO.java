/*
 * UserDTO.java 1.0 2/11/2020
 */

/*
  This class represents the DTO (Data Transfer Object) of the entity User

  @author Gonzalo Berné
  @version 1.0, 2/11/2020
 */

package com.project.LearnAndTrade.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
public class UserDTO {

    @Getter
    private String username;

    @Getter
    private String email;

    @Getter
    private List<String> interests;

    @Getter
    private List<String> knowledges;

    @Getter
    private String name;

    @Getter
    private String surname;

    @Getter
    private Date birthDate;

}
