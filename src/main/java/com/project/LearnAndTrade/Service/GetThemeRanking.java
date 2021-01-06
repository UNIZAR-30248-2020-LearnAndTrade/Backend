/*
 * GetThemeRanking.java 1.0 29/12/2020
 */

/*
  This service gets the rating ranking of a theme

  @author Eduardo Gimeno
  @version 1.0, 29/12/2020
 */

package com.project.LearnAndTrade.Service;

import com.project.LearnAndTrade.AuxiliaryObject.RankingEntry;
import com.project.LearnAndTrade.Entity.Reservation;
import com.project.LearnAndTrade.Entity.Theme;
import com.project.LearnAndTrade.Entity.User;
import com.project.LearnAndTrade.Repository.ReservationRepository;
import com.project.LearnAndTrade.Repository.ThemeRepository;
import com.project.LearnAndTrade.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class GetThemeRanking {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ThemeRepository themeRepository;

    public List<RankingEntry> getRanking(String themeName) {
        Optional<Theme> themeOptional = themeRepository.findByName(themeName);
        if (themeOptional.isPresent()) {
            List<User> users = new ArrayList<>();
            Iterable<User> userIterable = userRepository.findAll();
            userIterable.forEach(users::add);

            List<Reservation> reservations;
            int total = 0;
            int average = 0;
            int reservationsRated = 0;
            List<RankingEntry> ranking = new ArrayList<>();
            for (User user : users) {
                reservations = reservationRepository.findByTeacherUsernameOrTheme(user.getUsername(), themeOptional.get());
                for (Reservation reservation : reservations) {
                    if (reservation.getStudentFinished() && reservation.getTeacherFinished()) {
                        total = total + reservation.getRating();
                        reservationsRated++;
                    }
                }
                if (reservationsRated != 0) {
                    average = total / reservationsRated;
                }
                RankingEntry entry = new RankingEntry(user.getUsername(), average);
                ranking.add(entry);
            }

            Collections.sort(ranking);
            Collections.reverse(ranking);
            return ranking;
        } else {
            return new ArrayList<>();
        }
    }

}
