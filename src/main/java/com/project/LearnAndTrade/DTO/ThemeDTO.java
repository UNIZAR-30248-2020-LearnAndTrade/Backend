/*
 * ThemeDTO.java 1.0 15/11/2020
 */

/*
  This class represents the DTO (Data Transfer Object) of the entity Theme

  @author Eduardo Gimeno
  @version 1.0, 15/11/2020
 */

package com.project.LearnAndTrade.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(name = "ThemeDTO", type = "object")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ThemeDTO {

    @Schema(
            description = "Name of the theme",
            example = "Angular",
            required = true
    )
    private String name;

}
