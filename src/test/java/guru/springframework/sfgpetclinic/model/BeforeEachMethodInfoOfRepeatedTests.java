package guru.springframework.sfgpetclinic.model;

import org.junit.jupiter.api.*;

@Tag("repeated")
public interface BeforeEachMethodInfoOfRepeatedTests {

    @BeforeEach
    default void beforeEachMethod(TestInfo testInfo, RepetitionInfo repetitionInfo) {
        System.out.printf("Starting %s for %d  time%s (total times %d)\n",
                testInfo.getDisplayName(),
                repetitionInfo.getCurrentRepetition(),
                repetitionInfo.getCurrentRepetition() % 10 == 1 ? "" : "s",
                repetitionInfo.getTotalRepetitions());
    }
}
