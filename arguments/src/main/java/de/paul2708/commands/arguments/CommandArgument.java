package de.paul2708.commands.arguments;

import de.paul2708.commands.arguments.exception.NotFulfilledConditionException;

import java.util.List;

/**
 * This interface represents a command argument that can be passed by input parameter.
 *
 * @param <T> passed command argument
 * @author Paul2708
 */
public interface CommandArgument<T> {

    /**
     * Validate the object by a given command argument.
     * The command argument is trimmed, doesn't contain any spaces and is not empty.
     *
     * @param argument command argument
     * @return a valid or invalid validation
     */
    Validation<T> validate(String argument);

    /**
     * Get the correct usage for this type.
     *
     * @return usage
     */
    String usage();

    /**
     * Get a list of strings that are used for the auto completion.
     * The argument can be empty.
     *
     * @param argument command argument (might be empty)
     * @return unmodifiable list of string
     */
    List<String> autoComplete(String argument);

    /**
     * Check if the given condition is fulfilled or not.<br>
     * The command won't be executed, if the condition is not fulfilled.
     *
     * @param condition condition
     * @param description condition description
     */
    static void condition(boolean condition, String description) {
        if (!condition) {
            throw new NotFulfilledConditionException(description);
        }
    }
}
