package de.paul2708.commands.core;

import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.core.annotation.Command;
import org.bukkit.plugin.java.JavaPlugin;

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
     * Create a new command registry for the plugin instance.
     *
     * @param plugin plugin instance
     * @return new command registry instance
     */
    static CommandRegistry create(JavaPlugin plugin) {
        return new DefaultCommandRegistry(plugin);
    }
}
