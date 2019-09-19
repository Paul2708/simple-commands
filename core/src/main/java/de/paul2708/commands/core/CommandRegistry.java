package de.paul2708.commands.core;

import de.paul2708.commands.arguments.ArgumentHolder;
import de.paul2708.commands.core.annotation.Command;
import de.paul2708.commands.core.command.SimpleCommand;
import de.paul2708.commands.core.language.LanguageSelector;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * This interface is the command registry, that registers commands and adds arguments.
 *
 * @author Paul2708
 */
public interface CommandRegistry {

    /**
     * Set the injected object value.<br>
     * <code>@Inject</code> will refer to the given instance.
     *
     * @param object object
     */
    void inject(Object object);

    /**
     * Set the injected object values.<br>
     * <code>@Inject(key = "key")</code> will refer to the given instance.
     *
     * @param key key
     * @param object object
     */
    void inject(String key, Object object);

    /**
     * Register all methods with {@link Command}-annotation in these objects.
     *
     * @param objects array of objects
     */
    void register(Object... objects);

    /**
     * Get an unmodifiable list of all registered commands.
     *
     * @see #register(Object...)
     * @return list of commands
     */
    List<SimpleCommand> getCommands();

    /**
     * Get the argument holder to add command arguments.
     *
     * @return argument holder
     */
    ArgumentHolder getArgumentHolder();

    /**
     * Get the language selector.
     *
     * @return language selector
     */
    LanguageSelector getLanguageSelector();

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
