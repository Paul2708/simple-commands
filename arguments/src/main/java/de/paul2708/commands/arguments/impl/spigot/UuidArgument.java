package de.paul2708.commands.arguments.impl.spigot;

import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.Validation;
import de.paul2708.commands.language.MessageResource;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * This command arguments represents a {@link UUID} argument.
 *
 * @author Stu (https://github.com/Stupremee)
 * @since 19.10.19
 */
public final class UuidArgument implements CommandArgument<UUID> {

  @Override
  public Validation<UUID> validate(String argument) {
    try {
      return Validation.valid(UUID.fromString(argument));
    } catch (IllegalArgumentException error) {
      return Validation.invalid(MessageResource.of("argument.uuid.invalid"));
    }
  }

  @Override
  public MessageResource usage() {
    return MessageResource.of("argument.uuid.usage");
  }

  @Override
  public List<String> autoComplete(String argument) {
    return Collections.emptyList();
  }
}
