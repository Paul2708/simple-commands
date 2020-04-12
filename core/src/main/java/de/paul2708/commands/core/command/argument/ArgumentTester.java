package de.paul2708.commands.core.command.argument;

import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.Validation;
import de.paul2708.commands.core.command.argument.result.InvalidMappingResult;
import de.paul2708.commands.core.command.argument.result.SuccessResult;
import de.paul2708.commands.core.command.argument.result.TestResult;
import de.paul2708.commands.core.command.argument.result.WrongLengthResult;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        List<CommandArgument<?>> presentArgs = arguments.stream()
                .filter(arg -> !arg.isOptional())
                .collect(Collectors.toList());
        if (presentArgs.size() != userArgs.length) {
            return new WrongLengthResult(presentArgs.size(), userArgs.length, arguments);
        }

        List<Optional<Object>> validParameters = new LinkedList<>();

        int pureArgumentCounter = 0;
        for (int i = 0; i < arguments.size(); i++) {
            CommandArgument<?> argument = arguments.get(i);

            if (argument.isOptional()) {
                validParameters.add(Optional.empty());
                continue;
            }

            Validation<?> validation = argument.validate(userArgs[pureArgumentCounter]);

            if (!validation.isValid()) {
                return new InvalidMappingResult(userArgs[pureArgumentCounter], validation, arguments);
            }

            validParameters.add(Optional.of(validation.getParsedObject()));

            pureArgumentCounter++;
        }

        return new SuccessResult(validParameters, arguments);
    }
}