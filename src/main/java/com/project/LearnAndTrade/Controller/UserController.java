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
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@Api(tags = "User")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET})
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
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful login",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = UserDTO.class)
                                    )
                            }
                    ),
                    @ApiResponse(responseCode = "404", description = "Error login"),
            })
    @GetMapping(path = "/login")
    public ResponseEntity<UserDTO> logIn(
            @Parameter(description = "The user's username", required = true) String username,
            @Parameter(description = "The user's password", required = true) String password
    ) {
        Optional<User> userOptional = logInUser.logIn(username, password);
        if (userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(parserUserDTO.userToUserDTO(userOptional.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Get user by it's username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully gotten user"),
            @ApiResponse(responseCode = "404", description = "Error login"),
    })
    @GetMapping(path = "/getuser")
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

    @Operation(summary = "Update user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful user update"),
            @ApiResponse(responseCode = "404", description = "Error login"),
            @ApiResponse(responseCode = "500", description = "Bad argument passed"),
    })
    @PostMapping(path = "/updateuser")
    public ResponseEntity<Object> updateInterests(@RequestBody UserDTO userDTO) {
        try {
            Optional<User> userOptional = parserUserDTO.userDTOToUser(userDTO);
            if (userOptional.isPresent()) {
                User updatedUser = updateUserData.updateUser(userOptional.get());
                return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(summary = "Search complementary users from the user passed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful complementary users search"),
            @ApiResponse(responseCode = "404", description = "Error login"),
    })
    @GetMapping(path = "/getcomplementaryusers")
    public ResponseEntity<Object> searchComplementaryUsers(String username) {
        Optional<User> userOptional = getUserData.getUser(username);
        if (userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(searchComplementaryUsers.searchUsers(userOptional.get())
                    .stream()
                    .map(parserUserDTO::userToUserDTO)
                    .collect(Collectors.toList()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
        }
    }

}
