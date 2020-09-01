package de.paul2708.commands.core.matcher;

import de.paul2708.commands.core.annotation.Command;
import de.paul2708.commands.core.command.CommandMatcher;
import de.paul2708.commands.core.command.CommandType;
import de.paul2708.commands.core.command.SimpleCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class tests the {@link CommandMatcher}.
 *
 * @author Paul2708
 */
public final class CommandMatcherTest {

    private SimpleCommand rootCommand;
    private SimpleCommand subACommand;
    private SimpleCommand subASubCommand;

    private CommandMatcher matcher;

    /**
     * Create a shuffled list of sub commands and its root command.
     */
    @BeforeEach
    void setUp() {
        List<SimpleCommand> commands = new ArrayList<>();
        this.rootCommand = buildCommand("root", new String[0]);
        this.subACommand = buildCommand("sub-a", new String[]{"root"});
        SimpleCommand subBCommand = buildCommand("sub-b", new String[]{"root"});
        this.subASubCommand = buildCommand("sub-a-sub", new String[]{"root", "sub-a"});

        commands.add(rootCommand);
        commands.add(subACommand);
        commands.add(subBCommand);
        commands.add(subASubCommand);

        Collections.shuffle(commands);

        this.matcher = new CommandMatcher(commands);
    }

    @Test
    void findRootCommand() {
        assertEquals(rootCommand, matcher.getRootCommand());
    }

    @Test
    void countMatchingArguments() {
        int matches = matcher.countMatchingArguments(
                new String[]{"a", "b", "c"},
                new String[]{"a", "b", "c", "d"}
        );
        assertEquals(3, matches);
    }

    @Test
    void countNoneMatchingArguments() {
        int matches = matcher.countMatchingArguments(
                new String[]{"d", "a", "b", "c"},
                new String[]{"a", "b", "c", "d"}
        );
        assertEquals(0, matches);
    }

    /**
     * Test if the correct command get matched for given arguments.
     */
    @Test
    void findBestMatch() {
        assertEquals(subACommand, matcher.findBestMatch(new String[]{"sub-a"}));

        assertEquals(subASubCommand, matcher.findBestMatch(new String[]{"sub-a", "sub-a-sub"}));

        assertEquals(subACommand, matcher.findBestMatch(new String[]{"sub-a", "sub-c"}));

        assertEquals(rootCommand, matcher.findBestMatch(new String[]{"sub-d", "sub-c"}));
    }

    /**
     * Build a mocked simple command by name and its parent.
     * Other attributes are undefined.
     *
     * @param name   name
     * @param parent parent
     * @return mocked simple command
     */
    private SimpleCommand buildCommand(String name, String[] parent) {
        Command command = mock(Command.class);
        when(command.name()).thenReturn(name);
        when(command.parent()).thenReturn(parent);
        when(command.toString()).thenReturn(name + " " + Arrays.toString(parent));

        return new SimpleCommand(command, CommandType.DEFAULT_COMMAND, null, null, Collections.emptyList());
    }
}