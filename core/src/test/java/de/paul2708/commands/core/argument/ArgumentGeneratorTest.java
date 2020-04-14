package de.paul2708.commands.core.argument;

import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.OptionalArgument;
import de.paul2708.commands.arguments.impl.StringArgument;
import de.paul2708.commands.arguments.impl.primitive.FloatArgument;
import de.paul2708.commands.arguments.impl.primitive.IntegerArgument;
import de.paul2708.commands.core.command.argument.ArgumentGenerator;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

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
        List<List<CommandArgument<?>>> combinations = generator.generate();

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
        List<List<CommandArgument<?>>> combinations = generator.generate();

        assertEquals(1, combinations.size());
        assertEquals(List.of(firstArg, secondArg), combinations.get(0));
    }

    /**
     * Test a list of only optional arguments.
     */
    @Test
    void onlyOptionalArguments() {
        OptionalArgument<?> firstArg = (OptionalArgument<?>) new StringArgument().asOptional();
        OptionalArgument<?> secondArg = (OptionalArgument<?>) new IntegerArgument().asOptional();

        ArgumentGenerator generator = new ArgumentGenerator(List.of(firstArg, secondArg));
        List<List<CommandArgument<?>>> combinations = generator.generate();

        assertEquals(4, combinations.size());

        assertContains(List.of(firstArg.getInternal(), secondArg.getInternal()), combinations);
        assertContains(List.of(firstArg.getInternal(), secondArg), combinations);
        assertContains(List.of(firstArg, secondArg.getInternal()), combinations);
        assertContains(List.of(firstArg, secondArg), combinations);
    }

    /**
     * Test a list of arguments with optional and none optional arguments.
     */
    @Test
    void mixedArguments() {
        OptionalArgument<?> optFirstArg = (OptionalArgument<?>) new StringArgument().asOptional();
        CommandArgument<?> secondArg = new FloatArgument();
        OptionalArgument<?> optThirdArg = (OptionalArgument<?>) new IntegerArgument().asOptional();
        CommandArgument<?> fourthArg = new IntegerArgument();

        ArgumentGenerator generator = new ArgumentGenerator(List.of(optFirstArg, secondArg, optThirdArg, fourthArg));
        List<List<CommandArgument<?>>> combinations = generator.generate();

        assertEquals(4, combinations.size());

        assertContains(List.of(optFirstArg.getInternal(), secondArg, optThirdArg.getInternal(), fourthArg), combinations);
        assertContains(List.of(optFirstArg.getInternal(), secondArg, optThirdArg, fourthArg), combinations);
        assertContains(List.of(optFirstArg, secondArg, optThirdArg.getInternal(), fourthArg), combinations);
        assertContains(List.of(optFirstArg, secondArg, optThirdArg, fourthArg), combinations);
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