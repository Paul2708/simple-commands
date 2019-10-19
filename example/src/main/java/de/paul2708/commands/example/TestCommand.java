package de.paul2708.commands.example;

import de.paul2708.commands.core.annotation.Command;
import de.paul2708.commands.core.annotation.Inject;
import de.paul2708.commands.core.annotation.Optional;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.logging.Level;

import static de.paul2708.commands.arguments.CommandArgument.condition;

/**
 * This class holds methods, that are declared as commands.
 *
 * @author Paul2708
 */
public final class TestCommand {

    @Inject
    private ExamplePlugin plugin;

    /**
     * Teleport yourself to a target player.
     *
     * @param sender player command only
     * @param target target player, that has to be online
     */
    @Command(name = "teleport", desc = "Teleport you to a player", permission = "example.tp")
    public void test(Player sender, Player target) {
        condition(!sender.equals(target), "You cannot teleport you to yourself");

        sender.teleport(target);
        sender.sendMessage("You got teleported to " + target.getName() + ".");
    }

    /**
     * Log an example message to the console by using injected plugin.
     *
     * @param sender console command only
     */
    @Command(name = "log", desc = "Log a message")
    public void log(ConsoleCommandSender sender) {
        plugin.getLogger().log(Level.INFO, "Just an example log message.");
    }

    /**
     * Set a persons age.
     *
     * @param sender sender (player and console allowed)
     * @param person person to change
     * @param age    age
     */
    @Command(name = "person", desc = "Change a persons age")
    public void setAge(CommandSender sender, Person person, int age) {
        condition(age > 0, "The persons age has to be positive");

        // Set age
        // ...

        sender.sendMessage("You set the age for: " + person.toString());
    }

    /**
     * Do some fancy optional stuff
     */
    @Command(name = "optcmd", desc = "Optionally specify a number")
    public void optionalTest(CommandSender sender, @Optional Integer integer, String text) {
        integer = integer != null ? integer : -1;
        sender.sendMessage(String.format("You send the string: %s and the number %d (-1 if none specified)",
                text, integer));
    }
}