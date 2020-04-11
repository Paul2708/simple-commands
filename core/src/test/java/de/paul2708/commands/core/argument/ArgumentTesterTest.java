package de.paul2708.commands.core.argument;

import de.paul2708.commands.arguments.impl.StringArgument;
import de.paul2708.commands.arguments.impl.primitive.BooleanArgument;
import de.paul2708.commands.arguments.impl.primitive.DoubleArgument;
import de.paul2708.commands.arguments.impl.primitive.IntegerArgument;
import de.paul2708.commands.core.command.argument.ArgumentTester;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This test class tests the {@link ArgumentTester} if the input matches the arguments.
 *
 * @author Paul2708
 */
final class ArgumentTesterTest {

    // TODO: Extend it by more test cases with different arguments and commands

    private ArgumentTester tester;

    /**
     * Set up a new argument tester with given user input.
     */
    @BeforeEach
    void setUp() {
        this.tester = new ArgumentTester(new String[]{"10", "hello", "optional", "5.0"});
    }

    /**
     * Test if all needed arguments are present.
     */
    @Test
    void validArguments() {
        assertTrue(tester.test(List.of(new IntegerArgument(), new StringArgument(), new StringArgument(),
                new DoubleArgument())));
    }

    /**
     * Test if all there are more arguments than needed.
     */
    @Test
    void testFalseLength() {
        assertFalse(tester.test(List.of(new IntegerArgument(), new StringArgument(), new StringArgument(),
                new DoubleArgument(), new BooleanArgument())));
    }

    /**
     * Test if optional arguments got matched correctly.
     */
    @Test
    public void optionalArguments() {
        assertTrue(tester.test(List.of(new IntegerArgument(), new StringArgument(), new StringArgument().asOptional(),
                new DoubleArgument())));
    }

    /**
     * Test if matching fails.
     * Expected int (or string), but found boolean argument.
     */
    @Test
    public void invalidArguments() {
        assertFalse(tester.test(List.of(new BooleanArgument(), new StringArgument(), new StringArgument(),
                new DoubleArgument())));
    }
}