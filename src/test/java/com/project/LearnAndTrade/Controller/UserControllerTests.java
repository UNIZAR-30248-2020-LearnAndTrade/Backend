package com.project.LearnAndTrade.Controller;

import com.project.LearnAndTrade.DTO.ThemeDTO;
import com.project.LearnAndTrade.DTO.UserDTO;
import com.project.LearnAndTrade.Entity.User;
import com.project.LearnAndTrade.Service.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTests {

    @Autowired
    private final UserController userController;

    @Autowired
    private final SignInUser signInUser;

    @Autowired
    private final ParserUserDTO parserUserDTO;

    private final UserDTO userDTO;

    public UserControllerTests() {
        this.userController = new UserController();
        this.signInUser = new SignInUser();
        parserUserDTO = new ParserUserDTO();
        List<ThemeDTO> interests = new ArrayList<>();
        interests.add(new ThemeDTO("Java"));
        List<ThemeDTO> knowledges = new ArrayList<>();
        knowledges.add(new ThemeDTO("Historia"));
        userDTO = new UserDTO("testUsername", "emailUserTest@learnandtrade.com", "testPassword", interests, knowledges,
                "Testing userDTO", "Testing purpose", new Date(1604268089), "urlImagen");
    }

    @BeforeAll
    public void before() {
        System.out.println("Starting 'UserController' testing...");
    }

    @AfterAll
    public void after() {
        signInUser.deleteUSer(userDTO.getUsername());
        System.out.println("Finished 'UserController' testing...");
    }

    @Test
    @Order(1)
    public void canSignIn() {
        ResponseEntity<UserDTO> responseEntity = userController.signIn(userDTO);
        Assertions.assertEquals(200, responseEntity.getStatusCode().value());
        UserDTO userResponse = responseEntity.getBody();
        assertNotNull(userResponse);
        System.out.println("1. 'canSignIn' test passed");
    }

    @Test
    @Order(2)
    public void cantSignIn() {
        ResponseEntity<UserDTO> responseEntity = userController.signIn(userDTO);
        Assertions.assertEquals(400, responseEntity.getStatusCode().value());
        System.out.println("2. 'cantSignIn' test passed");
    }

    @Test
    @Order(3)
    public void canLogIn() {
        ResponseEntity<UserDTO> responseEntity = userController.logIn(userDTO.getUsername(), userDTO.getPassword());
        Assertions.assertEquals(200, responseEntity.getStatusCode().value());
        UserDTO userResponse = responseEntity.getBody();
        assertNotNull(userResponse);
        System.out.println("3. 'canLogIn' test passed");
    }

    @Test
    @Order(4)
    public void cantLogIn() {
        ResponseEntity<UserDTO> responseEntity = userController.logIn("", "");
        Assertions.assertEquals(404, responseEntity.getStatusCode().value());
        System.out.println("4. 'cantLogIn' test passed");
    }

    @Test
    @Order(5)
    public void canGetUser() {
        ResponseEntity<UserDTO> responseEntity = userController.getUser(userDTO.getUsername());
        Assertions.assertEquals(200, responseEntity.getStatusCode().value());
        UserDTO userResponse = responseEntity.getBody();
        assertNotNull(userResponse);
        System.out.println("5. 'canGetUser' test passed");
    }

    @Test
    @Order(6)
    public void cantGetUser() {
        ResponseEntity<UserDTO> responseEntity = userController.getUser(userDTO.getUsername() + "2");
        Assertions.assertEquals(404, responseEntity.getStatusCode().value());
        System.out.println("6. 'cantGetUser' test passed");
    }

    @Test
    @Order(7)
    public void canUpdateUser() {
        UserDTO newUser = new UserDTO(userDTO.getUsername(), "emailUserTest2@learnandtrade.com", userDTO.getPassword() + "2", userDTO.getInterests(),
                userDTO.getKnowledges(),userDTO.getName() + "2", userDTO.getSurname() + "2", new Date(), "urlImagen2");
        ResponseEntity<UserDTO> responseEntity = userController.updateUser(newUser);
        Assertions.assertEquals(200, responseEntity.getStatusCode().value());
        UserDTO result = responseEntity.getBody();
        assertNotNull(result);
        System.out.println("7. 'canUpdateUser' test passed");
    }

    @Test
    @Order(8)
    public void cantUpdateUser() {
        UserDTO newUser = new UserDTO(null, "emailUserTest2@learnandtrade.com", userDTO.getPassword() + "2", userDTO.getInterests(),
                userDTO.getKnowledges(),userDTO.getName() + "2", userDTO.getSurname() + "2", new Date(), "urlImagen2");
        ResponseEntity<UserDTO> responseEntity = userController.updateUser(newUser);
        Assertions.assertEquals(404, responseEntity.getStatusCode().value());
        System.out.println("8. 'cantUpdateUser' test passed");
    }

    @Test
    @Order(9)
    public void canGetComplementaryUsers() {
        ResponseEntity<List<UserDTO>> responseEntity = userController.searchComplementaryUsers(userDTO.getUsername());
        Assertions.assertEquals(200, responseEntity.getStatusCode().value());
        List<UserDTO> result = responseEntity.getBody();
        assertNotNull(result);
        System.out.println("9. 'canGetComplementaryUsers' test passed");
    }

    @Test
    @Order(10)
    public void cantGetComplementaryUsers() {
        ResponseEntity<List<UserDTO>> responseEntity = userController.searchComplementaryUsers("");
        Assertions.assertEquals(404, responseEntity.getStatusCode().value());
        System.out.println("10. 'cantGetComplementaryUsers' test passed");
    }

    @Test
    @Transactional
    @Order(11)
    public void searchUsersByListsOfThemes() {
        ResponseEntity<List<UserDTO>> responseEntity =
                userController.searchUsersByList(new String[]{"Java"},true);
        Assertions.assertEquals(200, responseEntity.getStatusCode().value());
        System.out.println("11. 'searchUsersByListOfInterests' test passed");
    }

    @Test
    @Order(12)
    public void canConvertUserDTO() {
        Optional<User> user = parserUserDTO.userDTOToUser(userDTO);
        Assertions.assertTrue(user.isPresent());
        assertEquals(userDTO.getUsername(), user.get().getUsername());
        UserDTO result = parserUserDTO.userToUserDTO(user.get());
        assertNotNull(result);
        System.out.println("12. 'canConvertUserDTO' test passed");
    }

}
