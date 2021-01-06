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
import com.project.LearnAndTrade.Entity.Theme;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, String> {

    List<Reservation> findByTeacherUsernameOrStudentUsername(String teacherUsername, String studentUsername);

    @Query(value = "SELECT r " +
            " FROM Reservation r " +
            " WHERE (r.studentUsername = ?1 OR r.teacherUsername = ?1 " +
                " OR r.studentUsername = ?2 OR r.teacherUsername = ?2) " +
            " AND r.date = ?3 " +
            " AND ((r.startTime <= ?4 AND r.finishTime > ?4) " +
                " OR (r.startTime < ?5 AND r.finishTime >= ?5) " +
                " OR (r.startTime >= ?4 AND r.finishTime <= ?5))")
    List<Reservation> findByUserAndHours(String usernameTeacher, String usernameStuden, Date date, int startTime, int finishTime);

    List<Reservation> findByTeacherUsernameOrTheme(String teacherUsername, Theme theme);
}
