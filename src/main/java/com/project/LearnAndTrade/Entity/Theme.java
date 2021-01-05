/*
 * Theme.java 1.0 15/11/2020
 */

/*
  This class represents the entity Theme

  @author Eduardo Gimeno
  @version 1.0, 15/11/2020
 */

package com.project.LearnAndTrade.Entity;

import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "Theme_T")
@Data
public class Theme {

    @Id
    @NotNull
    @Getter
    private String name;

    @ManyToMany(mappedBy = "interests")
    @LazyCollection(LazyCollectionOption.FALSE)
    @Getter
    private List<User> interests_users;

    @ManyToMany(mappedBy = "knowledges")
    @LazyCollection(LazyCollectionOption.FALSE)
    @Getter
    private List<User> knowledges_users;

}
