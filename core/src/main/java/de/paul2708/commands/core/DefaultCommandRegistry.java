package de.paul2708.commands.core;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.impl.primitive.*;
import de.paul2708.commands.arguments.impl.StringArgument;
import de.paul2708.commands.arguments.util.Pair;
import de.paul2708.commands.core.annotation.Command;
import de.paul2708.commands.core.annotation.Inject;
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

    private static final List<CommandArgument<?>> COMMAND_ARGUMENTS = ImmutableList.<CommandArgument<?>>builder()
            .add(new IntegerArgument())
            .add(new StringArgument())
            .add(new CharacterArgument())
            .add(new ByteArgument())
            .add(new ShortArgument())
            .add(new DoubleArgument())
            .add(new FloatArgument())
            .build();

    private static final Map<Class<?>, Class<?>> PRIMITIVES = ImmutableMap.<Class<?>, Class<?>>builder()
            .put(int.class, Integer.class)
            .put(char.class, Character.class)
            .put(double.class, Double.class)
            .put(float.class, Float.class)
            .put(byte.class, Byte.class)
            .put(short.class, Short.class)
            .build();

    private final JavaPlugin plugin;

    private final Map<Class<?>, CommandArgument<?>> commandArguments;
    private final List<SimpleCommand> commands;

    private final List<Pair<Object, String>> injectedObjects;

    /**
     * Create a new command registry.
     *
     * @param plugin plugin instance
     */
    DefaultCommandRegistry(JavaPlugin plugin) {
        this.plugin = plugin;

        this.commandArguments = new HashMap<>();
        this.commands = new LinkedList<>();

        this.injectedObjects = new ArrayList<>();

        // Add default vales
        for (CommandArgument<?> argument : DefaultCommandRegistry.COMMAND_ARGUMENTS) {
            addArgument(argument);
        }
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
            if (argument == null) {
                throw new IllegalArgumentException("cannot add null arguments");
            }

            Class<?>[] typeArgs = TypeResolver.resolveRawArguments(CommandArgument.class, argument.getClass());

            commandArguments.put(typeArgs[0], argument);
        }
    }

    /**
     * Set the injected object value.<br>
     * <code>@Inject</code> will refer to the given instance.
     *
     * @param object object
     */
    @Override
    public void inject(Object object) {
        inject("", object);
    }

    /**
     * Set the injected object values.<br>
     * <code>@Inject(key = "key")</code> will refer to the given instance.
     *
     * @param key    key
     * @param object object
     */
    @Override
    public void inject(String key, Object object) {
        if (object == null) {
            throw new IllegalArgumentException("injected object cannot be null");
        }

        for (Pair<Object, String> pair : injectedObjects) {
            if (pair.getKey().getClass().equals(object.getClass())
                    && pair.getValue().equals(key)) {
                throw new IllegalArgumentException("an object of " + object.getClass().getName() + " and key " + key
                        + "already exists.");
            }
        }

        injectedObjects.add(Pair.of(object, key));
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
                        Class<?> type = parameters[i].getType();

                        if (type.isPrimitive()) {
                            type = DefaultCommandRegistry.PRIMITIVES.get(type);
                        }
                        if (!commandArguments.containsKey(type)) {
                            throw new IllegalArgumentException(String.format("parameter %s of method %s doesn't have "
                                    + "an argument wrapper", type.getName(), method.getName()));
                        }

                        list.add(commandArguments.get(type));
                    }

                    // Set injected fields
                    for (Field field : clazz.getDeclaredFields()) {
                        if (field.isAnnotationPresent(Inject.class)) {
                            Inject inject = field.getAnnotation(Inject.class);

                            field.setAccessible(true);
                            try {
                                field.set(object, getInjectedValue(inject.key(), field.getType()));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
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
     * Get an unmodifiable list of all added command arguments.
     *
     * @return list of arguments
     * @see #register(Object...)
     */
    @Override
    public List<CommandArgument<?>> getArguments() {
        return Collections.unmodifiableList(new ArrayList<>(commandArguments.values()));
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
     * Get the injected value by key and type.
     *
     * @param key key
     * @param objectClass object class
     * @return injected value or <code>null</code> if none was injected
     */
    private Object getInjectedValue(String key, Class<?> objectClass) {
        for (Pair<Object, String> pair : injectedObjects) {
            if (objectClass.isAssignableFrom(pair.getKey().getClass()) && pair.getValue().equals(key)) {
                return pair.getKey();
            }
        }

        return null;
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
