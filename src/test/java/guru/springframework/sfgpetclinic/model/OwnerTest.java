package guru.springframework.sfgpetclinic.model;

import guru.springframework.sfgpetclinic.ModelTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class OwnerTest implements ModelTests {

    @Test
    void dependentAssertions() {
        Owner owner = new Owner(1L, "Kate", "Shyshkina");
        owner.setCity("Kramatorsk");
        owner.setTelephone("1111");
        assertAll("Properties Test",
                () -> assertAll("Person Properties",
                        () -> assertEquals("Kate", owner.getFirstName(), () -> "First name does not match"),
                        () -> assertEquals("Shyshkina", owner.getLastName(), () -> "Last name does not match")),
                () -> assertAll("Owner Properties",
                        () -> assertEquals("Kramatorsk", owner.getCity(), "city does not match"),
                        () -> assertEquals("1111", owner.getTelephone(), "telephone does not match")
                ));
    }

    @DisplayName("Value Source Test")
    @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
    @ValueSource(strings = {"Art", "Kate", "Arina", "Nazar"})
    void testValueSource(String val) {
        System.out.println(val);
    }
}
