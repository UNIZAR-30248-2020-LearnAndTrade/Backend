/*
 * UserController.java 1.0 26/10/2020
 */

/*
  This controller acts for the client petitions about users

  @author Gonzalo Berné
  @author Eduardo Gimeno
  @author Jorge Turbica
  @version 4.0, 30/10/2020
 */

package com.project.LearnAndTrade.Controller;

import com.project.LearnAndTrade.DTO.UserDTO;
import com.project.LearnAndTrade.Entity.User;
import com.project.LearnAndTrade.Service.*;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@Api(tags = "User")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private LogInUser logInUser;

    @Autowired
    private GetUserData getUserData;

    @Autowired
    private UpdateUserData updateUserData;

    @Autowired
    private SearchComplementaryUsers searchComplementaryUsers;

    @Autowired
    private ParserUserDTO parserUserDTO;

    @Operation(
            summary = "Perform login action for registered users",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful login"),
                    @ApiResponse(responseCode = "404", description = "Error login"),
            })
    @GetMapping(path = "/login", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> logIn(
            @Parameter(description = "The user's username", required = true) String username,
            @Parameter(description = "The user's password", required = true) String password
    ) {
        Optional<User> userOptional = logInUser.logIn(username, password);
        if (userOptional.isPresent()) {
            //return ResponseEntity.status(HttpStatus.OK).body(parserUserDTO.userToUserDTO(userOptional.get()));
            return ResponseEntity.ok().body(parserUserDTO.userToUserDTO(userOptional.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Get user by it's username",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully gotten user"),
                    @ApiResponse(responseCode = "404", description = "Error login"),
            })
    @GetMapping(path = "/getuser", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getUser(
            @Parameter(description = "The user's username", required = true) String username
    ) {
        Optional<User> userOptional = getUserData.getUser(username);
        if (userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(parserUserDTO.userToUserDTO(userOptional.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(
            summary = "Update user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful user update"),
                    @ApiResponse(responseCode = "201", description = "Successful user created"),
                    @ApiResponse(responseCode = "404", description = "Error login"),
                    @ApiResponse(responseCode = "500", description = "Bad argument passed"),
            })
    @PostMapping(path = "/updateuser", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> updateUser(
            @Parameter(description = "UserDTO object wanted to be saved", required = true) @RequestBody UserDTO userDTO) {
        try {
            Optional<User> userOptional = parserUserDTO.userDTOToUser(userDTO);
            if (userOptional.isPresent()) {
                User updatedUser = updateUserData.updateUser(userOptional.get());
                return ResponseEntity.status(HttpStatus.OK).body(parserUserDTO.userToUserDTO(updatedUser));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
            summary = "Search complementary users from the user passed",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful complementary users search"),
                    @ApiResponse(responseCode = "404", description = "Error searching complementary users"),
            })
    @GetMapping(path = "/getcomplementaryusers", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> searchComplementaryUsers(
            @Parameter(description = "The user's username", required = true) String username
    ) {
        Optional<User> userOptional = getUserData.getUser(username);
        if (userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(searchComplementaryUsers.searchUsers(userOptional.get())
                    .stream()
                    .map(parserUserDTO::userToUserDTO)
                    .collect(Collectors.toList()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
