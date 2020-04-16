package de.paul2708.commands.core.delegator;

import de.paul2708.commands.core.annotation.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * This class provides a set of dummy commands to test specific scenarios.
 *
 * @author Paul2708
 */
public final class ExampleCommand {

    @Command(name = "player-only")
    public void onlyPlayer(Player player) {

    }

    @Command(name = "console-only")
    public void onlyPlayer(ConsoleCommandSender console) {

    }

    @Command(name = "player-console")
    public void onlyPlayer(CommandSender sender) {

    }
}