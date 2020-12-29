/*
 * CreateReservation.java 1.0 30/11/2020
 */

/*
  This service saves the information of a reservation

  @author Eduardo Gimeno
  @version 1.0, 30/11/2020
 */

package com.project.LearnAndTrade.Service;

import com.project.LearnAndTrade.Entity.Reservation;
import com.project.LearnAndTrade.Repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteReservation {

    @Autowired
    private ReservationRepository reservationRepository;

    public void delete(Reservation reservation) {
        reservationRepository.delete(reservation);
    }

}
