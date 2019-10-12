package de.paul2708.commands.arguments.impl.spigot;

import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.Validation;
import de.paul2708.commands.language.MessageResource;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * This command arguments represents a {@link Location} argument.
 *
 * @author Cerus
 */
public final class LocationArgument implements CommandArgument<Location> {

    private final Predicate<String> locationValidator = s -> s.matches(".*,(-?[0-9]*),(-?[0-9]*),(-?[0-9]*)");

    /**
     * Validate the object by a given command argument.
     * The command argument is trimmed, doesn't contain any spaces and is not empty.
     *
     * @param argument command argument
     * @return a valid or invalid validation
     */
    @Override
    public Validation<Location> validate(String argument) {
        if (!locationValidator.test(argument)) {
            return Validation.invalid(MessageResource.of("argument.location.invalid", argument));
        }

        String[] splitted = argument.split(",");
        String worldName = splitted[0];
        String xAsString = splitted[1];
        String yAsString = splitted[2];
        String zAsString = splitted[3];

        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            return Validation.invalid(MessageResource.of("argument.location.invalid.world", worldName));
        }

        int x;
        try {
            x = Integer.parseInt(xAsString);
        } catch (NumberFormatException ignored) {
            // Should only be thrown if given number exceeds Integer range
            return Validation.invalid(MessageResource.of("argument.location.invalid.x", xAsString));
        }

        int y;
        try {
            y = Integer.parseInt(yAsString);
        } catch (NumberFormatException ignored) {
            // Should only be thrown if given number exceeds Integer range
            return Validation.invalid(MessageResource.of("argument.location.invalid.y", yAsString));
        }

        int z;
        try {
            z = Integer.parseInt(zAsString);
        } catch (NumberFormatException ignored) {
            // Should only be thrown if given number exceeds Integer range
            return Validation.invalid(MessageResource.of("argument.location.invalid.z", zAsString));
        }

        return Validation.valid(new Location(world, x, y, z));
    }

    /**
     * Get the correct usage for this type.
     *
     * @return usage
     */
    @Override
    public MessageResource usage() {
        return MessageResource.of("argument.location.usage");
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
        return Collections.emptyList();
    }
}