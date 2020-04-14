package de.paul2708.commands.arguments;

import com.google.common.collect.ImmutableMap;
import de.paul2708.commands.arguments.impl.EnumArgument;
import net.jodah.typetools.TypeResolver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class implements the argument holder.
 *
 * @author Paul2708
 */
public final class DefaultArgumentHolder implements ArgumentHolder {

    private static final Map<Class<?>, Class<?>> PRIMITIVES = ImmutableMap.<Class<?>, Class<?>>builder()
            .put(int.class, Integer.class)
            .put(char.class, Character.class)
            .put(double.class, Double.class)
            .put(float.class, Float.class)
            .put(byte.class, Byte.class)
            .put(short.class, Short.class)
            .put(boolean.class, Boolean.class)
            .build();

    private final Map<Class<?>, CommandArgument<?>> commandArguments;

    /**
     * Create a new default argument holder with default command arguments.
     */
    DefaultArgumentHolder() {
        this.commandArguments = new HashMap<>();

        for (CommandArgument<?> commandArgument : DefaultArgumentHolder.COMMAND_ARGUMENTS) {
            add(commandArgument);
        }
    }

    /**
     * Add a command argument.<br>
     * If there is already a command argument for specific type, the command argument will be replaced.
     *
     * @param argument command argument
     */
    @Override
    public void add(CommandArgument<?> argument) {
        if (argument == null) {
            throw new IllegalArgumentException("cannot add null argument");
        }

        if (argument instanceof EnumArgument<?>) {
            commandArguments.put(((EnumArgument<?>) argument).getEnumClass(), argument);
        } else {
            Class<?>[] typeArgs = TypeResolver.resolveRawArguments(CommandArgument.class, argument.getClass());

            commandArguments.put(typeArgs[0], argument);
        }
    }

    /**
     * Get the matching command argument wrapper for the give argument type.<br>
     * Primitives will be mapped to their wrapper classes.
     * E.g. <code>int</code> refers to <code>Integer</code>.
     *
     * @param argumentType argument type
     * @return matching command argument or <code>null</code> if none argument wrapper matches
     */
    @Override
    public CommandArgument<?> resolve(Class<?> argumentType) {
        Class<?> correctType = argumentType;
        if (argumentType.isPrimitive()) {
            correctType = DefaultArgumentHolder.PRIMITIVES.get(argumentType);
        }

        return commandArguments.getOrDefault(correctType, null);
    }

    /**
     * Get an unmodifiable list of all added command arguments.
     * This includes the the default command arguments in {@link de.paul2708.commands.arguments.impl}.
     *
     * @return unmodifiable list of all command arguments
     */
    @Override
    public List<CommandArgument<?>> getAll() {
        return List.copyOf(commandArguments.values());
    }
}
