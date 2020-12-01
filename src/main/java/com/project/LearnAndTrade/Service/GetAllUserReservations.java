/*
 * GetAllUserReservations.java 1.0 01/12/2020
 */

/*
  This service gets all the reservations of a user

  @author Eduardo Gimeno
  @version 1.0, 01/12/2020
 */

package com.project.LearnAndTrade.Service;

import com.project.LearnAndTrade.Entity.Reservation;
import com.project.LearnAndTrade.Repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllUserReservations {

    @Autowired
    private ReservationRepository reservationRepository;

    public List<Reservation> getAll(String username) {
        return reservationRepository.findByTeacherUsernameAndStudentUsername(username, username);
    }

}
