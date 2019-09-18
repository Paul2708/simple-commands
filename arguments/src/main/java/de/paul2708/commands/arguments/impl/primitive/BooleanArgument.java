package de.paul2708.commands.arguments.impl.primitive;

import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.Validation;
import de.paul2708.commands.language.MessageResource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This command arguments represents a {@link Boolean} argument.
 *
 * @author Paul2708
 */
public class BooleanArgument implements CommandArgument<Boolean> {

    /**
     * Valid arguments that will be evaluated to <ocde>true</ocde>.
     */
    public static final String[] TRUE_KEYS = new String[] {
            "true", "yes", "y", "ja", "wahr", "correct"
    };

    /**
     * Valid arguments that will be evaluated to <ocde>false</ocde>.
     */
    public static final String[] FALSE_KEYS = new String[] {
            "false", "no", "n", "nein", "falsch", "negative"
    };

    /**
     * Validate the object by a given command argument.
     * The command argument is trimmed, doesn't contain any spaces and is not empty.
     *
     * @param argument command argument
     * @return a valid or invalid validation
     */
    @Override
    public Validation<Boolean> validate(String argument) {
        for (String trueKey : BooleanArgument.TRUE_KEYS) {
            if (trueKey.equalsIgnoreCase(argument)) {
                return Validation.valid(true);
            }
        }
        for (String falseKey : BooleanArgument.FALSE_KEYS) {
            if (falseKey.equalsIgnoreCase(argument)) {
                return Validation.valid(false);
            }
        }

        return Validation.invalid(MessageResource.of("argument.boolean.invalid", argument));
    }

    /**
     * Get the correct usage for this type.
     *
     * @return usage
     */
    @Override
    public String usage() {
        return "[true/false]";
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
        Stream<String> stream = Stream.concat(Arrays.stream(BooleanArgument.TRUE_KEYS),
                Arrays.stream(BooleanArgument.FALSE_KEYS));

        List<String> autoComplete = stream
                .filter(key -> key.startsWith(argument))
                .collect(Collectors.toList());

        return Collections.unmodifiableList(autoComplete);
    }
}
