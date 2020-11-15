/*
  ThemeRepository.java 1.0 15/11/2020
 */

/*
  This interface is the repository for Theme
  used for keep the persistence for the Theme entity

  @author Eduardo Gimeno
  @version 1.0, 15/11/2020
 */

package com.project.LearnAndTrade.Repository;

import com.project.LearnAndTrade.Entity.Theme;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThemeRepository extends CrudRepository<Theme, String> {

    Optional<Theme> findByName(String name);

}
