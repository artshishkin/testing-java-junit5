package guru.springframework.sfgpetclinic;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class CustomArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        String displayName = context.getDisplayName();
        String methodName = context.getTestMethod().get().getName();
        return Stream.of(
                Arguments.of("Ukraine" + "-" + displayName, 1, 2),
                Arguments.of("USA" + "-" + context.getUniqueId(), 3, 4),
                Arguments.of("France" + "-" + context.getTags(), 5, 6),
                Arguments.of("MethodName:" + methodName, 7, 8)
        );
    }
}
