package guru.springframework.sfgpetclinic.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInfo;

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
