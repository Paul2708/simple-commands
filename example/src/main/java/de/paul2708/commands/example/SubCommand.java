package de.paul2708.commands.example;

import de.paul2708.commands.core.annotation.Command;
import org.bukkit.command.ConsoleCommandSender;

/**
 * This class provides commands with sub commands.
 *
 * @author Paul2708
 */
public final class SubCommand {

    /**
     * /parent
     *
     * @param player sender
     */
    @Command(name = "parent")
    public void parent(ConsoleCommandSender player) {
        player.sendMessage("Parent command.");
    }

    /**
     * /parent sub [Person]
     *
     * @param player sender
     * @param arg    arg
     */
    @Command(name = "sub", parent = {"parent"})
    public void sub(ConsoleCommandSender player, Person arg) {
        player.sendMessage("Sub command: " + arg);
    }

    /**
     * /parent sab [Person] [Person]
     *
     * @param player sender
     * @param arg    arg
     * @param arg2   arg2
     */
    @Command(name = "sab", parent = {"parent"})
    public void sab(ConsoleCommandSender player, Person arg, Person arg2) {
        player.sendMessage("Sab command: " + arg + " " + arg2);
    }

    /**
     * /parent sub subsub [Person]
     *
     * @param player sender
     * @param arg    arg
     */
    @Command(name = "subsub", parent = {"parent", "sub"})
    public void subSub(ConsoleCommandSender player, Person arg) {
        player.sendMessage("Subsub command: " + arg);
    }
}