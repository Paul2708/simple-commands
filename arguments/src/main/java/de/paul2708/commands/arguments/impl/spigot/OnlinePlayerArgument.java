package de.paul2708.commands.arguments.impl.spigot;

import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.Validation;
import de.paul2708.commands.language.MessageResource;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This command arguments represents a {@link Player} argument.
 * The player has to be online to be valid.
 *
 * @author Paul2708
 */
public class OnlinePlayerArgument implements CommandArgument<Player> {

    /**
     * Validate the object by a given command argument.
     * The command argument is trimmed, doesn't contain any spaces and is not empty.
     *
     * @param argument command argument
     * @return a valid or invalid validation
     */
    @Override
    public Validation<Player> validate(String argument) {
        Player player = Bukkit.getPlayer(argument);

        if (player == null || !player.isOnline()) {
            return Validation.invalid(MessageResource.of("argument.player.offline", argument));
        }

        return Validation.valid(player);
    }

    /**
     * Get the correct usage for this type.
     *
     * @return usage
     */
    @Override
    public MessageResource usage() {
        return MessageResource.of("argument.player.usage");
    }

    /**
     * Get a list of strings that are used for the auto completion.
     * The argument can be empty.
     *
     * @param argument command argument (might be empty)
     * @return unmodifiable list of string
     */
    @Override
    public List<String> autoComplete(String argument) {
        List<String> autoComplete = Bukkit.getOnlinePlayers()
                .stream()
                .map(player -> player.getName().toLowerCase())
                .filter(name -> name.startsWith(argument.toLowerCase()))
                .collect(Collectors.toList());

        return Collections.unmodifiableList(autoComplete);
    }
}