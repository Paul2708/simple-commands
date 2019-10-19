package de.paul2708.commands.core.command;

import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.Validation;
import de.paul2708.commands.arguments.exception.NotFulfilledConditionException;
import de.paul2708.commands.core.language.LanguageSelector;
import de.paul2708.commands.language.MessageResource;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * This command is a bukkit command that executes the {@link SimpleCommand}.
 *
 * @author Paul2708
 */
public final class BasicCommand extends Command {

    private final LanguageSelector languageSelector;
    private final SimpleCommand simpleCommand;

    /**
     * Create a new basic command based on the simple command.
     *
     * @param languageSelector language selector to translate messages
     * @param simpleCommand simple command
     */
    public BasicCommand(LanguageSelector languageSelector, SimpleCommand simpleCommand) {
        super(simpleCommand.getInformation().name());

        this.languageSelector = languageSelector;
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
    @SuppressWarnings("checkstyle:illegalcatch")
    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        // TODO: Add custom listener

        // Check executor
        switch (simpleCommand.getType()) {
            case PLAYER_COMMAND:
                if (!(sender instanceof Player)) {
                    languageSelector.sendMessage(sender, MessageResource.of("command.only_players"));
                    return false;
                }

                break;
            case CONSOLE_COMMAND:
                if (!(sender instanceof ConsoleCommandSender)) {
                    languageSelector.sendMessage(sender, MessageResource.of("command.only_console"));
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
            languageSelector.sendMessage(sender, MessageResource.of("command.no_permission"));
            return false;
        }

        // Check arguments
        Iterator<CommandArgument<?>> arguments = simpleCommand.getArguments().iterator();


        List<Validation<?>> errors = new ArrayList<>();
        List<Object> parameters = new LinkedList<>();
        parameters.add(sender);

        for (int i = 0; i < args.length; i++) {
            if (!arguments.hasNext()) {
                sendUsage(sender, simpleCommand.getArguments());
                for(Validation<?> validation: errors){
                    languageSelector.sendMessage(sender, validation.getErrorResource());
                }
                return false;
            }
            CommandArgument<?> argument = arguments.next();
            Validation<?> validate = argument.validate(args[i]);
            parameters.add(validate.getParsedObject());

            if (!validate.isValid()) {
                if(argument.isOptional()){
                    i--;
                    // i will be incremented at the end of the loop causing this text argument to be processed once
                    //more
                    errors.add(validate);
                    // We will take note, so if the command still fails we can still report this
                    // error
                    continue;
                }
                languageSelector.sendMessage(sender, validate.getErrorResource());
                return false;
            }
        }
        System.out.println(parameters);
        // If any arguments are left, you passed to few arguments.
        if(arguments.hasNext()){
            sendUsage(sender, simpleCommand.getArguments());
        }

        // Execute command
        try {
            simpleCommand.getMethod().invoke(simpleCommand.getObject(), parameters.toArray());
            return true;
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof NotFulfilledConditionException) {
                NotFulfilledConditionException exception = (NotFulfilledConditionException) e.getCause();
                languageSelector.sendMessage(sender,
                        MessageResource.of("command.failed_condition", exception.getDescription()));
                return true;
            } else {
                languageSelector.sendMessage(sender, MessageResource.of("command.error"));
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            languageSelector.sendMessage(sender, MessageResource.of("command.error"));
            e.printStackTrace();
            return false;
        }
    }

    private void sendUsage(CommandSender sender, List<CommandArgument<?>> arguments) {
        StringBuilder usage = new StringBuilder("/" + simpleCommand.getInformation().name() + " ");

        for (CommandArgument<?> argument : arguments) {
            usage.append(languageSelector.translate(sender, argument.usage())).append(" ");
        }

        languageSelector.sendMessage(sender, MessageResource.of("command.false_usage", usage.toString()));
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
