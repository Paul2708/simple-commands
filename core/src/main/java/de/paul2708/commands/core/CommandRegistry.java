package de.paul2708.commands.core;

import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.core.annotation.Command;
import de.paul2708.commands.core.command.SimpleCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * This interface is the command registry, that registers commands and adds arguments.
 *
 * @author Paul2708
 */
public interface CommandRegistry {

    /**
     * Add command arguments.
     *
     * @param arguments command arguments
     */
    void addArgument(CommandArgument<?>... arguments);

    /**
     * Register all methods with {@link Command}-annotation in these objects.
     *
     * @param objects array of objects
     */
    void register(Object... objects);

    /**
     * Get an unmodifiable list of all added command arguments.
     *
     * @see #register(Object...)
     * @return list of arguments
     */
    List<CommandArgument<?>> getArguments();

    /**
     * Get an unmodifiable list of all registered commands.
     *
     * @see #register(Object...)
     * @return list of commands
     */
    List<SimpleCommand> getCommands();

    /**
     * Create a new command registry for the plugin instance.
     *
     * @param plugin plugin instance
     * @return new command registry instance
     */
    static CommandRegistry create(JavaPlugin plugin) {
        if (plugin == null) {
            throw new IllegalArgumentException("plugin cannot be null");
        }

        return new DefaultCommandRegistry(plugin);
    }
}
