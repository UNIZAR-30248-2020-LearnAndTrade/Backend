package com.project.LearnAndTrade.Controller;

import com.project.LearnAndTrade.DTO.ThemeDTO;
import com.project.LearnAndTrade.DTO.UserDTO;
import com.project.LearnAndTrade.Entity.Theme;
import com.project.LearnAndTrade.Entity.User;
import com.project.LearnAndTrade.Service.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
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

    @Autowired
    private final GetThemes getThemes;

    @Autowired
    private final ParserUserDTO parserUserDTO;

    @Autowired
    private final ParserThemeDTO parserThemeDTO;

    private final User user;

    public UserControllerTests() {
        user = new User("testUsername", "emailUserTest@learnandtrade.com", "testPassword", new ArrayList<>(), new ArrayList<>(),
                "Testing User", "Testing purpose", new Date(1604268089));
        logInService = new LogInUser();
        getUserDataService = new GetUserData();
        updateUserDataService = new UpdateUserData();
        searchComplementaryUsersService = new SearchComplementaryUsers();
        getThemes = new GetThemes();
        parserUserDTO = new ParserUserDTO();
        parserThemeDTO = new ParserThemeDTO();
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
        canGetListOfThemes();
    }

    @BeforeAll
    static void before() {
        System.out.println("Starting 'UserController' testing...");
    }

    @AfterAll
    static void after() {
        System.out.println("Finished 'UserController' testing...");
    }

    @Test
    @Order(1)
    public void canCreateUser() {
        User result = updateUserDataService.updateUser(user);
        assertNotNull(result);
        System.out.println("1. 'canCreateUser' test passed");
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
        Optional<User> result = getUserDataService.getUser(user.getUsername() + "2");
        assertFalse(result.isPresent());
        System.out.println("5. 'cantGetUser' test passed");
    }

    @Test
    @Order(6)
    public void canUpdateUser() {
        User newUser = new User(user.getUsername(), "emailUserTest2@learnandtrade.com", user.getPassword() + "2", user.getInterests(), user.getKnowledges(),
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
        Assertions.assertThrows(RuntimeException.class, () -> {
            User result = updateUserDataService.updateUser(null);
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

    @Test
    @Order(10)
    public void canGetListOfThemes() {
        Optional<List<Theme>> result = getThemes.getAllThemes();
        assertTrue(result.isPresent());
        System.out.println("10. 'canGetListOfThemes' test passed");
    }

    @Test
    @Order(11)
    public void canConvertUserDTO() {
        UserDTO userDTO = parserUserDTO.userToUserDTO(user);
        assertEquals(user.getUsername(), userDTO.getUsername());
        Optional<User> result = parserUserDTO.userDTOToUser(userDTO);
        assertTrue(result.isPresent());
        System.out.println("11. 'canConvertUserDTO' test passed");
    }

    @Test
    @Order(12)
    public void canConvertThemeDTO() {
        Optional<List<Theme>> list = getThemes.getAllThemes();
        if (list.isPresent()) {
            List<ThemeDTO> listDTO = parserThemeDTO.themeToThemeDTOList(list.get());
            assertEquals(listDTO.size(), list.get().size());

            Optional<List<Theme>> list2 = parserThemeDTO.themeDTOToThemeList(listDTO);
            assertTrue(list2.isPresent());
            assertEquals(list2.get().size(), list.get().size());

            ThemeDTO themeDTO = parserThemeDTO.themeToThemeDTO(list.get().get(0));
            assertEquals(list.get().get(0).getName(), themeDTO.getName());

            Optional<Theme> theme = parserThemeDTO.themeDTOToTheme(themeDTO);
            assertTrue(theme.isPresent());
            assertEquals(list.get().get(0).getName(), theme.get().getName());
        }
        System.out.println("12. 'canConvertThemeDTO' test passed");
    }

}
