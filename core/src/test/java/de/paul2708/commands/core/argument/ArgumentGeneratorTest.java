package de.paul2708.commands.core.argument;

import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.impl.StringArgument;
import de.paul2708.commands.arguments.impl.primitive.FloatArgument;
import de.paul2708.commands.arguments.impl.primitive.IntegerArgument;
import de.paul2708.commands.core.command.argument.ArgumentGenerator;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * This class tests the {@link ArgumentGenerator} and its generation with optional arguments.
 *
 * @author Paul2708
 */
final class ArgumentGeneratorTest {

    /**
     * Test empty list of arguments.
     */
    @Test
    void emptyArguments() {
        ArgumentGenerator generator = new ArgumentGenerator(Collections.emptyList());
        List<List<Optional<CommandArgument<?>>>> combinations = generator.generate();

        assertEquals(1, combinations.size());
        assertEquals(0, combinations.get(0).size());
    }

    /**
     * Test argument generation with none optional arguments.
     */
    @Test
    void noneOptionalArguments() {
        StringArgument firstArg = new StringArgument();
        IntegerArgument secondArg = new IntegerArgument();
        ArgumentGenerator generator = new ArgumentGenerator(List.of(firstArg, secondArg));
        List<List<Optional<CommandArgument<?>>>> combinations = generator.generate();

        assertEquals(1, combinations.size());
        assertEquals(List.of(Optional.of(firstArg), Optional.of(secondArg)), combinations.get(0));
    }

    /**
     * Test a list of only optional arguments.
     */
    @Test
    void onlyOptionalArguments() {
        CommandArgument<?> firstArg = new StringArgument().asOptional();
        CommandArgument<?> secondArg = new IntegerArgument().asOptional();

        ArgumentGenerator generator = new ArgumentGenerator(List.of(firstArg, secondArg));
        List<List<Optional<CommandArgument<?>>>> combinations = generator.generate();

        assertEquals(4, combinations.size());

        assertContains(List.of(Optional.of(firstArg), Optional.of(secondArg)), combinations);
        assertContains(List.of(Optional.of(firstArg), Optional.empty()), combinations);
        assertContains(List.of(Optional.empty(), Optional.of(secondArg)), combinations);
        assertContains(List.of(Optional.empty(), Optional.empty()), combinations);
    }

    /**
     * Test a list of arguments with optional and none optional arguments.
     */
    @Test
    void mixedArguments() {
        CommandArgument<?> optFirstArg = new StringArgument().asOptional();
        CommandArgument<?> secondArg = new FloatArgument();
        CommandArgument<?> optThirdArg = new IntegerArgument().asOptional();
        CommandArgument<?> fourthArg = new IntegerArgument();

        ArgumentGenerator generator = new ArgumentGenerator(List.of(optFirstArg, secondArg, optThirdArg, fourthArg));
        List<List<Optional<CommandArgument<?>>>> combinations = generator.generate();

        assertEquals(4, combinations.size());

        assertContains(List.of(Optional.of(optFirstArg), Optional.of(secondArg), Optional.of(optThirdArg), Optional.of(fourthArg)), combinations);
        assertContains(List.of(Optional.of(optFirstArg), Optional.of(secondArg), Optional.empty(), Optional.of(fourthArg)), combinations);
        assertContains(List.of(Optional.empty(), Optional.of(secondArg), Optional.of(optThirdArg), Optional.of(fourthArg)), combinations);
        assertContains(List.of(Optional.empty(), Optional.of(secondArg), Optional.empty(), Optional.of(fourthArg)), combinations);
    }

    /**
     * Assert that the actual list contains the expected item.
     * Otherwise it fails.
     *
     * @param expected expected list item
     * @param actual   actual list
     * @param <T>      list type
     */
    private <T> void assertContains(T expected, List<T> actual) {
        if (!actual.contains(expected)) {
            fail(String.format("Expected %s in list %s", expected, actual));
        }
    }
}