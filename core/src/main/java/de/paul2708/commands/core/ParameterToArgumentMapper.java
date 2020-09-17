package de.paul2708.commands.core;

import de.paul2708.commands.arguments.ArgumentHolder;
import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.core.annotation.Optional;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * This mapper checks if each parameter has a command argument and maps them to arguments.
 * Optional and multiple arguments will be handled as well.
 *
 * @author Paul2708
 */
public final class ParameterToArgumentMapper {

    private final Method method;
    private final Parameter[] parameters;
    private final ArgumentHolder argumentHolder;

    /**
     * Create a new parameter to argument mapper
     *
     * @param method         command method
     * @param parameters     list of parameters from command method
     * @param argumentHolder argument holder to get arguments
     */
    public ParameterToArgumentMapper(Method method, Parameter[] parameters, ArgumentHolder argumentHolder) {
        this.method = method;
        this.parameters = parameters;
        this.argumentHolder = argumentHolder;
    }

    /**
     * Check if every parameter has its command argument.
     * Declare arguments as optional.
     *
     * @return list of mapped command arguments
     */
    public List<CommandArgument<?>> map() {
        List<CommandArgument<?>> list = new ArrayList<>();

        for (int i = 1; i < parameters.length; i++) {
            Class<?> type = parameters[i].getType();
            boolean isOptional = parameters[i].getAnnotation(Optional.class) != null;

            CommandArgument<?> argument = argumentHolder.resolve(type);

            if (argument == null) {
                throw new IllegalArgumentException(String.format("parameter %s of method %s doesn't have "
                        + "an argument wrapper", type.getName(), method.getName()));
            }

            if (isOptional) {
                argument = argument.asOptional();
            }
            list.add(argument);
        }

        return list;
    }
}