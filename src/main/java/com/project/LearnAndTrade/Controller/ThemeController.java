/*
 * ThemeController.java 1.0 15/11/2020
 */

/*
  This controller acts for the client petitions about themes

  @author Eduardo Gimeno
  @version 1.0, 15/11/2020
 */

package com.project.LearnAndTrade.Controller;

import com.project.LearnAndTrade.DTO.ThemeDTO;
import com.project.LearnAndTrade.Entity.Theme;
import com.project.LearnAndTrade.Service.GetThemes;
import com.project.LearnAndTrade.Service.ParserThemeDTO;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@Api(tags = "Theme")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping(path = "/theme")
public class ThemeController {

    @Autowired
    private GetThemes getThemes;

    @Autowired
    private ParserThemeDTO parserThemeDTO;

    @Operation(
            summary = "Get all themes",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully gotten themes"),
                    @ApiResponse(responseCode = "404", description = "Error getting themes"),
            })
    @GetMapping(path = "/getthemes", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ThemeDTO>> getThemes() {
        Optional<List<Theme>> themes = getThemes.getAllThemes();
        return themes.map(themeList -> ResponseEntity.status(HttpStatus.OK).body(parserThemeDTO.themeToThemeDTOList(themeList))).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}
