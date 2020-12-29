package com.project.LearnAndTrade.Controller;

import com.project.LearnAndTrade.Entity.Theme;
import com.project.LearnAndTrade.Service.GetThemes;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
public class ThemeControllerTests {

    @Autowired
    private final GetThemes getThemes;

    public ThemeControllerTests() {
        getThemes = new GetThemes();
    }

    @Test
    @Order(1)
    public void canGetThemes() {
        Optional<List<Theme>> result = getThemes.getAllThemes();
        assertNotNull(result);
        assertTrue(result.isPresent());
        List<Theme> themes = result.get();
        assertTrue(themes.size() > 0);
    }
}
