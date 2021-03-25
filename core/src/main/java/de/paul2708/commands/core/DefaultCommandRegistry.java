package de.paul2708.commands.core;

import de.paul2708.commands.arguments.ArgumentHolder;
import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.util.Pair;
import de.paul2708.commands.core.annotation.Command;
import de.paul2708.commands.core.annotation.Inject;
import de.paul2708.commands.core.annotation.Optional;
import de.paul2708.commands.core.command.CommandType;
import de.paul2708.commands.core.command.SimpleCommand;
import de.paul2708.commands.core.command.registry.BukkitCommandRegistry;
import de.paul2708.commands.core.language.DefaultLanguageSelector;
import de.paul2708.commands.core.language.LanguageSelector;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This class implements the command registry.
 *
 * @author Paul2708
 */
public final class DefaultCommandRegistry implements CommandRegistry {

    private final JavaPlugin plugin;

    private final ArgumentHolder argumentHolder;
    private final List<SimpleCommand> commands;

    private final List<Pair<Object, String>> injectedObjects;

    private final LanguageSelector languageSelector;

    private final BukkitCommandRegistry bukkitCommandRegistry;

    /**
     * Create a new command registry.
     *
     * @param plugin plugin instance
     */
    DefaultCommandRegistry(JavaPlugin plugin) {
        this.plugin = plugin;

        this.argumentHolder = ArgumentHolder.create();
        this.commands = new LinkedList<>();

        this.injectedObjects = new ArrayList<>();

        this.languageSelector = new DefaultLanguageSelector();

        this.bukkitCommandRegistry = new BukkitCommandRegistry(plugin, languageSelector);
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
                    ParameterToArgumentMapper mapper = new ParameterToArgumentMapper(method, parameters, argumentHolder);
                    List<CommandArgument<?>> list = mapper.map();

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
                    bukkitCommandRegistry.register(simpleCommand);
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
     * Get the argument holder to add command arguments.
     *
     * @return argument holder
     */
    @Override
    public ArgumentHolder getArgumentHolder() {
        return argumentHolder;
    }

    /**
     * Get the language selector.
     *
     * @return language selector
     */
    @Override
    public LanguageSelector getLanguageSelector() {
        return languageSelector;
    }

    /**
     * Get the injected value by key and type.
     *
     * @param key         key
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
}