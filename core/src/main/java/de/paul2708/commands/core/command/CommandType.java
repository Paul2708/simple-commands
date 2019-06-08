package de.paul2708.commands.core.command;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * This enum holds different command types. A command type decides who the command can execute.
 *
 * @author Paul2708
 */
public enum CommandType {

    /**
     * Only players can execute the command.
     */
    PLAYER_COMMAND(Player.class),

    /**
     * Only the server console can execute the command.
     */
    CONSOLE_COMMAND(ConsoleCommandSender.class),

    /**
     * Both, player and console, can execute the command.
     */
    DEFAULT_COMMAND(CommandSender.class);

    private Class<?> executorClass;

    /**
     * Create a new command type.
     *
     * @param executorClass parameter class, that executes the command
     */
    CommandType(Class<?> executorClass) {
        this.executorClass = executorClass;
    }

    /**
     * Get the executor class.
     *
     * @return class, that executes the command
     */
    public Class<?> getExecutorClass() {
        return executorClass;
    }

    /**
     * Resolve the command type by a given executor class.
     *
     * @param aClass class
     * @return command type by class or <code>null</code> if none was found
     */
    public static CommandType resolveType(Class<?> aClass) {
        for (CommandType type : CommandType.values()) {
            if (aClass.equals(type.getExecutorClass())) {
                return type;
            }
        }

        return null;
    }
}
