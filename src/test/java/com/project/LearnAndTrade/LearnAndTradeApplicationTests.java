package com.project.LearnAndTrade;

import com.project.LearnAndTrade.Controller.ChatControllerTests;
import com.project.LearnAndTrade.Controller.UserControllerTests;
import org.junit.jupiter.api.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;


//@SpringBootTest
class LearnAndTradeApplicationTests {

    //@Test
    void main() {
        System.out.println("Preparing environment for testing...");
        Result result = JUnitCore.runClasses(TestSuite.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println("All test passed: " + result.wasSuccessful());
    }

}

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UserControllerTests.class,
        ChatControllerTests.class
})
class TestSuite {}
