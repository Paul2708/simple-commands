package de.paul2708.commands.core;

import de.paul2708.commands.core.annotation.Command;
import de.paul2708.commands.core.test.CommandArgument;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * This interface is the command registry, that registers commands and adds arguments.
 *
 * @author Paul2708
 */
public interface CommandRegistry {

    /**
     * Register all methods with {@link Command}-annotation in these classes.
     *
     * @param classes array of classes
     */
    void register(Class<?>... classes);

    /**
     * Add command arguments.
     *
     * @param arguments command arguments
     */
    void addArgument(CommandArgument<?>... arguments);

    /**
     * Create a new command registry for the plugin instance.
     *
     * @param plugin plugin instance
     * @return new command registry instance
     */
    static CommandRegistry create(JavaPlugin plugin) {
        // TODO: Implement me
        return null;
    }
}
