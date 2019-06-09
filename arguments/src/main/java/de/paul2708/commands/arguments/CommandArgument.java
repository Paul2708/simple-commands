package de.paul2708.commands.arguments;

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
     * Check if the given condition is fulfilled or not.
     *
     * @param condition condition
     * @param description condition description
     */
    static void condition(boolean condition, String description) {
        // TODO: Implement me
    }
}
