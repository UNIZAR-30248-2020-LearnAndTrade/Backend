/*
 * ParserRankingEntryDTO.java 1.0 28/12/2020
 */

/*
  This class parses the object RankingEntry to RankingEntryDTO (Data Transfer Object)

  @author Eduardo Gimeno
  @version 1.0, 28/12/2020
 */

package com.project.LearnAndTrade.Service;

import com.project.LearnAndTrade.AuxiliaryObject.RankingEntry;
import com.project.LearnAndTrade.DTO.RankingEntryDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParserRankingEntryDTO {

    public List<RankingEntryDTO> rankingEntryToRankingEntryDTOList(List<RankingEntry> entries) {
        List<RankingEntryDTO> ranking = new ArrayList<>();
        for (RankingEntry rankingEntry : entries) {
            ranking.add(new RankingEntryDTO(rankingEntry.getUsername(), rankingEntry.getRating()));
        }
        return ranking;
    }

}
