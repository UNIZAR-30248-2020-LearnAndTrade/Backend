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
        List<Reservation> teacherReservationsAsTeacher = reservationRepository.findByTeacherUsernameAndStartTimeAndFinishTimeAndDate(teacher,
                startTime, finishTime, date);
        List<Reservation> studentReservationsAsStudent = reservationRepository.findByStudentUsernameAndStartTimeAndFinishTimeAndDate(student,
                startTime, finishTime, date);
        List<Reservation> teacherReservationsAsStudent = reservationRepository.findByStudentUsernameAndStartTimeAndFinishTimeAndDate(teacher,
                startTime, finishTime, date);
        List<Reservation> studentReservationsAsTeacher = reservationRepository.findByTeacherUsernameAndStartTimeAndFinishTimeAndDate(student,
                startTime, finishTime, date);
        return teacherReservationsAsTeacher.isEmpty() && studentReservationsAsStudent.isEmpty() &&
                teacherReservationsAsStudent.isEmpty() && studentReservationsAsTeacher.isEmpty();
    }

}
