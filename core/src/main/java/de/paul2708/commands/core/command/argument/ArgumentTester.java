package de.paul2708.commands.core.command.argument;

import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.Validation;

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
     * @return true if the user input matches the arguments, otherwise false
     */
    public boolean test(List<CommandArgument<?>> arguments) {
        if (arguments.size() != userArgs.length) {
            return false;
        }

        for (int i = 0; i < userArgs.length; i++) {
            CommandArgument<?> argument = arguments.get(i);
            Validation<?> validation = argument.validate(userArgs[i]);

            if (!validation.isValid()) {
                return false;
            }
        }

        return true;
    }
}