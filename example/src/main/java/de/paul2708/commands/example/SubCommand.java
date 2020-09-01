package de.paul2708.commands.example;

import de.paul2708.commands.core.annotation.Command;
import org.bukkit.command.ConsoleCommandSender;

/**
 * This class provides commands with sub commands.
 *
 * @author Paul2708
 */
public final class SubCommand {

    @Command(name = "parent")
    public void parent(ConsoleCommandSender player) {
        player.sendMessage("Parent command.");
    }

    @Command(name = "sub", parent = {"parent"})
    public void sub(ConsoleCommandSender player, String arg) {
        player.sendMessage("Sub command: " + arg);
    }

    @Command(name = "subsub", parent = {"parent", "sub"})
    public void subSub(ConsoleCommandSender player, String argument) {
        player.sendMessage("Subsub command: " + argument);
    }
}