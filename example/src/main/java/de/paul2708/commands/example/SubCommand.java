package de.paul2708.commands.example;

import de.paul2708.commands.core.annotation.Command;
import org.bukkit.entity.Player;

/**
 * This class provides commands with sub commands.
 *
 * @author Paul2708
 */
public final class SubCommand {

    @Command(name = "parent")
    public void parent(Player player) {
        player.sendMessage("Parent command.");
    }

    @Command(name = "sub", parent = {"parent"})
    public void sub(Player player) {
        player.sendMessage("Sub command.");
    }

    @Command(name = "subsub", parent = {"parent", "sub"})
    public void subSub(Player player, String argument) {
        player.sendMessage("Subsub command: " + argument);
    }
}