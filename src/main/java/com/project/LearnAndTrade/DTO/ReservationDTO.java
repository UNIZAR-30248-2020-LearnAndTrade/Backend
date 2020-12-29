/*
 * ReservationDTO.java 1.0 30/11/2020
 */

/*
  This class represents the DTO (Data Transfer Object) of the entity Reservation

  @author Eduardo Gimeno
  @version 1.0, 30/11/2020
 */

package com.project.LearnAndTrade.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Schema(name = "ReservationDTO", type = "object")
@Getter
@AllArgsConstructor
public class ReservationDTO {

    @Schema(
            description = "Id of the reservation",
            example = "exampleID",
            required = true
    )
    private String id;

    @Schema(
            description = "Start time of the reservation",
            example = "12",
            required = true
    )
    private int startTime;

    @Schema(
            description = "Finish time of the reservation",
            example = "20",
            required = true
    )
    private int finishTime;

    @Schema(
            description = "Date of the reservation",
            example = "2020-12-12",
            required = true
    )
    private Date date;

    @Schema(
            description = "Theme of the lesson",
            example = "\"Baile\"",
            required = true
    )
    private ThemeDTO theme;

    @Schema(
            description = "Username of the user who teaches the lesson",
            example = "Gonzalo",
            required = true
    )
    private String teacherUsername;

    @Schema(
            description = "Username of the user who receives the lesson",
            example = "Fernando",
            required = true
    )
    private String studentUsername;

    @Schema(
            description = "Teacher's confirmation of the reservation's ending",
            example = "true",
            required = true
    )
    private Boolean teacherFinished;

    @Schema(
            description = "Student's confirmation of the reservation's ending",
            example = "false",
            required = true
    )
    private Boolean studentFinished;

    @Schema(
            description = "Valoration of the reservation from 0 to 5",
            example = "3",
            required = true
    )
    private int rating;

}
