package de.paul2708.commands.arguments.impl;

import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.Validation;
import de.paul2708.commands.language.MessageResource;

import java.util.List;

/**
 * This command arguments represents a {@link List} argument.<br>
 * The item type must have a command argument wrapper.
 * If you want to escape the separator, use '\x' where x is the separator char.
 *
 * @param <T> item type
 * @param <A> command argument wrapper for T
 * @author Paul2708
 */
public class ListArgument<T, A extends CommandArgument<T>> implements CommandArgument<List<T>> {

    private final List<T> list;
    private final char separator;

    /**
     * Create a new list argument with ',' as separator.
     *
     * @param list list of arguments that have command argument wrapper
     */
    public ListArgument(List<T> list) {
        this(list, ',');
    }

    /**
     * Create a new list argument with a given separator, that separates the items in the list.
     *
     * @param list list of arguments that have command argument wrapper
     * @param separator separator like '-', ',' or ';'
     */
    public ListArgument(List<T> list, char separator) {
        this.list = list;
        this.separator = separator;
    }

    /**
     * Validate the object by a given command argument.
     * The command argument is trimmed, doesn't contain any spaces and is not empty.
     *
     * @param argument command argument
     * @return a valid or invalid validation
     */
    @Override
    public Validation<List<T>> validate(String argument) {
        String[] array = argument.split("" + separator);

        for (String item : array) {

        }

        return null;
    }

    /**
     * Get the correct usage for this type.
     *
     * @return usage
     */
    @Override
    public MessageResource usage() {
        return null;
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
        return null;
    }
}
