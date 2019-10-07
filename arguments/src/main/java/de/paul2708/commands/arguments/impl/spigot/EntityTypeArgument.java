package de.paul2708.commands.arguments.impl.spigot;

import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.Validation;
import de.paul2708.commands.language.MessageResource;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This command arguments represents a {@link org.bukkit.entity.EntityType} argument.
 *
 * @author Cerus
 */
public final class EntityTypeArgument implements CommandArgument<EntityType> {

    /**
     * Validate the object by a given command argument.
     * The command argument is trimmed, doesn't contain any spaces and is not empty.
     *
     * @param argument command argument
     * @return a valid or invalid validation
     */
    @Override
    public Validation<EntityType> validate(String argument) {
        EntityType entityType;
        try {
            entityType = EntityType.valueOf(argument);
        } catch (Exception ignored) {
            return Validation.invalid(MessageResource.of("argument.entitytype.invalid", argument));
        }

        return Validation.valid(entityType);
    }

    /**
     * Get the correct usage for this type.
     *
     * @return usage
     */
    @Override
    public MessageResource usage() {
        return MessageResource.of("argument.entitytype.usage");
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
        List<String> autoComplete = Arrays.stream(EntityType.values())
                .map(entityType -> entityType.name().toLowerCase())
                .filter(name -> name.startsWith(argument.toLowerCase()))
                .collect(Collectors.toList());

        return Collections.unmodifiableList(autoComplete);
    }
}