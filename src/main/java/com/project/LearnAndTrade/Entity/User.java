/*
 * User.java 1.0 25/10/2020
 */

/*
  This class represents the entity User

  @author Gonzalo Berné
 * @version 1.0, 25/10/2020
 */

package com.project.LearnAndTrade.Entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "User_T")
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

}
