/*
 * ParserThemeDTO.java 1.0 15/11/2020
 */

/*
  This class parses the Entity Theme to ThemeDTO (Data Transfer Object) and the contrary

  @author Eduardo Gimeno
  @version 1.0, 15/11/2020
 */

package com.project.LearnAndTrade.Service;

import com.project.LearnAndTrade.DTO.ThemeDTO;
import com.project.LearnAndTrade.Entity.Theme;
import com.project.LearnAndTrade.Repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ParserThemeDTO {

    @Autowired
    private ThemeRepository themeRepository;

    public ThemeDTO themeToThemeDTO(Theme theme) {
        return new ThemeDTO(theme.getName());
    }

    public Optional<Theme> themeDTOToTheme(ThemeDTO themeDTO) {
        Optional<Theme> oldTheme = themeRepository.findByName(themeDTO.getName());
        if (oldTheme.isPresent()) {
            return Optional.of(oldTheme.get());
        } else {
            return Optional.empty();
        }
    }

    public List<ThemeDTO> themeToThemeDTOList(List<Theme> themes) {
        List<ThemeDTO> themesDTO = new ArrayList<>();
        for (Theme theme : themes) {
            themesDTO.add(new ThemeDTO(theme.getName()));
        }
        return themesDTO;
    }

    public Optional<List<Theme>> themeDTOToThemeList(List<ThemeDTO> themesDTO) {
        List<Theme> themes = new ArrayList<>();
        for (ThemeDTO themeDTO : themesDTO) {
            Optional<Theme> oldTheme = themeRepository.findByName(themeDTO.getName());
            if (oldTheme.isPresent()) {
                themes.add(oldTheme.get());
            }
        }
        if (themes.size() > 0) {
            return  Optional.of(themes);
        } else {
            return Optional.empty();
        }
    }
}
