/*
 * RankingEntry.java 1.0 28/12/2020
 */

/*
  This class represents the object RankingEntry

  @author Eduardo Gimeno
  @version 1.0, 28/12/2020
 */

package com.project.LearnAndTrade.AuxiliaryObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class RankingEntry implements Comparable<RankingEntry> {

    @Getter
    private String username;

    @Getter
    private int rating;

    @Override
    public int compareTo(RankingEntry entry) {
        Integer rating = getRating();
        Integer entryRating = entry.getRating();
        return rating.compareTo(entryRating);
    }

}
