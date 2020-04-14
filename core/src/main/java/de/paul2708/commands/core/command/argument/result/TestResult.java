package de.paul2708.commands.core.command.argument.result;

import de.paul2708.commands.arguments.CommandArgument;

import java.util.List;

/**
 * This class represents the result of {@link de.paul2708.commands.core.command.argument.ArgumentTester#test(List)}.
 *
 * @author Paul2708
 */
public abstract class TestResult {

    private final List<CommandArgument<?>> testedArguments;

    /**
     * Create a new test result with the tested list of arguments.
     *
     * @param testedArguments tested arguments
     */
    TestResult(List<CommandArgument<?>> testedArguments) {
        this.testedArguments = testedArguments;
    }

    /**
     * Get the tested arguments.
     *
     * @return tested arguments
     */
    public List<CommandArgument<?>> getTestedArguments() {
        return testedArguments;
    }
}