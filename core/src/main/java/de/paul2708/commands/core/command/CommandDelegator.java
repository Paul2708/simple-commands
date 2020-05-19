package de.paul2708.commands.core.command;

import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.exception.NotFulfilledConditionException;
import de.paul2708.commands.core.CommandRegistry;
import de.paul2708.commands.core.command.argument.ArgumentGenerator;
import de.paul2708.commands.core.command.argument.ArgumentTester;
import de.paul2708.commands.core.command.argument.result.SuccessResult;
import de.paul2708.commands.core.command.argument.result.TestResult;
import de.paul2708.commands.core.command.registry.BukkitCommandRegistry;
import de.paul2708.commands.core.language.LanguageSelector;
import de.paul2708.commands.language.MessageResource;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This command is a bukkit command that executes the {@link SimpleCommand}.
 *
 * @author Paul2708
 */
public final class CommandDelegator extends Command {

    private final LanguageSelector languageSelector;
    private final BukkitCommandRegistry registry;
    private final SimpleCommand simpleCommand;

    /**
     * Create a new basic command based on the simple command.
     *
     * @param languageSelector language selector to translate messages
     * @param simpleCommand    simple command
     */
    public CommandDelegator(LanguageSelector languageSelector, BukkitCommandRegistry registry,
                            SimpleCommand simpleCommand) {
        super(simpleCommand.getInformation().name());

        this.languageSelector = languageSelector;
        this.registry = registry;
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
                    return true;
                }

                break;
            case CONSOLE_COMMAND:
                if (!(sender instanceof ConsoleCommandSender)) {
                    languageSelector.sendMessage(sender, MessageResource.of("command.only_console"));
                    return true;
                }

                break;
            case DEFAULT_COMMAND:
            default:
                break;
        }

        // Check permission
        if (!hasPermission(sender, simpleCommand.getInformation().permission())) {
            languageSelector.sendMessage(sender, MessageResource.of("command.no_permission"));
            return true;
        }

        // Test arguments
        ArgumentGenerator generator = new ArgumentGenerator(simpleCommand.getArguments());
        ArgumentTester tester = new ArgumentTester(args);

        List<TestResult> results = new LinkedList<>();

        for (List<CommandArgument<?>> arguments : generator.generate()) {
            TestResult result = tester.test(arguments);
            results.add(result);
        }

        // Handle results
        results.stream()
                .filter(result -> result instanceof SuccessResult)
                .findFirst()
                .ifPresentOrElse(result -> {
                    List<Object> test = ((SuccessResult) result).getMappedArguments().stream()
                            .map(parameter -> parameter.isEmpty() ? null : parameter.get())
                            .collect(Collectors.toList());
                    execute(sender, test);
                }, () -> {
                    // TODO: How to check which error message should be printed?
                    sendUsage(sender, simpleCommand.getArguments());
                });
        return true;
    }

    /**
     * Execute the command method with a list of passed parameters.
     *
     * @param sender           command sender
     * @param mappedParameters mapped parameters, excluding the first sender object
     */
    @SuppressWarnings("checkstyle:IllegalCatch")
    public void execute(CommandSender sender, List<Object> mappedParameters) {
        mappedParameters.add(0, sender);

        try {
            simpleCommand.getMethod().invoke(simpleCommand.getObject(), mappedParameters.toArray());
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof NotFulfilledConditionException) {
                NotFulfilledConditionException exception = (NotFulfilledConditionException) e.getCause();
                languageSelector.sendMessage(sender,
                        MessageResource.of("command.failed_condition", exception.getDescription()));
            } else {
                languageSelector.sendMessage(sender, MessageResource.of("command.error"));
                e.printStackTrace();
            }
        } catch (Exception e) {
            languageSelector.sendMessage(sender, MessageResource.of("command.error"));
            e.printStackTrace();
        }
    }

    /**
     * private helper to send usage to a CommandSender
     *
     * @param sender    the sender to send the usage to
     * @param arguments the required arguments of the command
     */
    private void sendUsage(CommandSender sender, List<CommandArgument<?>> arguments) {
        StringBuilder usage = new StringBuilder("/" + simpleCommand.getInformation().name() + " ");

        for (CommandArgument<?> argument : arguments) {
            if (argument.isOptional()) {
                usage.append(languageSelector.translate(sender, MessageResource.of("symbol.optional.left")))
                        .append(languageSelector.translate(sender, argument.usage()))
                        .append(languageSelector.translate(sender, MessageResource.of("symbol.optional.right")));
            } else {
                usage.append(languageSelector.translate(sender, MessageResource.of("symbol.required.left")))
                        .append(languageSelector.translate(sender, argument.usage()))
                        .append(languageSelector.translate(sender, MessageResource.of("symbol.required.right")));
            }

            usage.append(" ");
        }

        languageSelector.sendMessage(sender, MessageResource.of("command.false_usage", usage.toString().trim()));
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
        // TODO: Check if tab completion works with optional arguments

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
     * @param sender     command sender
     * @param permission permission
     * @return true if the sender is the console, has operator rights or has the permission
     * or no permission is needed at all
     */
    private boolean hasPermission(CommandSender sender, String permission) {
        if (permission.equals("")) {
            return true;
        }

        return sender instanceof ConsoleCommandSender
                || sender.isOp()
                || (sender.hasPermission(permission)
                && !permission.equals(de.paul2708.commands.core.annotation.Command.OP_PERMISSION));
    }
}