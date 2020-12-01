/*
 * ParserReservationDTO.java 1.0 30/11/2020
 */

/*
  This class parses the Entity Reservation to ReservationDTO (Data Transfer Object) and the contrary

  @author Eduardo Gimeno
  @version 1.0, 15/11/2020
 */

package com.project.LearnAndTrade.Service;

import com.project.LearnAndTrade.DTO.ReservationDTO;
import com.project.LearnAndTrade.Entity.Reservation;
import com.project.LearnAndTrade.Entity.Theme;
import com.project.LearnAndTrade.Entity.User;
import com.project.LearnAndTrade.Repository.ReservationRepository;
import com.project.LearnAndTrade.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ParserReservationDTO {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParserThemeDTO parserThemeDTO;

    public ReservationDTO reservationToReservationDTO(Reservation reservation) {
        return new ReservationDTO(reservation.getId(), reservation.getStartTime(), reservation.getFinishTime(),
                reservation.getDate(), parserThemeDTO.themeToThemeDTO(reservation.getTheme()),
                reservation.getTeacherUsername(), reservation.getStudentUsername());
    }

    public Optional<Reservation> reservationDTOToReservation(ReservationDTO reservationDTO) {
        Optional<Reservation> oldReservation = reservationRepository.findById(reservationDTO.getId());
        if (oldReservation.isPresent()) {
            Optional<Theme> theme = parserThemeDTO.themeDTOToTheme(reservationDTO.getTheme());
            return Optional.of(new Reservation(reservationDTO.getId(), reservationDTO.getStartTime(),
                    reservationDTO.getFinishTime(), reservationDTO.getDate(), theme.get(),
                    reservationDTO.getTeacherUsername(), reservationDTO.getStudentUsername()));
        } else {
            return Optional.empty();
        }
    }

    public Optional<Reservation> reservationDTOtoReservationAdd(ReservationDTO reservationDTO) {
        Optional<Theme> theme = parserThemeDTO.themeDTOToTheme(reservationDTO.getTheme());
        Optional<User> teacher = userRepository.findByUsername(reservationDTO.getTeacherUsername());
        Optional<User> student = userRepository.findByUsername(reservationDTO.getStudentUsername());
        if (theme.isPresent() && teacher.isPresent() && student.isPresent()) {
            return Optional.of(new Reservation(reservationDTO.getId(), reservationDTO.getStartTime(),
                    reservationDTO.getFinishTime(), reservationDTO.getDate(), theme.get(),
                    reservationDTO.getTeacherUsername(), reservationDTO.getStudentUsername()));
        } else {
            return Optional.empty();
        }
    }

    public List<ReservationDTO> reservationToReservationDTOList(List<Reservation> reservations) {
        List<ReservationDTO> reservationsDTO = new ArrayList<>();
        for (Reservation reservation : reservations) {
            reservationsDTO.add(new ReservationDTO(reservation.getId(), reservation.getStartTime(),
                    reservation.getFinishTime(), reservation.getDate(),
                    parserThemeDTO.themeToThemeDTO(reservation.getTheme()), reservation.getTeacherUsername(),
                    reservation.getStudentUsername()));
        }
        return reservationsDTO;
    }
}
