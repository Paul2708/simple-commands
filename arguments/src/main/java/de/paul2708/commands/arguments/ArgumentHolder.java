package de.paul2708.commands.arguments;

import java.util.List;

/**
 * An argument holder holds added command arguments and resolves them for given argument types.
 *
 * @author Paul2708
 */
public interface ArgumentHolder {

    /**
     * Add a command argument.<br>
     * If there is already a command argument for specific type, the command argument will be replaced.
     *
     * @param argument command argument
     */
    void add(CommandArgument<?> argument);

    /**
     * Get the matching command argument wrapper for the give argument type.<br>
     * Primitives will be mapped to their wrapper classes.
     * E.g. <code>int</code> refers to <code>Integer</code>.
     *
     * @param argumentType argument type
     * @return matching command argument or <code>null</code> if none argument wrapper matches
     */
    CommandArgument<?> resolve(Class<?> argumentType);

    /**
     * Get an unmodifiable list of all added command arguments.
     * This includes the the default command arguments in {@link de.paul2708.commands.arguments.impl}.
     *
     * @return unmodifiable list of all command arguments
     */
    List<CommandArgument<?>> getAll();

    /**
     * Create a new argument holder instance.
     *
     * @return new argument holder implementation
     */
    static ArgumentHolder create() {
        return new DefaultArgumentHolder();
    }
}
