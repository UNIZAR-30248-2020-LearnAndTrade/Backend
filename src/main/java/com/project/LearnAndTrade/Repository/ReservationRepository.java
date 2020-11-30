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

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, String> { }
