/*
  UserRepository.java 1.0 25/10/2020
 */

/*
  This interface is the repository for User
  used for keep the persistence for the User entity

  @author Gonzalo Berné
  @author Eduardo Gimeno
  @version 3.0, 30/10/2020
 */

package com.project.LearnAndTrade.Repository;

import com.project.LearnAndTrade.Entity.Theme;
import com.project.LearnAndTrade.Entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByUsernameAndPassword(String username, String password);

    Optional<User> findByUsername(String username);

    // This query finds complementary users through one list of interests and other of knowledges
    @Query(value = "SELECT DISTINCT u FROM User u JOIN u.interests i JOIN u.knowledges k WHERE k IN ?1 AND i IN ?2")
    List<User> findComplementaryUsers(List<Theme> interests, List<Theme> knowledges);

    // This query searches users through one list of interests
    @Query(value = "SELECT DISTINCT u FROM User u JOIN u.interests i WHERE i IN ?1")
    List<User> searchUsersByListOfInterests(List<Theme> interests);

    // This query searches users through one list of knowledges
    @Query(value = "SELECT DISTINCT u FROM User u JOIN u.knowledges k WHERE k IN ?1")
    List<User> searchUsersByListOfKnowledges(List<Theme> knowledges);

}
