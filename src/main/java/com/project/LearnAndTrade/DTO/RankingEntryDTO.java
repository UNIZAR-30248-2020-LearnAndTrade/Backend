/*
 * RankingDTO.java 1.0 28/12/2020
 */

/*
  This class represents the DTO (Data Transfer Object) of a RankingEntry object

  @author Eduardo Gimeno
  @version 1.0, 28/12/2020
 */

package com.project.LearnAndTrade.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(name = "RankingEntryDTO", type = "object")
@Getter
@AllArgsConstructor
public class RankingEntryDTO {

    @Schema(
            description = "Username of the user",
            example = "jorge",
            required = true
    )
    private String username;

    @Schema(
            description = "Rating of the user",
            example = "3",
            required = true
    )
    private int rating;

}
