/*
  UserRepository.java 1.0 25/10/2020
 */

/*
  This interface is the repository for User
  used for keep the persistence for the User entity

  @author Gonzalo Berné
  @author Eduardo Gimeno
  @version 2.0, 27/10/2020
 */

package com.project.LearnAndTrade.Repository;

import com.project.LearnAndTrade.Entity.User;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {

    boolean existsByEmailAndPassword(String email, String password);

    boolean existsByUsernameAndPassword(String username, String password);

    boolean existsByUsername(String username);

    User findByUsername(String username);

}
