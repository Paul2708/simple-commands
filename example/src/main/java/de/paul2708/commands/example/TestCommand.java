package de.paul2708.commands.example;

import de.paul2708.commands.core.annotation.Command;
import org.bukkit.entity.Player;

/**
 * This class holds methods, that are declared as commands.
 *
 * @author Paul2708
 */
public class TestCommand {

    /**
     * Add two numbers.
     *
     * @param player command executor
     * @param a first integer
     * @param b second integer
     */
    @Command(name = "test")
    public void test(Player player, Integer a, Integer b) {
        player.sendMessage(String.format("%d + %d = %d", a, b, a + b));
    }
}
