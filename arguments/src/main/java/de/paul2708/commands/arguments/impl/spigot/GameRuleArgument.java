package de.paul2708.commands.arguments.impl.spigot;

import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.Validation;
import de.paul2708.commands.language.MessageResource;
import org.bukkit.GameRule;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 19.10.19
 */
public final class GameRuleArgument implements CommandArgument<GameRule<?>> {
    @Override
    public Validation<GameRule<?>> validate(String argument) {
        GameRule<?> gameRule = GameRule.getByName(argument);
        if (gameRule == null) {
            return Validation.invalid(MessageResource.of("argument.gamerule.invalid", argument));
        } else {
            return Validation.valid(gameRule);
        }
    }

    @Override
    public MessageResource usage() {
        return MessageResource.of("argument.gamerule.usage");
    }

    @Override
    public List<String> autoComplete(String argument) {
        return Stream.of(GameRule.values())
                .filter(rule -> rule.getName().startsWith(argument))
                .map(GameRule::getName)
                .collect(collectingAndThen(toList(), Collections::unmodifiableList));
    }
}
