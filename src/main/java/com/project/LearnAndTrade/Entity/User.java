/*
 * User.java 1.0 25/10/2020
 */

/*
  This class represents the entity User

  @author Gonzalo Berné
  @author Eduardo Gimeno
  @version 2.0, 27/10/2020
 */

package com.project.LearnAndTrade.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "User_T")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @NotNull
    @Getter
    private String username;

    @NotNull
    @Getter
    private String email;

    @NotNull
    @Getter
    private String password;

    @NotNull
    @ManyToMany
    @Getter
    private List<Theme> interests;

    @NotNull
    @ManyToMany
    @Getter
    private List<Theme> knowledges;

    @NotNull
    @Getter
    private String name;

    @NotNull
    @Getter
    private String surname;

    @NotNull
    @Getter
    private Date birthDate;

}
