package guru.springframework.sfgpetclinic.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

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
}
