package de.paul2708.commands.arguments.impl.spigot;

import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.Validation;
import de.paul2708.commands.language.MessageResource;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 19.10.19
 */
public final class EnchantmentArgument implements CommandArgument<Enchantment> {

    @Override
    public Validation<Enchantment> validate(String argument) {
        String newArgument = argument.startsWith("minecraft:") ? argument.substring(10) : argument;

        NamespacedKey key = NamespacedKey.minecraft(newArgument);
        return Stream.of(Enchantment.values())
                .filter(e -> e.getKey().equals(key))
                .findFirst()
                .map(Validation::valid)
                .orElse(Validation.invalid(MessageResource.of("argument.enchantment.invalid", newArgument)));
    }

    @Override
    public MessageResource usage() {
        return MessageResource.of("argument.enchantment.usage");
    }

    @Override
    public List<String> autoComplete(String argument) {
        return Stream.of(Enchantment.values())
                .filter(enchantment -> {
                    NamespacedKey key = enchantment.getKey();
                    return argument.startsWith(key.toString())
                            || argument.startsWith(key.getNamespace())
                            || argument.startsWith(key.getKey());
                })
                .map(enchantment -> enchantment.getKey().toString())
                .collect(collectingAndThen(toList(), Collections::unmodifiableList));
    }
}
