package de.paul2708.commands.example;

import com.google.common.collect.ImmutableList;
import de.paul2708.commands.arguments.ArgumentHolder;
import de.paul2708.commands.core.CommandRegistry;
import de.paul2708.commands.core.language.LanguageSelector;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Locale;

/**
 * This class is the main plugin class.
 *
 * @author Paul2708
 */
public final class ExamplePlugin extends JavaPlugin {

    /**
     * Prepare the command registry.
     */
    @Override
    public void onEnable() {
        CommandRegistry registry = CommandRegistry.create(this);

        // Add custom arguments
        List<Person> persons = ImmutableList.of(
                new Person("Paul", 20),
                new Person("Tina", 5),
                new Person("Tom", 18)
        );

        ArgumentHolder argumentHolder = registry.getArgumentHolder();
        argumentHolder.add(new PersonArgument(persons));

        // Select the language
        LanguageSelector languageSelector = registry.getLanguageSelector();
        languageSelector.select(Bukkit.getConsoleSender(), Locale.ENGLISH);
        Bukkit.getPluginManager().registerEvents(new Listener() {

            @EventHandler
            public void onJoin(PlayerJoinEvent event) {
                languageSelector.select(event.getPlayer(), Locale.GERMAN);
            }
        }, this);

        // Inject the plugin
        registry.inject(this);

        // Register the command classes
        registry.register(new TestCommand(), new SubCommand());
    }
}