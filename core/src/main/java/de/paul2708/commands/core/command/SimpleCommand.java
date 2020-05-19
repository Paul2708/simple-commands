package de.paul2708.commands.core.command;

import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.core.annotation.Command;

import javax.security.auth.SubjectDomainCombiner;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * This class holds information about the command like name, permission, ... and the java method.
 *
 * @author Paul2708
 */
public final class SimpleCommand {

    private final Command information;
    private final CommandType type;

    private final Object object;
    private final Method method;

    private final List<CommandArgument<?>> arguments;

    private final Set<SimpleCommand> subCommands;

    /**
     * Create a new simple command.
     *
     * @param information basic information about the command
     * @param type        command type
     * @param object      object, that holds the method
     * @param method      command method
     * @param arguments   list of command arguments
     */
    public SimpleCommand(Command information, CommandType type, Object object, Method method,
                         List<CommandArgument<?>> arguments) {
        this.information = information;
        this.type = type;
        this.object = object;
        this.method = method;
        this.arguments = arguments;

        this.subCommands = new HashSet<>();
    }

    public boolean isRoot() {
        return information.parent().length == 0;
    }

    /**
     * Get the bukkit command label that refers to the command.
     * If the simple command is <code>parent=["test", "2ndlayer"], name = "command"</code>,
     * it will return the first parent entry.
     * Otherwise the command name will be taken to trigger the bukkit command.
     *
     * @return bukkit label (first parent entry if it exists, otherwise command name)
     */
    public String getBukkitLabel() {
        return information.parent().length == 0 ? information.name() : information.parent()[0];
    }

    /**
     * Get the information.
     *
     * @return information
     */
    public Command getInformation() {
        return information;
    }

    /**
     * Get the command type.
     *
     * @return command type
     */
    public CommandType getType() {
        return type;
    }

    /**
     * Get the java object, that holds the command method.
     *
     * @return object
     */
    public Object getObject() {
        return object;
    }

    /**
     * Get command method.
     *
     * @return method
     */
    public Method getMethod() {
        return method;
    }

    /**
     * Get the list of command arguments.
     *
     * @return list of arguments
     */
    public List<CommandArgument<?>> getArguments() {
        return arguments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SimpleCommand command = (SimpleCommand) o;
        return Objects.equals(information, command.information)
                && type == command.type
                && Objects.equals(object, command.object)
                && Objects.equals(method, command.method)
                && Objects.equals(arguments, command.arguments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(information, type, object, method, arguments);
    }
}