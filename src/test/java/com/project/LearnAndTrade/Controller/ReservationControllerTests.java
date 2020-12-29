package com.project.LearnAndTrade.Controller;

import com.project.LearnAndTrade.DTO.RankingEntryDTO;
import com.project.LearnAndTrade.DTO.ReservationDTO;
import com.project.LearnAndTrade.DTO.ThemeDTO;
import com.project.LearnAndTrade.Entity.Reservation;
import com.project.LearnAndTrade.Service.DeleteReservation;
import com.project.LearnAndTrade.Service.ParserReservationDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReservationControllerTests {

    @Autowired
    private final ReservationController reservationController;

    @Autowired
    private final DeleteReservation deleteReservation;

    @Autowired
    private final ParserReservationDTO parserReservationDTO;

    private ReservationDTO reservationDTO;

    private String reservationId = "";

    public ReservationControllerTests() {
        this.reservationController = new ReservationController();
        this.deleteReservation = new DeleteReservation();
        this.parserReservationDTO = new ParserReservationDTO();
        reservationDTO = new ReservationDTO(
                "",
                12,
                14,
                Date.from(Instant.now()),
                new ThemeDTO("Historia"),
                "testUser1",
                "testUser2",
                false,
                false,
                3
        );
    }

    @BeforeAll
    public void before() {
        System.out.println("Starting 'ReservationController' testing...");
    }

    @AfterAll
    public void after() {
        reservationDTO.setId(reservationId);
        reservationDTO.setTheme(new ThemeDTO("Historia"));
        Optional<Reservation> reservationOptional = parserReservationDTO.reservationDTOToReservation(reservationDTO);
        reservationOptional.ifPresent(deleteReservation::delete);
        System.out.println("Finished 'ReservationController' testing...");
    }

    @Test
    @Order(1)
    public void canCreateReservation() {
        ResponseEntity<ReservationDTO> response = reservationController.createReservation(reservationDTO);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(response.getBody());
        reservationId = response.getBody().getId();
        reservationDTO = response.getBody();
    }

    @Test
    @Order(2)
    public void cantCreateReservationConflict() {
        ResponseEntity<ReservationDTO> response = reservationController.createReservation(reservationDTO);
        Assertions.assertEquals(409, response.getStatusCode().value());
    }

    @Test
    @Order(3)
    public void cantCreateReservationUnknown() {
        reservationDTO.setTheme(new ThemeDTO("Testing"));
        ResponseEntity<ReservationDTO> response = reservationController.createReservation(reservationDTO);
        Assertions.assertEquals(400, response.getStatusCode().value());
    }

    @Test
    @Order(4)
    public void canGetReservation() {
        ResponseEntity<ReservationDTO> response = reservationController.getReservation(reservationId);
        Assertions.assertEquals(200, response.getStatusCode().value());
    }

    @Test
    @Order(5)
    public void cantGetReservation() {
        ResponseEntity<ReservationDTO> response = reservationController.getReservation(reservationDTO.getId() + "1");
        Assertions.assertEquals(404, response.getStatusCode().value());
    }

    @Test
    @Order(6)
    public void canGetUserReservation() {
        ResponseEntity<List<ReservationDTO>> response = reservationController.getUserReservations(reservationDTO.getStudentUsername());
        Assertions.assertEquals(200, response.getStatusCode().value());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertFalse(response.getBody().isEmpty());
    }

    @Test
    @Order(7)
    public void cantGetUserReservation() {
        ResponseEntity<List<ReservationDTO>> response = reservationController.getUserReservations(reservationDTO.getStudentUsername() + "1");
        Assertions.assertEquals(404, response.getStatusCode().value());
    }

    @Test
    @Order(8)
    public void canUpdateReservation() {
        reservationDTO.setId(reservationId);
        reservationDTO.setStartTime(13);
        reservationDTO.setFinishTime(15);
        reservationDTO.setTheme(new ThemeDTO("Historia"));
        ResponseEntity<ReservationDTO> response = reservationController.updateReservation(reservationDTO);
        Assertions.assertEquals(200, response.getStatusCode().value());
    }

    @Test
    @Order(9)
    public void cantUpdateReservation() {
        reservationDTO.setId("");
        ResponseEntity<ReservationDTO> response = reservationController.updateReservation(reservationDTO);
        Assertions.assertEquals(404, response.getStatusCode().value());
    }

    @Test
    @Order(10)
    public void cantUpdateReservationArguments() {
        reservationDTO.setId(reservationId);
        reservationDTO.setTheme(new ThemeDTO("Testing"));
        ResponseEntity<ReservationDTO> response = reservationController.updateReservation(reservationDTO);
        Assertions.assertEquals(500, response.getStatusCode().value());
    }

    @Test
    @Order(11)
    public void canGetThemeRanking() {
        ResponseEntity<List<RankingEntryDTO>> response = reservationController.getThemeRanking("Historia");
        Assertions.assertEquals(200, response.getStatusCode().value());
    }

    @Test
    @Order(12)
    public void cantGetThemeRanking() {
        ResponseEntity<List<RankingEntryDTO>> response = reservationController.getThemeRanking("Testing");
        Assertions.assertEquals(404, response.getStatusCode().value());
    }
}
