/*
 * GetThemes.java 1.0 15/11/2020
 */

/*
  This service gets the information of the themes

  @author Eduardo Gimeno
  @version 1.0, 15/11/2020
 */

package com.project.LearnAndTrade.Service;

import com.project.LearnAndTrade.Entity.Theme;
import com.project.LearnAndTrade.Repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GetThemes {

    @Autowired
    private ThemeRepository themeRepository;

    public Optional<List<Theme>> getAllThemes() {
        Iterable<Theme> it = themeRepository.findAll();
        List<Theme> themes = new ArrayList<>();
        it.forEach(themes::add);
        if (themes.size() > 0) {
            return Optional.of(themes);
        } else {
            return Optional.empty();
        }
    }

}
