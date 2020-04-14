package de.paul2708.commands.core.command.argument.result;

import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.Validation;

import java.util.List;

/**
 * This result indicates a failed test result, because a {@link Validation} failed.
 *
 * @author Paul2708
 */
public class InvalidMappingResult extends TestResult {

    private final String argument;
    private final Validation<?> failedValidation;

    /**
     * Create a new success result with the already mapped arguments and the tested arguments.
     *
     * @param argument         argument that couldn't be validated
     * @param failedValidation failed validation
     * @param testedArguments  tested arguments
     */
    public InvalidMappingResult(String argument, Validation<?> failedValidation,
                                List<CommandArgument<?>> testedArguments) {
        super(testedArguments);

        this.argument = argument;
        this.failedValidation = failedValidation;
    }

    /**
     * Get the argument, that couldn't be validated.
     *
     * @return argument
     */
    public String getArgument() {
        return argument;
    }

    /**
     * Get the failed validation.
     *
     * @return failed validation
     */
    public Validation<?> getFailedValidation() {
        return failedValidation;
    }

    /**
     * Return the result to string.
     *
     * @return string
     */
    @Override
    public String toString() {
        return "InvalidMappingResult{"
                + "failedValidation=" + failedValidation
                + '}';
    }
}