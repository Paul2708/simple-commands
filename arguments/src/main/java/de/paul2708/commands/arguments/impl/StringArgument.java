package de.paul2708.commands.arguments.impl;

import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.Validation;
import de.paul2708.commands.language.MessageResource;

import java.util.Collections;
import java.util.List;

/**
 * This command arguments represents an {@link String} argument.
 * A string is only one word - a string without spaces.
 *
 * @author Paul2708
 */
public class StringArgument implements CommandArgument<String> {

    /**
     * Validate the object by a given command argument.
     * The command argument is trimmed, doesn't contain any spaces and is not empty.
     *
     * @param argument command argument
     * @return a valid or invalid validation
     */
    @Override
    public Validation<String> validate(String argument) {
        String trimmedArgument = argument.trim();

        return Validation.valid(trimmedArgument);
    }

    /**
     * Get the correct usage for this type.
     *
     * @return usage
     */
    @Override
    public MessageResource usage() {
        return MessageResource.of("argument.string.usage");
    }

    /**
     * Get a list of strings that are used for the auto completion.
     * The argument can be empty.
     *
     * @param argument command argument (might be empty)
     * @return unmodifiable list of string
     */
    @Override
    public List<String> autoComplete(String argument) {
        return Collections.emptyList();
    }
}
