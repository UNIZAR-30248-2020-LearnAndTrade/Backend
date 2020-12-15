/*
 * Rservation.java 1.0 28/11/2020
 */

/*
  This class represents the entity Reservation

  @author Eduardo Gimeno
  @version 1.0, 28/11/2020
 */

package com.project.LearnAndTrade.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "Reservation_T")
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    @NotNull
    @Getter
    private String id;

    @NotNull
    @Getter
    @Min(0)
    @Max(23)
    private int startTime;

    @NotNull
    @Getter
    @Min(0)
    @Max(23)
    private int finishTime;

    @NotNull
    @Getter
    private Date date;

    @NotNull
    @OneToOne
    @Getter
    private Theme theme;

    @NotNull
    @Getter
    private String teacherUsername;

    @NotNull
    @Getter
    private String studentUsername;

    @NotNull
    @Getter
    private Boolean finished;

}
