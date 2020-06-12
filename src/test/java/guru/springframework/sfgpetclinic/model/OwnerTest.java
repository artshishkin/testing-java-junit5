package guru.springframework.sfgpetclinic.model;

import guru.springframework.sfgpetclinic.ModelTests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

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

    @DisplayName("Enum Source Test")
    @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
    @EnumSource(value = OwnerType.class)
    void testEnumSource(OwnerType ownerType) {
        System.out.println(ownerType);
    }

    @DisplayName("CSV Input Test")
    @ParameterizedTest(name = "{displayName} - [{index}] {argumentsWithNames}")
    @CsvSource({
            "Ukraine, 1, 2",
            "USA,3,4",
            "France,5,6"
    })
    void csvInputTest(String state, int val1, int val2) {
        System.out.printf("State is %s, val1: %d, val2: %d", state, val1, val2);
    }

    @DisplayName("CSV File Source Test")
    @ParameterizedTest(name = "{displayName} [{index}] {arguments}")
    @CsvFileSource(resources = {"/csv_input_file.csv"}, numLinesToSkip = 1)
    void csvFileSourceTest(String state, int val1, int val2) {
        System.out.printf("State is %s, val1: %d, val2: %d", state, val1, val2);
    }

    @DisplayName("Stream Method Source Test")
    @ParameterizedTest(name = "{displayName} [{index}] {arguments}")
    @MethodSource("getArgStream")
    void argsStreamMethodSourceTest(String state, int val1, int val2) {
        System.out.printf("State is %s, val1: %d, val2: %d", state, val1, val2);
    }

    private static Stream<Arguments> getArgStream() {
        return Stream.of(
                Arguments.of("Ukraine", 1, 2),
                Arguments.of("USA", 3, 4),
                Arguments.of("France", 5, 6)
        );
    }

    @DisplayName("Objects 2D Array Method Source Test")
    @ParameterizedTest(name = "{displayName} [{index}] {arguments}")
    @MethodSource("getObjArray")
    void obj2DArrayMethodSourceTest(String state, int val1, int val2) {
        System.out.printf("State is %s, val1: %d, val2: %d", state, val1, val2);
    }

    private static Object[][] getObjArray() {
        return new Object[][]{
                {"Ukraine", 1, 2},
                {"USA", 3, 4},
                {"France", 5, 6}
        };
    }


}
