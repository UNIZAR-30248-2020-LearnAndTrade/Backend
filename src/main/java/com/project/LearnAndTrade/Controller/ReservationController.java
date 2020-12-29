/*
 * ReservationController.java 1.0 30/11/2020
 */

/*
  This controller acts for the client petitions about reservations

  @author Eduardo Gimeno
  @version 1.0, 30/11/2020
*/

package com.project.LearnAndTrade.Controller;

import com.project.LearnAndTrade.AuxiliaryObject.RankingEntry;
import com.project.LearnAndTrade.DTO.RankingEntryDTO;
import com.project.LearnAndTrade.DTO.ReservationDTO;
import com.project.LearnAndTrade.Entity.Reservation;
import com.project.LearnAndTrade.Entity.Theme;
import com.project.LearnAndTrade.Service.*;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@Api(tags = "Reservation")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping(path = "/reservation")
public class ReservationController {

    @Autowired
    private CreateReservation createReservation;

    @Autowired
    private GetReservationData getReservationData;

    @Autowired
    private GetAllUserReservations getAllUserReservations;

    @Autowired
    private UpdateReservationData updateReservationData;

    @Autowired
    private CheckReservationAvailability checkReservationAvailability;

    @Autowired
    private GetThemeRanking getThemeRanking;

    @Autowired
    private ParserReservationDTO parserReservationDTO;

    @Autowired
    private ParserRankingEntryDTO parserRankingEntryDTO;

    @Operation(
            summary = "Creates a new reservation",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reservation successfully saved"),
                    @ApiResponse(responseCode = "409", description = "Reservation not created because user's schedule conflict"),
                    @ApiResponse(responseCode = "400", description = "Theme and/or usernames in the body do not exits")
            })
    @PostMapping(path = "/create", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationDTO> createReservation(
            @Parameter(description = "ReservationDTO object wanted to be saved", required = true) @RequestBody ReservationDTO reservationDTO) {
        Optional<Reservation> reservation = parserReservationDTO.reservationDTOtoReservationAdd(reservationDTO);
        if (reservation.isPresent()) {
            Boolean isPossible = checkReservationAvailability.check(reservation.get().getTeacherUsername(),
                    reservation.get().getStudentUsername(), reservation.get().getStartTime(), reservation.get().getFinishTime(),
                    reservation.get().getDate());
            if (isPossible) {
                Reservation newReservation = createReservation.create(reservation.get());
                return ResponseEntity.status(HttpStatus.OK).body(parserReservationDTO.reservationToReservationDTO(newReservation));
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(
            summary = "Get a reservation by its id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reservation successfully obtained"),
                    @ApiResponse(responseCode = "404", description = "Error getting reservation")
            })
    @GetMapping(path = "/get", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationDTO> getReservation(
            @Parameter(description = "Id of the reservation", required = true) String id) {
        Optional<Reservation> reservation = getReservationData.getReservation(id);
        if (reservation.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(parserReservationDTO.reservationToReservationDTO(reservation.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(
            summary = "Get all reservations of a user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reservations successfully obtained"),
                    @ApiResponse(responseCode = "404", description = "Error getting reservations")
            })
    @GetMapping(path = "/getAll", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReservationDTO>> getUserReservations(
            @Parameter(description = "Username of the user we want to get the reservations", required = true) String username) {
        List<Reservation> reservations = getAllUserReservations.getAll(username);
        if (reservations.size() > 0) {
            List<ReservationDTO> sendReservations = parserReservationDTO.reservationToReservationDTOList(reservations);
            return ResponseEntity.status(HttpStatus.OK).body(sendReservations);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(
            summary = "Update reservation",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful reservation update"),
                    @ApiResponse(responseCode = "404", description = "Error updating reservation"),
                    @ApiResponse(responseCode = "500", description = "Bad argument passed"),
            })
    @PostMapping(path = "/updatereservation", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationDTO> updateReservation(
            @Parameter(description = "ReservationDTO object wanted to be updated", required = true) @RequestBody ReservationDTO reservationDTO) {
        try {
            Optional<Reservation> reservationOptional = parserReservationDTO.reservationDTOToReservation(reservationDTO);
            if (reservationOptional.isPresent()) {
                Reservation updatedReservation = updateReservationData.updateReservation(reservationOptional.get());
                return ResponseEntity.status(HttpStatus.OK).body(parserReservationDTO.reservationToReservationDTO(updatedReservation));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (IllegalArgumentException | NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
            summary = "Get users rating ranking of a theme",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ranking created"),
                    @ApiResponse(responseCode = "404", description = "Unable to get ranking"),
            })
    @GetMapping(path = "/getthemeranking", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RankingEntryDTO>> getThemeRanking(
            @Parameter(description = "Theme wanted to get the ranking", required = true) String themeName) {
        List<RankingEntry> ranking = getThemeRanking.getRanking(themeName);
        if (ranking.size() > 0) {
            List<RankingEntryDTO> rankingDTO = parserRankingEntryDTO.rankingEntryToRankingEntryDTOList(ranking);
            return ResponseEntity.status(HttpStatus.OK).body(rankingDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
