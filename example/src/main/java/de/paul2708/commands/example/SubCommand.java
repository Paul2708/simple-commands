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
    public void sub(ConsoleCommandSender player, Person arg) {
        player.sendMessage("Sub command: " + arg);
    }

    @Command(name = "sab", parent = {"parent"})
    public void sab(ConsoleCommandSender player, Person arg, Person lol) {
        player.sendMessage("Sab command: " + arg + " " + lol);
    }

    @Command(name = "subsub", parent = {"parent", "sub"})
    public void subSub(ConsoleCommandSender player, Person argument) {
        player.sendMessage("Subsub command: " + argument);
    }
}