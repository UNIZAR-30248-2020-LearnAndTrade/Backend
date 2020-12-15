/*
 * Rating.java 1.0 15/12/2020
 */

/*
  This class represents the entity Rating

  @author Eduardo Gimeno
  @version 1.0, 15/12/2020
 */

package com.project.LearnAndTrade.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Rating_T")
@AllArgsConstructor
@NoArgsConstructor
public class Rating {

    @Id
    @NotNull
    @Getter
    private String id;

    @NotNull
    @Getter
    private String username;

    @NotNull
    @OneToOne
    @Getter
    private Theme theme;

    @NotNull
    @Getter
    private int total;

    @NotNull
    @Getter
    private int value;

}
