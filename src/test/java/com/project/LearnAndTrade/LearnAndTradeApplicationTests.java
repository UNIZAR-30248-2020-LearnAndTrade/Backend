package com.project.LearnAndTrade;

import com.project.LearnAndTrade.Controller.UserControllerTests;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LearnAndTradeApplicationTests {

    UserControllerTests userControllerTests = new UserControllerTests();

    LearnAndTradeApplicationTests() throws Exception {
    }

    @Test
    void main() throws Exception {
        System.out.println("Preparing environment for testing...");
        runUserControllerTests();
    }

    void runUserControllerTests() {
        System.out.println("Starting 'UserController' testing...");
        userControllerTests.runAll();
        System.out.println("Finished 'UserController' testing...");
    }

}
