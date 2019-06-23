package de.paul2708.commands.arguments.impl.primitive;

import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.Validation;

import java.util.Collections;
import java.util.List;

/**
 * This command arguments represents a {@link Byte} argument.
 *
 * @author Paul2708
 */
public class ByteArgument implements CommandArgument<Byte> {

    /**
     * Validate the object by a given command argument.
     * The command argument is trimmed, doesn't contain any spaces and is not empty.
     *
     * @param argument command argument
     * @return a valid or invalid validation
     */
    @Override
    public Validation<Byte> validate(String argument) {
        try {
            return Validation.valid(Byte.parseByte(argument));
        } catch (NumberFormatException e) {
            return Validation.invalid("Given argument is not a byte.");
        }
    }

    /**
     * Get the correct usage for this type.
     *
     * @return usage
     */
    @Override
    public String usage() {
        return "[Byte]";
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
