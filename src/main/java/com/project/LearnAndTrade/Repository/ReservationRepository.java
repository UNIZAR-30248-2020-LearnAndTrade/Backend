/*
  ReservationRepository.java 1.0 30/11/2020
 */

/*
  This interface is the repository for Reservation
  used for keep the persistence for the Reservation entity

  @author Eduardo Gimeno
  @version 1.0, 30/11/2020
 */

package com.project.LearnAndTrade.Repository;

import com.project.LearnAndTrade.Entity.Reservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, String> {

    List<Reservation> findByTeacherUsernameOrStudentUsername(String teacherUsername, String studentUsername);

    /*List<Reservation> findByTeacherUsernameAndDateAndStartTimeGreaterThanEqualOrFinishTimeLessThanEqualOrStartTimeLessThanEqualAndFinishTimeGreaterThanEqual(String teacherUsername,
                                                                                                                                                             Date date, int startTime, int finishTime);

    List<Reservation> findByStudentUsernameAndDateAndStartTimeGreaterThanEqualOrFinishTimeLessThanEqualOrStartTimeLessThanEqualAndFinishTimeGreaterThanEqual(String studentUsername,
                                                                                                                                                             Date date, int startTime, int finishTime);

*/
}