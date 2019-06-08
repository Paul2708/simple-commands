package de.paul2708.commands.core;

import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.core.annotation.Command;
import de.paul2708.commands.core.command.BasicCommand;
import de.paul2708.commands.core.command.CommandType;
import de.paul2708.commands.core.command.SimpleCommand;
import net.jodah.typetools.TypeResolver;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * This class implements the command registry.
 *
 * @author Paul2708
 */
public class DefaultCommandRegistry implements CommandRegistry {

    private final JavaPlugin plugin;

    private final Map<Class<?>, CommandArgument<?>> commandArguments;
    private final List<SimpleCommand> commands;

    /**
     * Create a new command registry.
     *
     * @param plugin plugin instance
     */
    DefaultCommandRegistry(JavaPlugin plugin) {
        this.plugin = plugin;

        this.commandArguments = new HashMap<>();
        this.commands = new LinkedList<>();
    }

    /**
     * Add command arguments.
     *
     * @param arguments command arguments
     */
    @Override
    public void addArgument(CommandArgument<?>... arguments) {
        if (arguments == null) {
            throw new IllegalArgumentException("cannot add null arguments");
        }

        for (CommandArgument<?> argument : arguments) {
            Class<?>[] typeArgs = TypeResolver.resolveRawArguments(CommandArgument.class, argument.getClass());

            commandArguments.put(typeArgs[0], argument);
        }
    }

    /**
     * Register all methods with {@link Command}-annotation in these objects.
     *
     * @param objects array of objects
     */
    @Override
    public void register(Object... objects) {
        if (objects == null) {
            throw new IllegalArgumentException("cannot register null classes");
        }

        for (Object object : objects) {
            Class<?> clazz = object.getClass();

            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Command.class)) {
                    Parameter[] parameters = method.getParameters();

                    // Check parameters length
                    if (parameters.length == 0) {
                        throw new IllegalArgumentException(String.format("method %s doesn't have parameters",
                                method.getName()));
                    }

                    // Check command executor type
                    CommandType commandType = CommandType.resolveType(parameters[0].getType());
                    if (commandType == null) {
                        throw new IllegalArgumentException(String.format("parameters of method %s doesn't start with "
                                + "an executor type", method.getName()));
                    }

                    // Check if each parameter has a command argument
                    List<CommandArgument<?>> list = new ArrayList<>();

                    for (int i = 1; i < parameters.length; i++) {
                        if (!commandArguments.containsKey(parameters[i].getType())) {
                            throw new IllegalArgumentException(String.format("parameter %s of method %s doesn't have "
                                    + "an argument wrapper", parameters[i].getName(), method.getName()));
                        }

                        list.add(commandArguments.get(parameters[i].getType()));
                    }

                    // Register command
                    SimpleCommand simpleCommand = new SimpleCommand(method.getAnnotation(Command.class), commandType,
                            object, method, list);
                    commands.add(simpleCommand);

                    registerBukkitCommand(simpleCommand, new BasicCommand(simpleCommand));
                }
            }
        }
    }

    /**
     * Get an unmodifiable list of all registered commands.
     *
     * @return list of commands
     * @see #register(Object...)
     */
    @Override
    public List<SimpleCommand> getCommands() {
        return Collections.unmodifiableList(commands);
    }

    /**
     * Register a simple command as a bukkit command by using the command map.
     * The plugin name will be used as command prefix.
     *
     * @param simpleCommand simple command
     * @param bukkitCommand to simple command related bukkit command
     */
    private void registerBukkitCommand(SimpleCommand simpleCommand, org.bukkit.command.Command bukkitCommand) {
        try {
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);

            CommandMap commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());
            commandMap.register(plugin.getName(), bukkitCommand);

            for (String alias : simpleCommand.getInformation().aliases()) {
                commandMap.register(alias, plugin.getName(), bukkitCommand);
            }
        } catch (NoSuchFieldException  | IllegalArgumentException | IllegalAccessException exception) {
            exception.printStackTrace();
        }
    }
}
