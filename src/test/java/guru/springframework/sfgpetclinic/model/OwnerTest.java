package guru.springframework.sfgpetclinic.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OwnerTest {

    @Test
    void dependentAssertions() {
        Owner owner = new Owner(1L, "Kate", "Shyshkina");
        owner.setCity("Kramatorsk");
        owner.setTelephone("1111");
        assertAll("Properties Test",
                () -> assertAll("Person Properties",
                        () -> assertEquals("Kat", owner.getFirstName(), () -> "First name does not match"),
                        () -> assertEquals("Shysh", owner.getLastName(), () -> "Last name does not match")),
                () -> assertAll("Owner Properties",
                        () -> assertEquals("Kram", owner.getCity(), "city does not match"),
                        () -> assertEquals("1234", owner.getTelephone(), "telephone does not match")
                ));
    }
}
