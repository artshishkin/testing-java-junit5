package guru.springframework.sfgpetclinic.controllers;

import org.junit.jupiter.api.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class IndexControllerTest {

    IndexController controller;

    @BeforeEach
    void setUp() {
        controller = new IndexController();
    }

    @Test
    @DisplayName("Test Proper View Name is returned for index page")
    void index() {
        assertEquals("index", controller.index());
        assertEquals("index", controller.index(), "Wrong View Returned");

        assertEquals("index", controller.index(), () -> "Another Expensive Message " +
                "Make me only if you have to");
    }

    @Test
    @DisplayName("Test exception")
    void oupsHandler() {
        assertThrows(ValueNotFoundException.class, () -> {
            controller.oupsHandler();
        });
    }

    @Test
    @Disabled("Sample of timeout failing test (may enable for studying)")
    void testTimeout() {
        System.out.println("Start simple timeoutTest");
        long start = System.currentTimeMillis();
        assertAll("Timeout test:",
                () -> assertTimeout(Duration.ofMillis(100), () -> {
                    Thread.sleep(2000);
                    System.out.println("I got here");
                }),
                () -> System.out.printf("Execution take %d ms", System.currentTimeMillis() - start));
//   org.opentest4j.AssertionFailedError: execution exceeded timeout of 100 ms by 4901 ms
//  do not abort execution

    }

    @Test
    @Disabled("Sample of timeout failing test (may enable for studying)")
    void testTimeoutPreemptive() {
        System.out.println("Start preemptiveTimeoutTest");
        long start = System.currentTimeMillis();
        assertAll("Timeout preemptive test:",
                () -> assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
                    Thread.sleep(2000);
                    System.out.println("I got here");
                }),
                () -> System.out.printf("Execution take %d ms", System.currentTimeMillis() - start));
//        org.opentest4j.AssertionFailedError: execution timed out after 100 ms
        // ----- ABORT execution ------
    }


    @Test
    void testAssumptionTrue() {
        Assumptions.assumeTrue("ART".equalsIgnoreCase(System.getenv("ART_RUNTIME")));
    }

    @Test
    void testAssumptionIsTrue() {
        Assumptions.assumeTrue("ART".equalsIgnoreCase("art"));
    }
}
