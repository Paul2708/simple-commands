package de.paul2708.commands.core.command;

import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.core.annotation.Command;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

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
    }

    /**
     * Check if the command is a root command.
     *
     * @return true if the command is a root command, otherwise false
     */
    public boolean isRoot() {
        return information.parent().length == 0;
    }

    /**
     * Get the path without parent including its own name.
     * E.g. /root sub subsub returns "sub subsub".
     *
     * @return path without parent including name
     */
    public String[] getPathWithoutParent() {
        if (isRoot()) {
            return new String[0];
        } else {
            String[] arr = new String[information.parent().length];
            System.arraycopy(information.parent(), 1, arr, 0, arr.length - 1);
            arr[information.parent().length - 1] = information.name();
            return arr;
        }
    }

    /**
     * Get the full path of the command including parent and name.
     *
     * @return full path
     */
    public String[] getPath() {
        String[] arr = new String[information.parent().length + 1];
        System.arraycopy(information.parent(), 0, arr, 0, information.parent().length);
        arr[information.parent().length] = information.name();
        return arr;
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
    public String toString() {
        return "SimpleCommand{"
                + "information=" + information
                + ", type=" + type
                + ", object=" + object
                + ", method=" + method
                + ", arguments=" + arguments
                + '}';
    }

    /**
     * Check if two commands are equal.
     *
     * @param o object
     * @return true if every attribute is equal to each other
     */
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

    /**
     * Hash the object.
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(information, type, object, method, arguments);
    }
}