package de.paul2708.commands.core.argument;

import de.paul2708.commands.arguments.impl.StringArgument;
import de.paul2708.commands.arguments.impl.primitive.BooleanArgument;
import de.paul2708.commands.arguments.impl.primitive.DoubleArgument;
import de.paul2708.commands.arguments.impl.primitive.IntegerArgument;
import de.paul2708.commands.core.command.argument.ArgumentTester;
import de.paul2708.commands.core.command.argument.result.SuccessResult;
import de.paul2708.commands.core.command.argument.result.TestResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

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
        assertSuccess(tester.test(List.of(new IntegerArgument(), new StringArgument(), new StringArgument(),
                new DoubleArgument())));
    }

    /**
     * Test if all there are more arguments than needed.
     */
    @Test
    void testFalseLength() {
        assertFailure(tester.test(List.of(new IntegerArgument(), new StringArgument(), new StringArgument(),
                new DoubleArgument(), new BooleanArgument())));
    }

    /**
     * Test if optional arguments got matched correctly.
     */
    @Test
    public void optionalArguments() {
        assertFailure(tester.test(List.of(new IntegerArgument(), new StringArgument(), new StringArgument().asOptional(),
                new DoubleArgument())));
    }

    /**
     * Test if matching fails.
     * Expected int (or string), but found boolean argument.
     */
    @Test
    public void invalidArguments() {
        assertFailure(tester.test(List.of(new BooleanArgument(), new StringArgument(), new StringArgument(),
                new DoubleArgument())));
    }

    /**
     * Assert that the test result is successful.
     *
     * @param result actual result
     */
    private void assertSuccess(TestResult result) {
        if (!(result instanceof SuccessResult)) {
            fail(String.format("Expected success result bot got %s", result));
        }
    }

    /**
     * Assert that the test result is a failure.
     *
     * @param result actual result
     */
    private void assertFailure(TestResult result) {
        if (result instanceof SuccessResult) {
            fail(String.format("Expected failure result bot got %s", result));
        }
    }
}