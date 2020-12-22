/*
 * UpdateReservationData.java 1.0 22/12/2020
 */

/*
  This service updates the information of a reservation

  @author Eduardo Gimeno
  @version 1.0, 22/12/2020
 */

package com.project.LearnAndTrade.Service;

import com.project.LearnAndTrade.Entity.Reservation;
import com.project.LearnAndTrade.Repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateReservationData {

    @Autowired
    private ReservationRepository reservationRepository;

    public Reservation updateReservation(Reservation reservation) throws IllegalArgumentException {
        return reservationRepository.save(reservation);
    }

}
