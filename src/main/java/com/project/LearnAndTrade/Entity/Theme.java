/*
 * Theme.java 1.0 15/11/2020
 */

/*
  This class represents the entity Theme

  @author Eduardo Gimeno
  @version 1.0, 15/11/2020
 */

package com.project.LearnAndTrade.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "Theme_T")
@AllArgsConstructor
@NoArgsConstructor
public class Theme {

    @Id
    @NotNull
    @Getter
    private String name;

    @ManyToMany(mappedBy = "interests")
    @Getter
    private List<User> interests_users;

    @ManyToMany(mappedBy = "knowledges")
    @Getter
    private List<User> knowledges_users;

}
