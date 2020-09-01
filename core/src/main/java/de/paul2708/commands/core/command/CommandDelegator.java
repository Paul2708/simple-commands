package de.paul2708.commands.core.command;

import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.exception.NotFulfilledConditionException;
import de.paul2708.commands.core.command.argument.ArgumentGenerator;
import de.paul2708.commands.core.command.argument.ArgumentTester;
import de.paul2708.commands.core.command.argument.result.SuccessResult;
import de.paul2708.commands.core.command.argument.result.TestResult;
import de.paul2708.commands.core.language.LanguageSelector;
import de.paul2708.commands.language.MessageResource;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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
    private final List<SimpleCommand> commands;

    private CommandMatcher matcher;

    private boolean initiated;

    /**
     * Create a new basic command based on the simple command.
     *
     * @param languageSelector language selector to translate messages
     * @param commands         list of commands including root and all sub commands
     */
    public CommandDelegator(LanguageSelector languageSelector, List<SimpleCommand> commands) {
        super(commands.get(0).getBukkitLabel());

        this.languageSelector = languageSelector;
        this.commands = commands;
    }

    /**
     * Called if the first command relating to this command group got exectued.
     * This means that every command got registered.
     */
    private void firstExecution() {
        // TODO: Ugly af
        if (!initiated) {
            this.matcher = new CommandMatcher(commands);
            initiated = true;
        }
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
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, String[] args) {
        // TODO: Add custom listener
        firstExecution();

        SimpleCommand simpleCommand = matcher.findBestMatch(args);

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

        // Move arguments
        String[] movedArgs = new String[args.length - simpleCommand.getInformation().parent().length];
        System.arraycopy(args, simpleCommand.getInformation().parent().length, movedArgs, 0, movedArgs.length);

        // Test arguments
        ArgumentGenerator generator = new ArgumentGenerator(simpleCommand.getArguments());
        ArgumentTester tester = new ArgumentTester(movedArgs);

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
                    execute(simpleCommand, sender, test);
                }, () -> {
                    // TODO: How to check which error message should be printed?
                    sendUsage(sender, simpleCommand, simpleCommand.getArguments());
                });
        return true;
    }

    /**
     * Execute the command method with a list of passed parameters.
     *
     * @param simpleCommand    executed (sub) command
     * @param sender           command sender
     * @param mappedParameters mapped parameters, excluding the first sender object
     */
    @SuppressWarnings("checkstyle:IllegalCatch")
    public void execute(SimpleCommand simpleCommand, CommandSender sender, List<Object> mappedParameters) {
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
     * @param command   (sub) command
     * @param arguments the required arguments of the command
     */
    private void sendUsage(CommandSender sender, SimpleCommand command, List<CommandArgument<?>> arguments) {
        StringBuilder usage = new StringBuilder("/" + String.join(" ", command.getPath()) + " ");

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
    @NotNull
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, String[] args)
            throws IllegalArgumentException {
        firstExecution();

        // TODO: Check if tab completion works with optional arguments

        SimpleCommand command = matcher.findBestMatch(args);
        List<String> subCommands = matcher.findDirectSubCommands(command).stream()
                .filter(cmd -> cmd.getPathWithoutParent().length == args.length)
                .map(c -> c.getInformation().name())
                .filter(s -> s.startsWith(args[args.length - 1]))
                .collect(Collectors.toList());

        List<String> arguments = new ArrayList<>();
        if (!command.getArguments().isEmpty()) {
            String singleArgument = args[args.length - 1];
            int argIndex = args.length - command.getPathWithoutParent().length - 1;

            if (argIndex >= 0 && argIndex < command.getArguments().size()) {
                CommandArgument<?> argument = command.getArguments().get(argIndex);
                arguments.addAll(argument.autoComplete(singleArgument));
            }
        }

        List<String> result = new ArrayList<>(subCommands);
        result.addAll(arguments);
        return result;
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
        if (permission.equals(de.paul2708.commands.core.annotation.Command.NONE_PERMISSION)) {
            return true;
        }

        return sender instanceof ConsoleCommandSender
                || sender.isOp()
                || (sender.hasPermission(permission)
                && !permission.equals(de.paul2708.commands.core.annotation.Command.OP_PERMISSION));
    }
}