package de.paul2708.commands.core.command;

import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.Validation;
import de.paul2708.commands.arguments.exception.NotFulfilledConditionException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This command is a bukkit command that executes the {@link SimpleCommand}.
 *
 * @author Paul2708
 */
public class BasicCommand extends Command {

    private final SimpleCommand simpleCommand;

    /**
     * Create a new basic command based on the simple command.
     *
     * @param simpleCommand simple command
     */
    public BasicCommand(SimpleCommand simpleCommand) {
        super(simpleCommand.getInformation().name());

        this.simpleCommand = simpleCommand;
    }

    /**
     * Executes the command, returning its success
     *
     * @param sender       Source object which is executing this command
     * @param commandLabel The alias of the command used
     * @param args         All arguments passed to the command, split via ' '
     * @return true if the command was successful, otherwise false
     */
    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        // TODO: Add custom listener

        // Check executor
        switch (simpleCommand.getType()) {
            case PLAYER_COMMAND:
                if (!(sender instanceof Player)) {
                    sender.sendMessage("You cannot execute this command, only players allowed.");
                    return false;
                }

                break;
            case CONSOLE_COMMAND:
                if (!(sender instanceof ConsoleCommandSender)) {
                    sender.sendMessage("You cannot execute this command, only players allowed.");
                    return false;
                }

                break;
            case DEFAULT_COMMAND:
                break;
            default:
                break;
        }

        // Check permission
        if (!hasPermission(sender, simpleCommand.getInformation().permission())) {
            sender.sendMessage("You do not have enough permissions to execute the command.");
            return false;
        }

        // Check arguments
        List<CommandArgument<?>> arguments = simpleCommand.getArguments();
        if (arguments.size() != args.length) {
            sender.sendMessage(String.format("False usage. Use %d parameters instead of %d",
                    arguments.size(), args.length));
            return false;
        }

        for (int i = 0; i < args.length; i++) {
            Validation<?> validate = arguments.get(i).validate(args[i]);

            if (!validate.isValid()) {
                sender.sendMessage(String.format("False usage. %s", validate.getErrorMessage()));
                return false;
            }
        }

        // Execute command
        List<Object> parameters = new LinkedList<>();
        parameters.add(sender);

        for (int i = 0; i < args.length; i++) {
            Validation<?> validate = arguments.get(i).validate(args[i]);

            parameters.add(validate.getParsedObject());
        }

        try {
            simpleCommand.getMethod().invoke(simpleCommand.getObject(), parameters.toArray());
            return true;
        } catch (IllegalAccessException e) {
            sender.sendMessage("An error occurred while invoking the command method.");
            e.printStackTrace();
            return false;
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof NotFulfilledConditionException) {
                NotFulfilledConditionException exception = (NotFulfilledConditionException) e.getCause();
                sender.sendMessage("Failed condition: " + exception.getDescription());
                return true;
            } else {
                sender.sendMessage("An error occurred while executing the command.");
                e.printStackTrace();
                return false;
            }
        }
    }

    /**
     * Executed on tab completion for this command, returning a list of
     * options the player can tab through.
     *
     * @param sender Source object which is executing this command
     * @param alias  the alias being used
     * @param args   All arguments passed to the command, split via ' '
     * @return a list of tab-completions for the specified arguments. This
     * will never be null. List may be immutable.
     * @throws IllegalArgumentException if sender, alias, or args is null
     */
    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        if (sender == null || alias == null || args == null) {
            throw new IllegalArgumentException();
        }

        if (args.length == 0 || args.length > simpleCommand.getArguments().size()) {
            return Collections.emptyList();
        }

        return simpleCommand.getArguments().get(args.length - 1).autoComplete(args[args.length - 1]);
    }

    /**
     * Check if a command sender has the given permission.
     *
     * @param sender command sender
     * @param permission permission
     * @return true if the sender is the console or if the player has the permission, otherwise false
     */
    private boolean hasPermission(CommandSender sender, String permission) {
        return sender instanceof ConsoleCommandSender || (sender instanceof Player && sender.hasPermission(permission));
    }
}
