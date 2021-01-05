package com.project.LearnAndTrade.Controller;

import com.project.LearnAndTrade.DTO.ThemeDTO;
import com.project.LearnAndTrade.Entity.Theme;
import com.project.LearnAndTrade.Service.GetThemes;
import com.project.LearnAndTrade.Service.ParserThemeDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
public class ThemeControllerTests {

    @Autowired
    private final ThemeController themeController;

    @Autowired
    private final GetThemes getThemes;

    @Autowired
    private final ParserThemeDTO parserThemeDTO;

    public ThemeControllerTests() {
        themeController = new ThemeController();
        this.getThemes = new GetThemes();
        this.parserThemeDTO = new ParserThemeDTO();
    }

    @Test
    @Order(1)
    public void canGetThemes() {
        ResponseEntity<List<ThemeDTO>> responseEntity = themeController.getThemes();
        Assertions.assertEquals(200, responseEntity.getStatusCode().value());
        List<ThemeDTO> result = responseEntity.getBody();
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.size() > 0);
    }

    @Test
    @Order(2)
    public void canConvertThemeDTO() {
        Optional<List<Theme>> list = getThemes.getAllThemes();
        if (list.isPresent()) {
            List<ThemeDTO> listDTO = parserThemeDTO.themeToThemeDTOList(list.get());
            assertEquals(listDTO.size(), list.get().size());

            List<Theme> list2 = parserThemeDTO.themeDTOToThemeList(listDTO);
            assertEquals(list2.size(), list.get().size());

            ThemeDTO themeDTO = parserThemeDTO.themeToThemeDTO(list.get().get(0));
            assertEquals(list.get().get(0).getName(), themeDTO.getName());

            Optional<Theme> theme = parserThemeDTO.themeDTOToTheme(themeDTO);
            assertTrue(theme.isPresent());
            assertEquals(list.get().get(0).getName(), theme.get().getName());
        }
        System.out.println("2. 'canConvertThemeDTO' test passed");
    }
}
