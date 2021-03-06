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
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    @LazyCollection(LazyCollectionOption.FALSE)
    @Getter
    private List<Theme> interests;

    @NotNull
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
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

    @NotNull
    @Getter
    private String imageUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
