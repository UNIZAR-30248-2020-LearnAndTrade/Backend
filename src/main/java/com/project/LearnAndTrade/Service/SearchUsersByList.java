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

import com.project.LearnAndTrade.DTO.ThemeDTO;
import com.project.LearnAndTrade.Entity.Theme;
import com.project.LearnAndTrade.Entity.User;
import com.project.LearnAndTrade.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchUsersByList {

    @Autowired
    private UserRepository userRepository;

    public List<User> search(List<Theme> themeList, boolean interests) {
        List<User> resultList = new ArrayList<User>();
        int iter = 0;
        if (interests) {
            for (Theme theme : themeList) {
                List<Theme> list = new ArrayList<Theme>();
                list.add(theme);
                if (iter == 0) {
                    resultList = userRepository.searchUsersByListOfInterests(list);
                } else {
                    resultList.retainAll(userRepository.searchUsersByListOfInterests(list));
                }
                iter++;
            }
        } else {
            for (Theme theme : themeList) {
                List<Theme> list = new ArrayList<Theme>();
                list.add(theme);
                if (iter == 0) {
                    resultList = userRepository.searchUsersByListOfKnowledges(list);
                } else {
                    resultList.retainAll(userRepository.searchUsersByListOfKnowledges(list));
                }
                iter++;
            }
        }
        return resultList;
    }
}
