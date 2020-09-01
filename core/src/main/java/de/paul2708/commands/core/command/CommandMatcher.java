package de.paul2708.commands.core.command;

import java.util.ArrayList;
import java.util.List;

/**
 * This class matches a group of commands against user arguments.
 *
 * @author Paul2708
 * @see #findBestMatch(String[])
 */
public final class CommandMatcher {

    private final List<SimpleCommand> commands;
    private final SimpleCommand rootCommand;

    /**
     * Create a new command matcher with a list of commands.
     * It also finds the root command.
     *
     * @param commands group of commands including root command and all sub commands
     */
    public CommandMatcher(List<SimpleCommand> commands) {
        this.commands = new ArrayList<>(commands);

        this.rootCommand = commands.stream()
                .filter(SimpleCommand::isRoot)
                .findAny()
                .orElseThrow();
    }

    /**
     * Find the best commands that matches the arguments.
     *
     * @param args arguments that doesn't contain the root command
     * @return best match or root command, if none sub commands matches the arguments
     */
    public SimpleCommand findBestMatch(String[] args) {
        int maxMatches = 0;
        SimpleCommand current = null;

        // TODO: Regard arguments of sub commands
        for (SimpleCommand command : commands) {
            int matches = countMatchingArguments(command.getPathWithoutParent(), args);

            if (matches >= maxMatches) {
                maxMatches = matches;
                current = command;
            }
        }

        return maxMatches == 0 ? rootCommand : current;
    }

    /**
     * Count the matching arguments in command and input args.
     *
     * @param commandPath command path excluding root
     * @param inputArgs   input args excluding root
     * @return matches
     */
    public int countMatchingArguments(String[] commandPath, String[] inputArgs) {
        if (commandPath.length > inputArgs.length) {
            return 0;
        } else {
            for (int i = 0; i < commandPath.length; i++) {
                if (!commandPath[i].equalsIgnoreCase(inputArgs[i])) {
                    return 0;
                }
            }

            return commandPath.length;
        }
    }

    /**
     * Get the found root command.
     *
     * @return root command
     */
    public SimpleCommand getRootCommand() {
        return rootCommand;
    }
}