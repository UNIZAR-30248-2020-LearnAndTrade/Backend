/*
 * CheckReservationAvailability.java 1.0 2/12/2020
 */

/*
  This service checks if it is possible to create a reservation

  @author Eduardo Gimeno
  @version 1.0, 2/12/2020
 */

package com.project.LearnAndTrade.Service;

import com.project.LearnAndTrade.Entity.Reservation;
import com.project.LearnAndTrade.Repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CheckReservationAvailability {

    @Autowired
    private ReservationRepository reservationRepository;

    public Boolean check(String teacher, String student, int startTime, int finishTime, Date date) {
        List<Reservation> listReservations = reservationRepository.findByUserAndHours(teacher, student, date, startTime, finishTime);
        for (Reservation r : listReservations) {
            System.out.println(r.getId());
        }
        return listReservations.isEmpty();
    }

}
