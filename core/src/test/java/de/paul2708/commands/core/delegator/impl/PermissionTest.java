package de.paul2708.commands.core.delegator.impl;

import de.paul2708.commands.core.annotation.Command;
import de.paul2708.commands.core.command.SimpleCommand;
import de.paul2708.commands.core.delegator.CommandDelegatorTest;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.mockito.Mockito.*;

/**
 * This class tests if the command will be executed if the sender has enough permissions.
 *
 * @author Paul2708
 */
final class PermissionTest extends CommandDelegatorTest {

    @Test
    void consolePermission() {
        SimpleCommand command = resolve(TestCommand.class, "console-permission", Collections.emptyList());
        assertSuccess(mock(ConsoleCommandSender.class), command);
    }

    @Test
    void playerOpPermission() {
        SimpleCommand command = resolve(TestCommand.class, "player-op", Collections.emptyList());

        // Mock players
        Player opPlayer = mock(Player.class);
        doReturn(true).when(opPlayer).isOp();

        Player randomPlayer = mock(Player.class);
        doReturn(false).when(randomPlayer).isOp();

        // Perform commands
        assertSuccess(opPlayer, command);
        assertMessage("command.no_permission", randomPlayer, command);
    }

    @Test
    void playerPermission() {
        SimpleCommand command = resolve(TestCommand.class, "player-permission", Collections.emptyList());

        // Mock players
        Player permissionPlayer = mock(Player.class);
        doAnswer(invocation -> invocation.getArgument(0).equals("sample"))
                .when(permissionPlayer)
                .hasPermission(any(String.class));

        Player noPermissionPlayer = mock(Player.class);
        doAnswer(invocation -> !invocation.getArgument(0).equals("sample"))
                .when(noPermissionPlayer)
                .hasPermission(any(String.class));

        // Perform commands
        assertSuccess(permissionPlayer, command);
        assertMessage("command.no_permission", noPermissionPlayer, command);
    }

    public static class TestCommand {

        @Command(name = "console-permission", permission = "console")
        public void consolePermission(ConsoleCommandSender console) {

        }

        @Command(name = "player-op", permission = "*")
        public void playerOpPermission(Player player) {

        }

        @Command(name = "player-permission", permission = "sample")
        public void playerPermission(Player player) {

        }
    }
}