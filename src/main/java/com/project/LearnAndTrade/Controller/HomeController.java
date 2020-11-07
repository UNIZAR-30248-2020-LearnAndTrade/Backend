/*
 * HomeController.java 1.0 26/10/2020
 */

/*
  This controller acts for the client petitions about home page

  @author Jorge Turbica
  @version 1.0, 26/10/2020
 */

package com.project.LearnAndTrade.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@CrossOrigin(origins = "*", methods = {RequestMethod.GET})
@RequestMapping(path = "/")
public class HomeController {

    @GetMapping(path = "/")
    public ResponseEntity<Object> home() {
        return ResponseEntity.status(HttpStatus.OK).body("Bienvenido!! :)");
    }

}
