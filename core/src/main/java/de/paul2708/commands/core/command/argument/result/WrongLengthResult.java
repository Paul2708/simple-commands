package de.paul2708.commands.core.command.argument.result;

import de.paul2708.commands.arguments.CommandArgument;

import java.util.List;

/**
 * This result indicates a failed test result, because the length of expected and actual didn't match.
 *
 * @author Paul2708
 */
public class WrongLengthResult extends TestResult {

    private final int expectedLength;
    private final int actualLength;

    /**
     * Create a new success result with the already mapped arguments and the tested arguments.
     *
     * @param expectedLength  expected amount of arguments
     * @param actualLength    actual amount of arguments
     * @param testedArguments tested arguments
     */
    public WrongLengthResult(int expectedLength, int actualLength, List<CommandArgument<?>> testedArguments) {
        super(testedArguments);

        this.expectedLength = expectedLength;
        this.actualLength = actualLength;
    }

    /**
     * Get the expected length of arguments.
     *
     * @return expected length
     */
    public int getExpectedLength() {
        return expectedLength;
    }

    /**
     * Get the actual length of arguments.
     *
     * @return actual length
     */
    public int getActualLength() {
        return actualLength;
    }

    /**
     * Return the result to string.
     *
     * @return string
     */
    @Override
    public String toString() {
        return "WrongLengthResult{"
                + "expectedLength=" + expectedLength
                + ", actualLength=" + actualLength
                + '}';
    }
}