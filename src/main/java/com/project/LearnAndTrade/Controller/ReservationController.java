/*
 * ReservationController.java 1.0 30/11/2020
 */

/*
  This controller acts for the client petitions about reservations

  @author Eduardo Gimeno
  @version 1.0, 30/11/2020
*/

package com.project.LearnAndTrade.Controller;

import com.project.LearnAndTrade.DTO.ReservationDTO;
import com.project.LearnAndTrade.Entity.Reservation;
import com.project.LearnAndTrade.Service.CreateReservation;
import com.project.LearnAndTrade.Service.GetAllUserReservations;
import com.project.LearnAndTrade.Service.GetReservationData;
import com.project.LearnAndTrade.Service.ParserReservationDTO;
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
    private ParserReservationDTO parserReservationDTO;

    @Operation(
            summary = "Creates a new reservation",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reservation successfully saved"),
                    @ApiResponse(responseCode = "500", description = "Reservation not successfully saved"),
                    @ApiResponse(responseCode = "400", description = "Theme and/or usernames in the body do not exits")
            })
    @PostMapping(path = "/create", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationDTO> createReservation(
            @Parameter(description = "ReservationDTO object wanted to be saved", required = true) @RequestBody ReservationDTO reservationDTO) {
        Optional<Reservation> reservation = parserReservationDTO.reservationDTOtoReservationAdd(reservationDTO);
        if (reservation.isPresent()) {
            Reservation newReservation = createReservation.create(reservation.get());
            if (reservation.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(parserReservationDTO.reservationToReservationDTO(newReservation));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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

}
