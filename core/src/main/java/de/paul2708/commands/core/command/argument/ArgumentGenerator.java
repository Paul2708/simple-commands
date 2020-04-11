package de.paul2708.commands.core.command.argument;

import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.core.math.Combinator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The class will generate a list of all possible argument combinations.
 *
 * @author Paul2708
 */
public final class ArgumentGenerator {

    private final List<CommandArgument<?>> arguments;

    /**
     * Create a new argument generator with a list of arguments.
     * {@link #generate()} will produce a list of all possible combinations of arguments.
     *
     * @param arguments arguments to combine
     */
    public ArgumentGenerator(List<CommandArgument<?>> arguments) {
        this.arguments = arguments;
    }

    /**
     * Generate all command argument combinations.
     * Every optional arguments will be replaced with empty and none empty argument.
     *
     * @return list of list of arguments
     */
    public List<List<CommandArgument<?>>> generate() {
        List<List<CommandArgument<?>>> generated = new LinkedList<>();

        List<List<Optional<CommandArgument<?>>>> optionals = Combinator.combinations(arguments.stream()
                .filter(CommandArgument::isOptional)
                .collect(Collectors.toList()))
                .collect(Collectors.toList());

        for (List<Optional<CommandArgument<?>>> optional : optionals) {
            List<CommandArgument<?>> list = new ArrayList<>();

            int optionalCounter = 0;
            for (CommandArgument<?> argument : arguments) {
                if (argument.isOptional()) {
                    if (optional.get(optionalCounter).isPresent()) {
                        list.add(argument);
                    }
                    optionalCounter++;
                } else {
                    list.add(argument);
                }
            }

            generated.add(list);
        }

        return generated;
    }
}