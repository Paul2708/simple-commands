package de.paul2708.commands.example;

import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.Validation;
import de.paul2708.commands.core.CommandRegistry;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is the main plugin class.
 *
 * @author Paul2708
 */
public class ExamplePlugin extends JavaPlugin {

    /**
     * Add command arguments and register commands.
     */
    @Override
    public void onEnable() {
        CommandRegistry registry = CommandRegistry.create(this);

        registry.addArgument(new CommandArgument<Integer>() {

            @Override
            public Validation<Integer> validate(String argument) {
                try {
                    return Validation.valid(Integer.parseInt(argument));
                } catch (NumberFormatException e) {
                    return Validation.invalid("Given argument is not an integer.");
                }
            }

            @Override
            public String usage() {
                return "[Int]";
            }

            @Override
            public List<String> autoComplete(String argument) {
                return new ArrayList<>();
            }
        });

        registry.register(new TestCommand());
    }
}
