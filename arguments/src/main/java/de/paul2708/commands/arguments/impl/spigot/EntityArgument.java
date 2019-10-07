package de.paul2708.commands.arguments.impl.spigot;

import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.Validation;
import de.paul2708.commands.language.MessageResource;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This command arguments represents a {@link Entity} argument.
 *
 * @author Cerus
 */
public final class EntityArgument implements CommandArgument<Entity> {

    /**
     * Validate the object by a given command argument.
     * The command argument is trimmed, doesn't contain any spaces and is not empty.
     *
     * @param argument command argument
     * @return a valid or invalid validation
     */
    @Override
    public Validation<Entity> validate(String argument) {
        Entity entity = Bukkit.getWorlds()
                .stream()
                .flatMap(world -> world.getEntities().stream())
                .filter(anEntity -> anEntity.getUniqueId().toString().equalsIgnoreCase(argument) ||
                        argument.equalsIgnoreCase(anEntity.getCustomName()))
                .findAny()
                .orElse(null);

        if (entity == null) {
            return Validation.invalid(MessageResource.of("argument.entity.invalid", argument));
        }

        return Validation.valid(entity);
    }

    /**
     * Get the correct usage for this type.
     *
     * @return usage
     */
    @Override
    public MessageResource usage() {
        return MessageResource.of("argument.entity.usage");
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
        List<String> autoComplete = Bukkit.getWorlds().stream()
                .flatMap(world -> world.getEntities().stream())
                .map(entity -> (entity.getCustomName() == null ?
                        entity.getUniqueId().toString() : entity.getCustomName()))
                .filter(s -> s.startsWith(argument.toLowerCase()))
                .collect(Collectors.toList());

        return Collections.unmodifiableList(autoComplete);
    }
}