package de.paul2708.commands.core.command.registry;

import de.paul2708.commands.core.command.CommandDelegator;
import de.paul2708.commands.core.command.SimpleCommand;
import de.paul2708.commands.core.language.LanguageSelector;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This class registers Bukkit-{@link org.bukkit.command.Command}s by simple commands
 * and keeps track of registered simple commands.
 * <br>
 * Note that this class is not an implementation of {@link de.paul2708.commands.core.CommandRegistry}!
 *
 * @author Paul2708
 */
public final class BukkitCommandRegistry {

    private final JavaPlugin plugin;
    private final LanguageSelector languageSelector;

    private final Map<String, List<SimpleCommand>> commands;

    /**
     * Create a new bukkit command registry.
     *
     * @param plugin           java plugin instance to register commands
     * @param languageSelector language selector
     */
    public BukkitCommandRegistry(JavaPlugin plugin, LanguageSelector languageSelector) {
        this.plugin = plugin;
        this.languageSelector = languageSelector;

        this.commands = new HashMap<>();
    }

    /**
     * Register a simple command as a bukkit command by using the command map.
     * The plugin name will be used as command prefix.
     *
     * @param simpleCommand simple command to register
     */
    public void register(SimpleCommand simpleCommand) {
        if (commands.containsKey(simpleCommand.getBukkitLabel())) {
            commands.get(simpleCommand.getBukkitLabel()).add(simpleCommand);
        } else {
            List<SimpleCommand> list = new LinkedList<>();
            list.add(simpleCommand);
            commands.put(simpleCommand.getBukkitLabel(), list);

            Command bukkitCommand = new CommandDelegator(languageSelector, list);
            try {
                Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                commandMapField.setAccessible(true);

                CommandMap commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());
                commandMap.register(plugin.getName(), bukkitCommand);

                for (String alias : simpleCommand.getInformation().aliases()) {
                    commandMap.register(alias, plugin.getName(), bukkitCommand);
                }
            } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException exception) {
                throw new RuntimeException(String.format("Couldn't register command %s", simpleCommand.toString()));
            }
        }
    }
}