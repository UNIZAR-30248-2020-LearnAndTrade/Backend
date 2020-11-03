package com.project.LearnAndTrade.Controller;

import com.project.LearnAndTrade.Entity.User;
import com.project.LearnAndTrade.Service.GetUserData;
import com.project.LearnAndTrade.Service.LogInUser;
import com.project.LearnAndTrade.Service.SearchComplementaryUsers;
import com.project.LearnAndTrade.Service.UpdateUserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
public class UserControllerTests {

    @Autowired
    private final LogInUser logInService;

    @Autowired
    private final GetUserData getUserDataService;

    @Autowired
    private final UpdateUserData updateUserDataService;

    @Autowired
    private final SearchComplementaryUsers searchComplementaryUsersService;

    private final User user;

    public UserControllerTests() {
        user = new User("testUsername", "emailUserTest@learnandtrade.com", "testPassword", new ArrayList<>(), new ArrayList<>(),
                "Testing User", "Testing purpose", new Date(1604268089));
        logInService = new LogInUser();
        getUserDataService = new GetUserData();
        updateUserDataService = new UpdateUserData();
        searchComplementaryUsersService = new SearchComplementaryUsers();
    }

    public void runAll() {
        canCreateUser();
        canLogIn();
        cantLogIn();
        canGetUser();
        cantGetUser();
        canUpdateUser();
        cantUpdateUser();
        canGetComplementaryUsers();
        cantGetComplementaryUsers();
    }

    @Test
    @Order(1)
    public void canCreateUser() {
        // TODO: Test 'canCreateUser' not implemented yet
    }

    @Test
    @Order(2)
    public void canLogIn() {
        Optional<User> result = logInService.logIn(user.getUsername(), user.getPassword());
        assertTrue(result.isPresent());
        System.out.println("2. 'canLogIn' test passed");
    }

    @Test
    @Order(3)
    public void cantLogIn() {
        Optional<User> result = logInService.logIn("", "");
        assertFalse(result.isPresent());
        System.out.println("3. 'cantLogIn' test passed");
    }

    @Test
    @Order(4)
    public void canGetUser() {
        Optional<User> result = getUserDataService.getUser(user.getUsername());
        assertTrue(result.isPresent());
        System.out.println("4. 'canGetUser' test passed");
    }

    @Test
    @Order(5)
    public void cantGetUser() {
        Optional<User> result = getUserDataService.getUser(user.getUsername());
        assertFalse(result.isPresent());
        System.out.println("5. 'cantGetUser' test passed");
    }

    @Test
    @Order(6)
    public void canUpdateUser() {
        User newUser = new User(user.getUsername() + "2", user.getEmail(), user.getPassword() + "2", user.getInterests(), user.getKnowledges(),
                user.getName() + "2", user.getSurname() + "2", new Date());
        User result = updateUserDataService.updateUser(newUser);
        assertNotNull(result);
        assertEquals(newUser.getUsername(), result.getUsername());
        assertEquals(newUser.getPassword(), result.getPassword());
        assertEquals(newUser.getInterests(), result.getInterests());
        assertEquals(newUser.getKnowledges(), result.getKnowledges());
        assertEquals(newUser.getName(), result.getName());
        assertEquals(newUser.getSurname(), result.getSurname());
        assertEquals(newUser.getBirthDate(), result.getBirthDate());
        System.out.println("6. 'canUpdateUser' test passed");
    }

    @Test
    @Order(7)
    public void cantUpdateUser() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            User newUser = new User(user.getUsername() + "2", user.getEmail(), user.getPassword() + "2", user.getInterests(), user.getKnowledges(),
                    user.getName() + "2", user.getSurname() + "2", new Date());
            User result = updateUserDataService.updateUser(newUser);
            assertNull(result);
        });
        System.out.println("7. 'cantUpdateUser' test passed");
    }

    @Test
    @Order(8)
    public void canGetComplementaryUsers() {
        List<User> result = searchComplementaryUsersService.searchUsers(user);
        assertNotNull(result);
        assertTrue(result.isEmpty());
        System.out.println("8. 'canGetComplementaryUsers' test passed");
    }

    @Test
    @Order(9)
    public void cantGetComplementaryUsers() {
        List<User> result = searchComplementaryUsersService.searchUsers(user);
        assertNotNull(result);
        System.out.println("9. 'cantGetComplementaryUsers' test passed");
    }

}