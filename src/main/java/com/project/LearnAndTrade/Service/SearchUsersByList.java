/*
 * SearchUsersByList.java 1.0 27/12/2020
 */

/*
  This service gets a list of users by a list of themes,
    also depending if filters by interests or knowledges

  @author Gonzalo Berné
  @version 1.0, 27/12/2020
 */

package com.project.LearnAndTrade.Service;

import com.project.LearnAndTrade.Entity.Theme;
import com.project.LearnAndTrade.Entity.User;
import com.project.LearnAndTrade.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SearchUsersByList {

    @Autowired
    private UserRepository userRepository;

    public List<User> search(List<Theme> themeList, boolean interests) {
        if (interests) {
            return userRepository.searchUsersByListOfInterests(themeList);
        } else {
            return userRepository.searchUsersByListOfKnowledges(themeList);
        }
    }
}
