package guru.springframework.sfgpetclinic;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInstance;

@Tag("controller")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public interface ControllerTests {

    @BeforeAll
    default void beforeAll() {
        System.out.println("Let's do something Before All (from default method in interface)");
    }
}
