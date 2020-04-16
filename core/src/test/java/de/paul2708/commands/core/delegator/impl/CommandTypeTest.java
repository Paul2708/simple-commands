package de.paul2708.commands.core.delegator.impl;

import de.paul2708.commands.core.command.SimpleCommand;
import de.paul2708.commands.core.delegator.CommandDelegatorTest;
import de.paul2708.commands.core.delegator.ExampleCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * This class tests the command sender matching.
 *
 * @author Paul2708
 */
final class CommandTypeTest extends CommandDelegatorTest {

    private SimpleCommand playerOnlyCommand;
    private SimpleCommand consoleOnlyCommand;
    private SimpleCommand bothCommand;

    @BeforeEach
    void setUp() {
        this.playerOnlyCommand = resolve(ExampleCommand.class, "player-only", Collections.emptyList());
        this.consoleOnlyCommand = resolve(ExampleCommand.class, "console-only", Collections.emptyList());
        this.bothCommand = resolve(ExampleCommand.class, "player-console", Collections.emptyList());
    }

    @Test
    void playerOnly() {
        assertMessage("command.only_players", mockConsole(), playerOnlyCommand);
        assertMessage("command.only_players", mockSender(), playerOnlyCommand);
        assertSuccess(mockPlayer(), playerOnlyCommand);
    }

    @Test
    void consoleOnly() {
        assertMessage("command.only_console", mockPlayer(), consoleOnlyCommand);
        assertMessage("command.only_console", mockSender(), consoleOnlyCommand);
        assertSuccess(mockConsole(), consoleOnlyCommand);
    }

    @Test
    void both() {
        assertSuccess(mockPlayer(), bothCommand);
        assertSuccess(mockConsole(), bothCommand);
        assertSuccess(mockSender(), bothCommand);
    }

    public CommandSender mockSender() {
        CommandSender sender = mock(CommandSender.class);
        doReturn(true).when(sender).isOp();

        return sender;
    }

    public Player mockPlayer() {
        Player player = mock(Player.class);
        doReturn(true).when(player).isOp();

        return player;
    }

    public ConsoleCommandSender mockConsole() {
        return mock(ConsoleCommandSender.class);
    }
}