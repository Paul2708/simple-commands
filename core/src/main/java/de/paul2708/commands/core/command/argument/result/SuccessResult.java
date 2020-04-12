package de.paul2708.commands.core.command.argument.result;

import de.paul2708.commands.arguments.CommandArgument;

import java.util.List;

/**
 * This result indicates a successful test result.
 * It contains the mapped arguments that can be used to invoke the command method.
 *
 * @author Paul2708
 */
public class SuccessResult extends TestResult {

    private final List<Object> mappedArguments;

    /**
     * Create a new success result with the already mapped arguments and the tested arguments.
     *
     * @param mappedArguments list of validated argument parameters
     * @param testedArguments tested arguments
     */
    public SuccessResult(List<Object> mappedArguments, List<CommandArgument<?>> testedArguments) {
        super(testedArguments);

        this.mappedArguments = mappedArguments;
    }

    /**
     * Get the mapped arguments.
     *
     * @return list of parameters
     * @see de.paul2708.commands.arguments.Validation#valid(Object)
     */
    public List<Object> getMappedArguments() {
        return mappedArguments;
    }

    /**
     * Return the result to string.
     *
     * @return string
     */
    @Override
    public String toString() {
        return "SuccessResult{"
                + "mappedArguments=" + mappedArguments
                + '}';
    }
}