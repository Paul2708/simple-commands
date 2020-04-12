package de.paul2708.commands.core.command.argument;

import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.Validation;
import de.paul2708.commands.core.command.argument.result.InvalidMappingResult;
import de.paul2708.commands.core.command.argument.result.SuccessResult;
import de.paul2708.commands.core.command.argument.result.TestResult;
import de.paul2708.commands.core.command.argument.result.WrongLengthResult;

import java.util.LinkedList;
import java.util.List;

/**
 * This class tries to match user input with a given list of command arguments.
 *
 * @author Paul2708
 * @see ArgumentGenerator
 */
public final class ArgumentTester {

    private final String[] userArgs;

    /**
     * Create a new argument tester with an array of user input arguments.
     *
     * @param userArgs array of user input arguments
     */
    public ArgumentTester(String[] userArgs) {
        this.userArgs = userArgs;
    }

    /**
     * Test if the user arguments match the given arguments.
     *
     * @param arguments list of command arguments
     * @return test result
     * @see TestResult
     */
    public TestResult test(List<CommandArgument<?>> arguments) {
        if (arguments.size() != userArgs.length) {
            return new WrongLengthResult(arguments.size(), userArgs.length, arguments);
        }

        List<Object> validParameters = new LinkedList<>();
        for (int i = 0; i < userArgs.length; i++) {
            CommandArgument<?> argument = arguments.get(i);
            Validation<?> validation = argument.validate(userArgs[i]);

            if (!validation.isValid()) {
                return new InvalidMappingResult(userArgs[i], validation, arguments);
            }

            validParameters.add(validation.getParsedObject());
        }

        return new SuccessResult(validParameters, arguments);
    }
}