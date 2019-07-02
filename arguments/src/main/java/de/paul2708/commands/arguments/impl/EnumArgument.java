package de.paul2708.commands.arguments.impl;

import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.Validation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This command arguments represents an {@link Enum} argument.
 *
 * @param <T> enum type parameter
 * @author Paul2708
 */
public class EnumArgument<T extends Enum<T>> implements CommandArgument<Enum<T>> {

    private final Class<T> enumClass;

    /**
     * Create a new enum argument with enum class as parameter.
     *
     * @param enumClass enum class type
     */
    public EnumArgument(Class<T> enumClass) {
        this.enumClass = enumClass;
    }

    /**
     * Validate the object by a given command argument.
     * The command argument is trimmed, doesn't contain any spaces and is not empty.
     *
     * @param argument command argument
     * @return a valid or invalid validation
     */
    @Override
    public Validation<Enum<T>> validate(String argument) {
        try {
            return Validation.valid(Enum.valueOf(enumClass, argument.toUpperCase()));
        } catch (IllegalArgumentException e) {
            return Validation.invalid(String.format("'%s' is not valid for %s", argument, enumClass));
        }
    }

    /**
     * Get the correct usage for this type.
     *
     * @return usage
     */
    @Override
    public String usage() {
        return "[" + enumClass.getSimpleName() + "]";
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
        List<String> autoComplete = Arrays.stream(enumClass.getEnumConstants())
                .map(Enum::toString)
                .filter(name -> name.startsWith(argument))
                .collect(Collectors.toList());

        return Collections.unmodifiableList(autoComplete);
    }
}
