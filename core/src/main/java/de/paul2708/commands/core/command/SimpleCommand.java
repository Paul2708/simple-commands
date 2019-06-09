package de.paul2708.commands.core.command;

import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.core.annotation.Command;

import java.lang.reflect.Method;
import java.util.List;

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
     * @param type command type
     * @param object object, that holds the method
     * @param method command method
     * @param arguments list of command arguments
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
}
