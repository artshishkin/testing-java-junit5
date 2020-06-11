package guru.springframework.sfgpetclinic.model;

import guru.springframework.sfgpetclinic.ModelTests;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest implements ModelTests {

    @Test
    void groupedAssertions() {
        Person person = new Person(1L, "Art", "Shyshkin");
        assertAll("Test props set",
                () -> assertEquals(Long.valueOf(1L), person.getId(), "id not match"),
                () -> assertEquals("Art", person.getFirstName()),
                () -> assertEquals("Shyshkin", person.getLastName(), "last name does not match"));
    }

    @Test
    void notGroupedAssertions() {
        Person person = new Person(1L, "Art", "Shyshkin");
        assertEquals(Long.valueOf(1L), person.getId(), "id not match");
        assertEquals("Art", person.getFirstName());
        assertEquals("Shyshkin", person.getLastName(), "last name does not match");
    }

    @RepeatedTest(value = 10, name = "{displayName} : {currentRepetition} / {totalRepetitions}")
    @DisplayName("Simple Repeated Test")
    void simpleRepeatedTest() {
    }

    @RepeatedTest(2)
    @DisplayName("Repeated Test with Dependency Injection")
    void repeatedTestWithDI(RepetitionInfo repetitionInfo, TestInfo testInfo) {
        System.out.printf("test repeated %d times, left %d\n", repetitionInfo.getCurrentRepetition(), repetitionInfo.getTotalRepetitions() - repetitionInfo.getCurrentRepetition());
        System.out.println("testInfo.getDisplayName():" + testInfo.getDisplayName());
        System.out.println("testInfo.getTags():" + testInfo.getTags());
        System.out.println("testInfo.getTestClass():" + testInfo.getTestClass());
        System.out.println("testInfo.getTestMethod():" + testInfo.getTestMethod());
    }

}
