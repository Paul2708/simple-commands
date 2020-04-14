package de.paul2708.commands.core.command.argument.result;

import de.paul2708.commands.arguments.CommandArgument;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Optional;

/**
 * This result indicates a successful test result.
 * It contains the mapped arguments that can be used to invoke the command method.
 *
 * @author Paul2708
 */
public class SuccessResult extends TestResult {

    private final List<Optional<Object>> mappedArguments;

    /**
     * Create a new success result with the already mapped arguments and the tested arguments.
     *
     * @param mappedArguments list of validated argument parameters
     * @param testedArguments tested arguments
     */
    public SuccessResult(List<Optional<Object>> mappedArguments, List<CommandArgument<?>> testedArguments) {
        super(testedArguments);

        this.mappedArguments = mappedArguments;
    }

    /**
     * Get the mapped arguments.
     * The empty items will be replaced with <code>null</code>
     * in {@link de.paul2708.commands.core.command.CommandDelegator#execute(CommandSender, String, String[])}.
     *
     * @return list of parameters
     * @see de.paul2708.commands.arguments.Validation#valid(Object)
     */
    public List<Optional<Object>> getMappedArguments() {
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